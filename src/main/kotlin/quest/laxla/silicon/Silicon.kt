package quest.laxla.silicon

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import net.minecraft.registry.Registries
import net.minecraft.registry.tag.TagManagerLoader
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.QuiltLoader
import org.quiltmc.loader.api.entrypoint.EntrypointUtil
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.LoggerFactory
import quest.laxla.silicon.block.*
import quest.laxla.silicon.item.BlockItemFeature

/**
 * The Silicon Library.
 *
 * @see Feature
 *
 * @author Laxystem
 */
public object Silicon : ModInitializer, NamespaceProvider {
    public override val namespace: String = "silicon"

    internal val logger: KLogger = KotlinLogging.logger(namespace)

    public lateinit var dependencies: ImmutableList<ModContainer>

    /**
     * Do [Feature]s accept modifications?
     */
    public var isFrozen: Boolean = false
        private set

    override fun onInitialize(mod: ModContainer) {
        logger.debug { "Initializing Silicon..." }

        mod.metadata()?.id().takeIf { it != namespace }?.let {
            logger.debug { "Silicon has encountered an unexpected mod ID. Change it to [$namespace] in the [quilt.mod.json] file." }
            throw IllegalStateException("Silicon's namespace ($namespace) differs from the mod ID ($it)!")
        }

        FeatureRegistry[Block at this] = BlockFeature
        FeatureRegistry[Button at this] = ButtonFeature
        FeatureRegistry[Door at this] = DoorFeature
        FeatureRegistry[Fence at this] = FenceFeature
        FeatureRegistry[FenceGate at this] = FenceGateFeature
        FeatureRegistry[Lever at this] = LeverFeature
        FeatureRegistry[PressurePlate at this] = PressurePlateFeature
        FeatureRegistry[Slab at this] = SlabFeature
        FeatureRegistry[Stairs at this] = StairsFeature
        FeatureRegistry[Trapdoor at this] = TrapdoorFeature
        FeatureRegistry[Wall at this] = WallFeature

        FeatureRegistry[BlockItem at this] = BlockItemFeature

        GeneratorRegistry[Block at this] = BlockFeature
        GeneratorRegistry[Button at this] = ButtonFeature
        GeneratorRegistry[Door at this] = DoorFeature
        GeneratorRegistry[Fence at this] = FenceFeature
        GeneratorRegistry[FenceGate at this] = FenceGateFeature
        GeneratorRegistry[Lever at this] = LeverFeature
        GeneratorRegistry[PressurePlate at this] = PressurePlateFeature
        GeneratorRegistry[Slab at this] = SlabFeature
        GeneratorRegistry[Stairs at this] = StairsFeature
        GeneratorRegistry[Trapdoor at this] = TrapdoorFeature
        GeneratorRegistry[Wall at this] = WallFeature

        GeneratorRegistry[BlockItem at this] = BlockItemFeature

        val dependencies = mutableListOf<ModContainer>()

        EntrypointUtil.invoke("silicon/init/kotlin", SiliconKotlinEntrypoint::class.java) { entrypoint, dependencyMod ->
            val modID = dependencyMod.metadata().id()
            with(entrypoint) {
                NamespaceProvider.StringWrapper(modID)
                    .onInitialize(KotlinLogging.logger("$namespace/$modID"), dependencyMod)
            }

            dependencies += dependencyMod
        }

        EntrypointUtil.invoke("silicon/init/java", SiliconJavaEntrypoint::class.java) { entrypoint, dependencyMod ->
            if (mod in dependencies) logger.warn {
                "Skipping java entrypoint of mod [${dependencyMod.metadata().name()}], " +
                        "namespace [${dependencyMod.metadata().id()}], as it also declares a kotlin entrypoint. "
            } else {
                entrypoint.onInitialize(
                    LoggerFactory.getLogger(namespace + '/' + dependencyMod.metadata().id()),
                    dependencyMod
                )
                dependencies += dependencyMod
            }
        }

        this.dependencies = dependencies.toImmutableList()

        isFrozen = true

        Registries.REGISTRY.asSequence().flatMap { registry ->
            QuiltLoader.getAllMods().map {
                it.getPath(TagManagerLoader.getRegistryDirectory(registry.key))
            }
        }.forEach(::println)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <Base : Any, T : Any> generate(feature: Feature<T>, base: Base, generator: FeatureGenerator<*, *>): T? =
        if (feature.accepts(generator.output) && generator.base.java.isAssignableFrom(base::class.java)) {
            try {
                @Suppress("UNREACHABLE_CODE")
                (generator as? FeatureGenerator<Base, T>)?.generate(TODO("create generator options from base"))
            } catch (e: ClassCastException) {
                logger.warn(e) { "Failed to use generator [${generator::class.qualifiedName}]: base [$base] of class [${base::class.qualifiedName}], feature [$feature] registered at [${feature.identifier}] of class [${feature::class.qualifiedName}]" }
                null
            }
        } else null
}
