package quest.laxla.silicon

import net.minecraft.registry.tag.TagKey
import kotlin.reflect.KClass

/**
 * Represents a Minecraft feature, like [Block][net.minecraft.block.Block] or [Item][net.minecraft.item.Item],
 * that can be generated at runtime.
 *
 * Features must be registered to the [FeatureRegistry], otherwise they won't work.
 *
 * todo: features with multiple parts, for example, floor signs + wall signs.
 *
 * @param T the type of this feature - must be a subtype of this feature's [Registry][net.minecraft.registry.Registry].
 * For example,
 * a [StairsBlock][net.minecraft.block.StairsBlock] feature belongs to the [BlockRegistry][net.minecraft.registry.Registries.BLOCK].
 * @see createTag
 * @author Laxystem
 * @since 0.0.1-alpha
 */
public interface Feature<T : Any> {
    /**
     * A single-use [sequence] of features that generate for blocks in this feature.
     *
     * After a feature generates, all of its subfeatures do too.
     *
     * @author Laxystem
     */
    @Language.Api(Language.Kotlin)
    public val subfeatures: Sequence<Feature<*>>

    /**
     * Check if [subfeatures] contains [feature].
     *
     * Override if a more performant implementation is available.
     * @see Sequence.contains
     *
     * @author Laxystem
     */
    @Language.Api(Language.BytecodeLol, Language.Kotlin)
    public operator fun contains(feature: Feature<*>): Boolean = feature in subfeatures

    /**
     * Add the provided [feature] to [subfeatures].
     *
     * @return null if failed
     * @author Laxystem
     */
    @Throws(IndexOutOfBoundsException::class)
    @Language.Api(Language.BytecodeLol)
    public fun add(feature: Feature<*>): Unit?

    /**
     * Creates the [TagKey] implementors of this feature should be in.
     * All members of this tag must be a subtype of [T].
     *
     * Each [Feature] has a corresponding tag containing the things it represents.
     *
     * For example, let's create a featire with the tag `#example_mod:stone`.
     * Let's add a [StairsFeature][quest.laxla.silicon.block.StairsFeature].
     * It will have the tag `#silicon:stone/silicon/stairs`,
     * as its [createTag] function adds its [identifier]'s [namespace][net.minecraft.util.Identifier.namespace]
     * and [path][net.minecraft.util.Identifier.path] to the end of the base tag.
     *
     * Note tags created this way tend to be extremely long after a few [subfeatures]
     * (for example, `#silicon:minecraft/stone/silicon/stairs/silicon/block_item`).
     * For that reason, showing them to the user is not recommended.
     *
     * This function is not called when this feature is created directly.
     * The namespace should be [Silicon]'s.
     *
     * @author Laxystem
     */
    public fun createTag(baseTagPath: String): TagKey<in T>

    /**
     * Is generator output of type [kClass] compatible with this feature?
     */
    @Language.Api(Language.Kotlin)
    public infix fun accepts(kClass: KClass<*>): Boolean

    /**
     * Placeholder companion object to allow fancy extension functions.
     *
     * @see Feature
     */
    public companion object
}
