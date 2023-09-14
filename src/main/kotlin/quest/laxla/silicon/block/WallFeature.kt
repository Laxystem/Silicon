package quest.laxla.silicon.block

import net.minecraft.block.WallBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.Wall
import quest.laxla.silicon.reference

public object WallFeature : Feature<WallBlock> by reference(Registries.BLOCK, subtag = Wall)
