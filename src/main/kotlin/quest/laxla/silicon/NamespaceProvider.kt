package quest.laxla.silicon

/**
 * @author Laxystem
 */
@Language.Api(Language.Kotlin)
public interface NamespaceProvider {
    /**
     * @author Laxystem
     */
    public val namespace: String

    /**
     * The default [NamespaceProvider] implementation, wrapping a string.
     *
     * @author Laxystem
     */
    @JvmInline
    @Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.BadPerformance)
    public value class StringWrapper(override val namespace: String) : NamespaceProvider
}
