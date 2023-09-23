package quest.laxla.silicon

import io.github.oshai.kotlinlogging.KLogger
import org.quiltmc.loader.api.ModContainer

@Language.Api(Language.Kotlin)
public interface SiliconKotlinEntrypoint {
    public fun NamespaceProvider.onInitialize(logger: KLogger, mod: ModContainer)
}
