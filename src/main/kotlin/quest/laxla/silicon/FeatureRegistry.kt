package quest.laxla.silicon

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.SimpleRegistry

public object FeatureRegistry : SimpleRegistry<Feature<*>>(Silicon registry "feature", Lifecycle.stable())

// todo: figure out intrusive holders
