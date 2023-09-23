package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.block.StairsBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

public object StairsFeature : Feature<StairsBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, StairsBlock> by Feature generator {
        StairsBlock(it.base.defaultState, it.blockSettings)
    }
