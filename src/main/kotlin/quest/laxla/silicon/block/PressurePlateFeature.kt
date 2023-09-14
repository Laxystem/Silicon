package quest.laxla.silicon.block

import net.minecraft.block.AbstractPressurePlateBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.PressurePlate
import quest.laxla.silicon.reference

public object PressurePlateFeature : Feature<AbstractPressurePlateBlock> by reference(Registries.BLOCK, subtag = PressurePlate)
