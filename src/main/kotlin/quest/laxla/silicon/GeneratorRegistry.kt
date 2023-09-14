package quest.laxla.silicon

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.SimpleRegistry
import net.minecraft.util.Identifier

public object GeneratorRegistry : SimpleRegistry<Generator<*>>(Silicon registry "generator", Lifecycle.stable()) {
    @Suppress("UNCHECKED_CAST")
    public inline fun <reified T> getGeneratorOf(identifier: Identifier): Generator<T>? =
        get(identifier) as? Generator<T>?
}
