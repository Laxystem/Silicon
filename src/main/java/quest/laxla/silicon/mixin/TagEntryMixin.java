package quest.laxla.silicon.mixin;

import net.minecraft.registry.tag.TagEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TagEntry.class)
public interface TagEntryMixin {
	@Accessor
	Identifier getId();

	@Accessor
	boolean isTag();
}
