package quest.laxla.silicon

import net.minecraft.registry.tag.TagKey

public interface Feature<T : Any> {
    @Language.Api(Language.Kotlin)
    public val subfeatures: Sequence<Feature<*>>

    public fun createTag(baseTagPath: String): TagKey<in T>

    public fun castOrNull(it: Any): T?

    @Language.Api(Language.BytecodeLol, Language.Kotlin)
    public operator fun contains(feature: Feature<*>): Boolean = feature in subfeatures
}
