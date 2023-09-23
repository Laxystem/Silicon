package quest.laxla.silicon

import kotlin.reflect.KClass

public abstract class AbstractSimpleFeatureGenerator<in Base : Any, out Output : Any>(
    override val base: KClass<in Base>,
    override val output: KClass<out Output>
) : FeatureGenerator<Base, Output>
