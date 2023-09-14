package quest.laxla.silicon

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.entrypoint.EntrypointUtil
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.LoggerFactory
import quest.laxla.silicon.block.*

public object Silicon : ModInitializer, NamespaceProvider {
    public override val namespace: String = "silicon"

    internal val logger: KLogger = KotlinLogging.logger(namespace)

    public lateinit var dependencies: ImmutableList<ModContainer>

    public var isFrozen: Boolean = false
        private set

    override fun onInitialize(mod: ModContainer) {
        logger.debug { "Initializing Silicon..." }

        mod.metadata()?.id().takeIf { it != namespace }?.let {
            logger.debug { "Silicon has encountered an unexpected mod ID. Change it to [$namespace] in the [quilt.mod.json] file." }
            throw IllegalStateException("Silicon's namespace ($namespace) differs from the mod ID ($it)!")
        }

        FeatureSetRegistry[Button at this] = ButtonFeature
        FeatureSetRegistry[Door at this] = DoorFeature
        FeatureSetRegistry[Fence at this] = FenceFeature
        FeatureSetRegistry[FenceGate at this] = FenceGateFeature
        FeatureSetRegistry[Lever at this] = LeverFeature
        FeatureSetRegistry[PressurePlate at this] = PressurePlateFeature
        FeatureSetRegistry[Slab at this] = SlabFeature
        FeatureSetRegistry[Stairs at this] = StairsFeature
        FeatureSetRegistry[Trapdoor at this] = TrapdoorFeature
        FeatureSetRegistry[Wall at this] = WallFeature

        FeatureSetRegistry[ShapedBlock at this] = ShapedBlockFeature

        val dependencies = mutableListOf<ModContainer>()

        EntrypointUtil.invoke("silicon/init/kotlin", SiliconKotlinEntrypoint::class.java) { entrypoint, dependencyMod ->
            val modID = dependencyMod.metadata().id()
            with(entrypoint) {
                NamespaceProvider.StringWrapper(modID)(KotlinLogging.logger("$namespace/$modID"), dependencyMod)
            }

            dependencies += dependencyMod
        }

        EntrypointUtil.invoke("silicon/init/java", SiliconJavaEntrypoint::class.java) { entrypoint, dependencyMod ->
            if (mod in dependencies) logger.warn {
                "Skipping java entrypoint of mod [${dependencyMod.metadata().name()}], " +
                    "namespace [${dependencyMod.metadata().id()}], as it also declares a kotlin entrypoint. "
            } else {
                entrypoint(LoggerFactory.getLogger(namespace + '/' + dependencyMod.metadata().id()), dependencyMod)
                dependencies += dependencyMod
            }
        }

        this.dependencies = dependencies.toImmutableList()

        // todo: find static data library, use it to create the generators
    }
}
