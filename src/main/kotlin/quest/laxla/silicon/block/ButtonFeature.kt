package quest.laxla.silicon.block

import net.minecraft.block.AbstractButtonBlock
import net.minecraft.block.Block
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

/**
 *
 */
public object ButtonFeature :
    Feature<AbstractButtonBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, AbstractButtonBlock> by Feature generator {
        AbstractButtonBlock(
            it.blockSettings
                .noCollision()
                .strength(0.5F)
                .pistonBehavior(PistonBehavior.DESTROY),
            it.blockSetType,
            it.buttonActivationLength,
            it.isButtonTriggeredByProjectiles
        )
    }
