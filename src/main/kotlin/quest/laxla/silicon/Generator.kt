package quest.laxla.silicon

/**
 * @author Laxystem
 * @since 0.0.1-alpha
 */
public fun interface Generator<in Base : Any, out Output> {
    /**
     * Generates a new [Output] from a [Base]!
     *
     * @author Laxystem
     * @return null if failed.
     * @since 0.0.1-alpha
     */
    public fun generate(input: GeneratorContext<out Base>): Output?
}
