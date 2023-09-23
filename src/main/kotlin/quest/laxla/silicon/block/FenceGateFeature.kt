package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.block.FenceGateBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

public object FenceGateFeature : Feature<FenceGateBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, FenceGateBlock> by Feature generator {
        FenceGateBlock(it.blockSettings.solid(), it.signType)
    }
