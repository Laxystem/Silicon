package quest.laxla.silicon

import net.minecraft.registry.Registry
import kotlin.reflect.KClass

public open class SimpleFeature<T : Any> internal constructor(
    override val registry: Registry<in T>, override val type: KClass<T>
) : AbstractSimpleFeature<T> {
    private val features: MutableList<Feature<*>> = mutableListOf()

    override val subfeatures: Sequence<Feature<*>>
        get() = features.asSequence()

    override fun add(feature: Feature<*>): Unit? = if (isAddable(feature)) {
        features += feature
    } else {
        Silicon.logger.warn {
            "Feature [$feature] not added to [$this]. " + "Features ${if (Silicon.isFrozen) "are" else "aren't"} frozen."
        }
        null
    }

    override fun contains(feature: Feature<*>): Boolean = feature in features

    override fun toString(): String = (this::class.qualifiedName?.removePrefix(SimpleFeature::class.java.packageName)?.removePrefix(prefix = ".")
            ?: SimpleFeature::class.simpleName) + "(identifier = '$identifier', registry = '${registry.key.value}', type = '${type.qualifiedName}')"
}
