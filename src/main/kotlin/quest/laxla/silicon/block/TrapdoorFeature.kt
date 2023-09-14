package quest.laxla.silicon.block

import net.minecraft.block.TrapdoorBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.Trapdoor
import quest.laxla.silicon.reference

public object TrapdoorFeature : Feature<TrapdoorBlock> by reference(Registries.BLOCK, subtag = Trapdoor)
