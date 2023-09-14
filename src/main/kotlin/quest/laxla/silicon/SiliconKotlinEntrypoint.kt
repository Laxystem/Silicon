package quest.laxla.silicon

import io.github.oshai.kotlinlogging.KLogger
import org.quiltmc.loader.api.ModContainer

@Language.Api(Language.Kotlin)
public interface SiliconKotlinEntrypoint {
    public operator fun NamespaceProvider.invoke(logger: KLogger, mod: ModContainer)
}
