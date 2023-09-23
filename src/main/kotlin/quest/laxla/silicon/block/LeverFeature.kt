package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.block.LeverBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

public object LeverFeature : Feature<Block> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, LeverBlock> by Feature generator {
        LeverBlock(it.blockSettings.noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY))
    }
