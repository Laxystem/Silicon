package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.block.DoorBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

public object DoorFeature : Feature<DoorBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, DoorBlock> by Feature generator {
        DoorBlock(
            it.blockSettings
                .nonOpaque()
                .strength(if (it.isManuallyOpenable) 3F else 5F)
                .pistonBehavior(PistonBehavior.DESTROY),
            it.blockSetType
        )
    }
