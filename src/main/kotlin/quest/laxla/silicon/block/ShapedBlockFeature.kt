package quest.laxla.silicon.block

import net.minecraft.block.*
import net.minecraft.registry.Registries
import quest.laxla.silicon.*

public object ShapedBlockFeature : DynamicFeature<Block> by feature(Registries.BLOCK, subtag = Block) {
    public val stairs: Feature<StairsBlock> by StairsFeature
    public val slab: Feature<SlabBlock> by SlabFeature
    public val wall: Feature<WallBlock> by WallFeature
    public val fence: Feature<FenceBlock> by FenceFeature
    public val fenceGate: Feature<FenceGateBlock> by FenceGateFeature
    public val door: Feature<DoorBlock> by DoorFeature
    public val trapdoor: Feature<TrapdoorBlock> by TrapdoorFeature
    public val button: Feature<AbstractButtonBlock> by ButtonFeature
    public val pressurePlate: Feature<AbstractPressurePlateBlock> by PressurePlateFeature
}
