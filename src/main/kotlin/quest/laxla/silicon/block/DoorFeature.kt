package quest.laxla.silicon.block

import net.minecraft.block.DoorBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Door
import quest.laxla.silicon.Feature
import quest.laxla.silicon.reference

public object DoorFeature : Feature<DoorBlock> by reference(Registries.BLOCK, subtag = Door)
