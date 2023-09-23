package quest.laxla.silicon

import kotlin.reflect.KClass

/**
 * @see feature
 * @see
 */
public open class SimpleFeatureGenerator<in Base : Any, out Output : Any>(
    base: KClass<in Base>,
    output: KClass<out Output>,
    generator: Generator<Base, Output>
) : AbstractSimpleFeatureGenerator<Base, Output>(base, output), Generator<Base, Output> by generator
