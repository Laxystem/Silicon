package quest.laxla.silicon

import kotlin.reflect.KClass

/**
 * Generates contents for [Feature]s.
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
public interface FeatureGenerator<in Base : Any, out Output : Any> : Generator<Base, Output> {
    /**
     * The [KClass] instance of [Base].
     *
     * Used to check if this generator can generate a [Feature].
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val base: KClass<in Base>

    /**
     * The [KClass] instance of [Output].
     *
     * Used by [Feature.accepts] to check if this generator can generate a [Feature].
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val output: KClass<out Output>

    // todo: move [base] and [output] to [Generator]?
}
