package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.Lever
import quest.laxla.silicon.reference

public object LeverFeature : Feature<Block> by reference(Registries.BLOCK, subtag = Lever)
