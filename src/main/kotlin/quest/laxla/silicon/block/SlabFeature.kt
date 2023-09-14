package quest.laxla.silicon.block

import net.minecraft.block.SlabBlock
import net.minecraft.registry.Registries
import quest.laxla.silicon.Feature
import quest.laxla.silicon.Slab
import quest.laxla.silicon.reference

public object SlabFeature : Feature<SlabBlock> by reference(Registries.BLOCK, subtag = Slab)
