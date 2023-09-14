package quest.laxla.silicon

import net.minecraft.registry.Registry
import kotlin.reflect.KClass

public class SimpleFeature<T : Any> internal constructor(
    subtag: String,
    registry: Registry<in T>,
    kClass: KClass<T>
) : AbstractSimpleFeature<T>(subtag, registry, kClass) {
    override val subfeatures: Sequence<Feature<*>>
        get() = emptySequence()
}
