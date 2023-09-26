package quest.laxla.silicon

import org.quiltmc.loader.api.ModContainer

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
     * The default [NamespaceProvider] implementation, wrapping a [ModContainer].
     *
     * @author Laxystem
     */
    @JvmInline
    @Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.BadPerformance)
    public value class Mod(public val mod: ModContainer) : NamespaceProvider {
        override val namespace: String
            get() = mod.metadata().id()
    }
}
