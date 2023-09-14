package quest.laxla.silicon

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.SimpleRegistry

public object FeatureSetRegistry : SimpleRegistry<Feature<*>>(Silicon registry "feature_set", Lifecycle.stable(), true)
