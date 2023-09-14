package quest.laxla.silicon

import net.minecraft.registry.Registry
import kotlin.reflect.KClass

public class SimpleDynamicFeature<T : Any> internal constructor(
    subtag: String,
    registry: Registry<in T>,
    kClass: KClass<T>
) : AbstractSimpleFeature<T>(subtag, registry, kClass), DynamicFeature<T> {
    private val features: MutableList<Feature<*>> = mutableListOf()

    override val subfeatures: Sequence<Feature<*>>
        get() = features.asSequence()

    override fun add(feature: Feature<*>): Unit? = if (!Silicon.isFrozen && this !== feature && feature !in this && this !in feature) {
        features += feature
    } else null

    override fun contains(feature: Feature<*>): Boolean = feature in features
}
