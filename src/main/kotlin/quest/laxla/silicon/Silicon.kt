package quest.laxla.silicon

import com.google.gson.JsonParser
import com.mojang.serialization.Dynamic
import com.mojang.serialization.JsonOps
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.collections.immutable.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.ResourceFileNamespace
import net.minecraft.registry.tag.TagEntry
import net.minecraft.registry.tag.TagFile
import net.minecraft.registry.tag.TagManagerLoader
import net.minecraft.resource.MultiPackResourceManager
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.QuiltLoader
import org.quiltmc.loader.api.entrypoint.EntrypointUtil
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.quiltmc.qsl.crash.api.CrashReportEvents
import org.quiltmc.qsl.resource.loader.api.ResourceLoader
import org.quiltmc.qsl.resource.loader.api.ResourcePackActivationType
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
public object Silicon : ModInitializer, NamespaceProvider, SiliconKotlinEntrypoint {
    private const val GIT_INSTANCE = "github.com/LaylaMeower"
    public override val namespace: String = "silicon"
    public val name: String = this::class.simpleName!!
    private val modrinth: String = "https://modrinth.com/mod/$namespace"
    private val github: String = "https://$GIT_INSTANCE/$name"

    internal val logger: KLogger = KotlinLogging.logger(namespace)

    /**
     * Lists all mods that use one of the following entrypoints:
     * - [SiliconJavaEntrypoint]
     * - [SiliconKotlinEntrypoint]
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public lateinit var dependencies: ImmutableList<ModContainer>

    /**
     * Statically available tags of every [Registry], and the identifiers of their contents.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public lateinit var staticTags: ImmutableMap<Registry<*>, ImmutableMap<Identifier, ImmutableList<Identifier>>>

    private val resourceLoader by lazy { ResourceLoader.get(ResourceType.SERVER_DATA) }


    /**
     * Do [Feature]s _currently_ accept modifications?
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public var isFrozen: Boolean = true
        private set

    /**
     * Has silicon generated all common-side assets?
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public var isCommonInitialized: Boolean = false
        private set

    /**
     * Has silicon generated all client-side assets?
     *
     * If this is not a client environment, this may never be set to true.
     *
     * This can only be true if [isCommonInitialized] is true.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public var isClientInitialized: Boolean = false
        internal set

    /**
     * Has silicon generated all server-side assets?
     *
     * If this is not a server environment, this may never be set to true.
     *
     * This can only be true if [isCommonInitialized] is true.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public var isServerInitialized: Boolean = false
        internal set

    /**
     * Initializes Silicon!
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    override fun onInitialize(silicon: ModContainer) {
        logger.debug { "Initializing $name..." }

        verifySiliconMetadata(silicon)

        CrashReportEvents.SYSTEM_DETAILS.register {
            it += ::isFrozen
            it += ::isCommonInitialized
            it += ::isClientInitialized
            it += ::isServerInitialized
        }

        isFrozen = false
        dependencies = invokeEntrypoints()
        isFrozen = true

        staticTags = loadStaticData()

        isCommonInitialized = true
    }

    /**
     * Verifies silicon metadata, and throws an exception if invalidated.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Throws(IllegalStateException::class)
    private fun verifySiliconMetadata(silicon: ModContainer) {
        logger.debug { "Verifying $name metadata..." }

        silicon.metadata()?.takeUnless { it.id() == namespace && it.name() == name }?.let {
            logger.debug {
                "$name has encountered an unexpected mod ID or display name. " +
                    "Change the mod ID to [$namespace] and the display name to [$name] in the [quilt.mod.json] file (aka QMJ).\n" +
                    "If this is a fork, please rename [${Silicon::class.qualifiedName}], and change the [namespace] property. " +
                    "$name uses variables to reference to itself, making it easier for you to fork!\n" +
                    " -- The Laxystem, original creators of the Silicon API."
            }
            logger.error { "$name has crashed! Visit debug.log for more info." }
            error("$name's namespace [$namespace] or name!\n Please only download $name from modrinth [$modrinth] and github [$github]!")
        }
    }

    private fun invokeEntrypoints(): ImmutableList<ModContainer> {
        val dependencies = mutableListOf<ModContainer>()

        EntrypointUtil.invoke(
            Language.Kotlin.initEntrypointName,
            SiliconKotlinEntrypoint::class.java
        ) { entrypoint, dependencyMod ->
            val modID = dependencyMod.metadata().id()
            with(entrypoint) {
                val logger = KotlinLogging.logger("$namespace/$modID")

                logger.debug { Language.Kotlin.initLogOutput }

                NamespaceProvider.StringWrapper(modID).onInitialize(logger, dependencyMod)
            }

            dependencies += dependencyMod
        }

        EntrypointUtil.invoke(
            Language.Java.initEntrypointName,
            SiliconJavaEntrypoint::class.java
        ) { entrypoint, dependencyMod ->
            if (dependencyMod in dependencies) logger.warn {
                "Skipping ${Language.Java.name} entrypoint of mod [${dependencyMod.metadata().name()}], " +
                    "namespace [${
                        dependencyMod.metadata().id()
                    }], as it also declares a ${Language.Kotlin.name} entrypoint. "
            } else {
                entrypoint.onInitialize(
                    LoggerFactory.getLogger(namespace + '/' + dependencyMod.metadata().id())
                        .also { it.debug(Language.Java.initLogOutput) },
                    dependencyMod
                )

                dependencies += dependencyMod
            }
        }

        return dependencies.toImmutableList()
    }

    /**
     * Registers built-in [Feature]s and [Generator]s.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    override fun NamespaceProvider.onInitialize(logger: KLogger, mod: ModContainer) {
        verifySiliconMetadata(mod)

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
    }

    private fun loadStaticData(): ImmutableMap<Registry<*>, ImmutableMap<Identifier, ImmutableList<Identifier>>> {
        val tags = mutableMapOf<Registry<*>, MutableMap<Identifier, MutableList<TagEntry>>>()

        val modResourceManager = MultiPackResourceManager(ResourceType.SERVER_DATA, QuiltLoader.getAllMods().map {
            resourceLoader.newFileSystemResourcePack(
                "static/mod/" + it.metadata().id() at this, // for example_mod, silicon:static/mod/example_mod
                it, // todo: what is this used for? Should the mod be the owner, or silicon?
                it.rootPath(),
                ResourcePackActivationType.ALWAYS_ENABLED // well, we never really register it sooooo
            )
        })


        Registries.REGISTRY.forEach { registry ->
            val tagList = tags.computeIfAbsent(registry) { mutableMapOf() }

            loadTags(tagList, modResourceManager, registry.key, allowReplacing = false)
            // todo datapack resource manager that allows replacing
        }

        val flattenedTags = mutableMapOf<Registry<*>, MutableMap<Identifier, ImmutableList<Identifier>>>()

        tags.forEach { (registry, tagMap) ->
            val flattenedTagMap = flattenedTags.computeIfAbsent(registry) { mutableMapOf() }

            tagMap.forEach { (identifier, contents) ->
                flattenedTagMap[identifier] = contents.asSequence().flatMap {
                    flattenedTagMap.unwrap(it, tagMap)
                }.toImmutableList()
            }
        }

        return flattenedTags.asSequence().map { (registry, tags) -> registry to tags.toImmutableMap() }.toMap().toImmutableMap()
    }

    @Suppress("SameParameterValue") // todo: add replacement-allowing ways to load static tags
    private fun loadTags(
        tags: MutableMap<Identifier, MutableList<TagEntry>>,
        resourceManager: ResourceManager,
        registry: RegistryKey<out Registry<*>>,
        allowReplacing: Boolean
    ) {
        val resourceNamespace = ResourceFileNamespace.json(TagManagerLoader.getRegistryDirectory(registry))
        resourceNamespace.findAllMatchingResources(resourceManager).forEach { (resourceLocation, resources) ->
            val resourceIdentifier = resourceNamespace.unwrapFilePath(resourceLocation)

            resources.forEach { resource ->
                loadTag(
                    resource,
                    tags.computeIfAbsent(resourceIdentifier) { mutableListOf() },
                    allowReplacing,
                    resourceIdentifier,
                    resourceLocation
                )
            }
        }
    }

    private fun loadTag(
        resource: Resource,
        tags: MutableList<TagEntry>,
        allowReplacing: Boolean,
        resourceIdentifier: Identifier,
        resourceLocation: Identifier
    ) = try {
        val tagFile = resource.openBufferedReader().use {
            @Suppress("DEPRECATION")
            TagFile.CODEC.parse(Dynamic(JsonOps.INSTANCE, JsonParser.parseReader(it)))
                .getOrThrow(false, logger::error)!!
        }

        if (tagFile.replace) {
            if (allowReplacing) {
                logger.debug { "Tag [$resourceIdentifier] from [${resource.sourceName}] is throwing a tantrum (replacing previous tag contents), obeying." }
                tags.clear()
            } else logger.warn {
                "Tag [$resourceIdentifier] from [${resource.sourceName}] is throwing a tantrum (replacing previous tag contents), ignoring."
            }
        }

        tags.addAll(tagFile.entries)
    } catch (e: Exception) {
        logger.error(e) { "Failed to read tag [$resourceIdentifier] from [${resource.sourceName} (located at [$resourceLocation])" }
    }

    /**
     * Unwraps [tagEntry] into the receiver, using data from [tagMap].
     *
     * This function is lazy - it'll only execute once if called multiple times with the same data.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    private fun MutableMap<Identifier, ImmutableList<Identifier>>.unwrap(
        tagEntry: TagEntry,
        tagMap: Map<Identifier, List<TagEntry>>
    ): Sequence<Identifier> = this[tagEntry.id]?.asSequence() ?: if (tagEntry.isTag) tagMap[tagEntry.id]!!
        .asSequence()
        .flatMap { unwrap(it, tagMap) }
        .toImmutableList().also { this[tagEntry.id] = it }
        .asSequence()
    else sequenceOf(tagEntry.id)
}
