package quest.laxla.silicon

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.SimpleRegistry

public object FeatureRegistry : SimpleRegistry<Feature<*>>(Silicon registry "feature_set", Lifecycle.stable(), true)
