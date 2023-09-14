package quest.laxla.silicon

@Language.Api(Language.Kotlin)
public interface NamespaceProvider {
    public val namespace: String

    @JvmInline
    @Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.BadPerformance)
    public value class StringWrapper(override val namespace: String): NamespaceProvider
}
