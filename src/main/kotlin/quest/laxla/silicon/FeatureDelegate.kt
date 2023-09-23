package quest.laxla.silicon

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@JvmInline
@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.BadPerformance)
public value class FeatureDelegate<T : Feature<*>> internal constructor(
    public val feature: T
) : ReadOnlyProperty<Feature<*>, T> {

    @Language.Api(Language.Kotlin)
    override fun getValue(thisRef: Feature<*>, property: KProperty<*>): T = feature.also {
        thisRef += it
    }
}
