package quest.laxla.silicon.item

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings
import quest.laxla.silicon.Feature
import quest.laxla.silicon.FeatureGenerator
import quest.laxla.silicon.feature
import quest.laxla.silicon.generator

public object BlockItemFeature : Feature<Item> by feature(Registries.ITEM),
    FeatureGenerator<Block, Item> by Feature generator {
        val blockItem = it.base.asItem()

        if (blockItem === Items.AIR) BlockItem(it.base, QuiltItemSettings()) else blockItem
    }
