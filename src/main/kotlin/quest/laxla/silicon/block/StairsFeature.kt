package quest.laxla.silicon.block

import net.minecraft.block.StairsBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.Stairs
import quest.laxla.silicon.reference

public object StairsFeature : Feature<StairsBlock> by reference(Registries.BLOCK, subtag = Stairs)
