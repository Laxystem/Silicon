package quest.laxla.silicon

import net.minecraft.registry.Registry
import net.minecraft.registry.tag.TagKey
import kotlin.reflect.KClass
import kotlin.reflect.full.safeCast

public sealed class AbstractSimpleFeature<T : Any>(
    public val subtag: String,
    public val registry: Registry<in T>,
    public val kClass: KClass<T>
) : Feature<T> {
    override fun createTag(baseTagPath: String): TagKey<in T> = "$baseTagPath/$subtag" at identifier!!.namespace asTagIn registry

    override fun castOrNull(it: Any): T? = kClass.safeCast(it)
}
