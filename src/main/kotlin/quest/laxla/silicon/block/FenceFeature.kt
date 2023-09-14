package quest.laxla.silicon.block

import net.minecraft.block.FenceBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.Fence
import quest.laxla.silicon.reference

public object FenceFeature : Feature<FenceBlock> by reference(Registries.BLOCK, subtag = Fence)
