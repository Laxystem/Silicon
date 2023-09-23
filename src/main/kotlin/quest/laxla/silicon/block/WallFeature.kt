package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.block.WallBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

public object WallFeature : Feature<WallBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, WallBlock> by Feature generator { WallBlock(it.blockSettings.solid()) }
