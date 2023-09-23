package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

/**
 * The basic [Feature] & [FeatureGenerator] for [SlabBlock].
 * @author Laxystem
 */
public object SlabFeature : Feature<SlabBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, SlabBlock> by Feature generator { SlabBlock(it.blockSettings) }
