package quest.laxla.silicon

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.SimpleRegistry

public object GeneratorRegistry : SimpleRegistry<FeatureGenerator<*, *>>(Silicon registry "generator", Lifecycle.stable())
