package quest.laxla.silicon

import io.github.oshai.kotlinlogging.KLogger

@Language.Api(Language.Kotlin)
public interface SiliconKotlinEntrypoint {
    public fun NamespaceProvider.Mod.onInitialize(logger: KLogger)
}
