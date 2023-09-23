package quest.laxla.silicon

import org.quiltmc.loader.api.ModContainer
import org.slf4j.Logger

@Language.Api(Language.Java)
public interface SiliconJavaEntrypoint {
    public fun onInitialize(logger: Logger, mod: ModContainer)
}
