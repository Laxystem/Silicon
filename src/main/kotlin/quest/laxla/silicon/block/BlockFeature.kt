package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import quest.laxla.silicon.*
import quest.laxla.silicon.item.BlockItemFeature
import kotlin.reflect.KClass

public open class BlockFeature<T : Block>(
    registry: Registry<in T>,
    type: KClass<T>
) : SimpleFeature<T>(registry, type) {
    public val item: Feature<Item> by BlockItemFeature

    /**
     * The most basic [Feature] & [Generator] possible, containing a [Block] and a [net.minecraft.item.BlockItem]
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public companion object :
        Feature<Block> by blockFeature(Registries.BLOCK),
        FeatureGenerator<Block, Block> by Feature generator { Block(it.blockSettings) }
}
