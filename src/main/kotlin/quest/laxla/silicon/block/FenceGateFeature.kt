package quest.laxla.silicon.block

import net.minecraft.block.FenceGateBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FenceGate
import quest.laxla.silicon.reference

public object FenceGateFeature : Feature<FenceGateBlock> by reference(Registries.BLOCK, subtag = FenceGate)
