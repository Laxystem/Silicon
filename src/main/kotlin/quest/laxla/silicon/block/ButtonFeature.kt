package quest.laxla.silicon.block

import net.minecraft.block.AbstractButtonBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Button
import quest.laxla.silicon.Feature
import quest.laxla.silicon.reference

public object ButtonFeature : Feature<AbstractButtonBlock> by reference(Registries.BLOCK, subtag = Button)
