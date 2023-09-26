package quest.laxla.silicon

import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

public enum class Language {
    /**
     * This API was made specifically for Java.
     */
    Java,

    /**
     * This API was made specifically for Kotlin.
     */
    Kotlin,

    /**
     * This API was made specifically for Groovy.
     */
    Groovy,

    /**
     * This API was made specifically for Scala.
     */
    Scala,

    /**
     * This API contains no language-specific features (for example, generics), and should work on any JVM language.
     */
    BytecodeLol;

    public val lowercaseName: String = name.lowercase()

    internal val initEntrypointName = "${Silicon.namespace}/${ModInitializer.ENTRYPOINT_KEY}/$lowercaseName"
    internal val initLogOutput = "<$name> Registering ${Silicon.name} features & generators..."

    public enum class ViolationResult {
        CursedCode, BadPerformance, MysteriousExceptions, WontCompile
    }

    /**
     * Marks what languages this API is made for.
     *
     * It is not recommended to use APIs not marked with your language or [BytecodeLol], as the [violationResult] may take place.
     */
    @MustBeDocumented
    @Target(
        AnnotationTarget.CLASS,
        AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.FILE,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.TYPEALIAS,
    )
    @Retention(AnnotationRetention.SOURCE)
    public annotation class Api(vararg val languages: Language, val violationResult: ViolationResult = ViolationResult.CursedCode)
}
