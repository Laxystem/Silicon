package quest.laxla.silicon.block

import net.minecraft.block.AbstractPressurePlateBlock
import net.minecraft.block.Block
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.WeightedPressurePlateBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.generator

public object PressurePlateFeature :
    Feature<AbstractPressurePlateBlock> by blockFeature(Registries.BLOCK),
    FeatureGenerator<Block, AbstractPressurePlateBlock> by Feature generator {
        val settings = it.blockSettings
            .pistonBehavior(PistonBehavior.DESTROY)
            .strength(0.5F)
            .solid()
            .noCollision()

        if (it.isPressurePlateWeighted) WeightedPressurePlateBlock(it.pressurePlateWeight, settings, it.blockSetType)
        else PressurePlateBlock(it.pressurePlateActivationRule, settings, it.blockSetType)
    }
