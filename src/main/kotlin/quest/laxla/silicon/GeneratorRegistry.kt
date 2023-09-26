package quest.laxla.silicon

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.SimpleRegistry

/**
 * @author Laxystem
 * @since 0.0.1-alpha
 */
public object GeneratorRegistry : SimpleRegistry<FeatureGenerator<*, *>>(Silicon registry "generator", Lifecycle.stable()) {

    /**
     * Fetches all generators that [feature] supports.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(ClassCastException::class)
    @Language.Api(Language.Kotlin)
    public operator fun <T : Any> get(feature: Feature<T>): Sequence<FeatureGenerator<*, T>> = asSequence()
        .filter { feature.accepts(it.output) }
        .mapNotNull { it as? FeatureGenerator<*, T>? }
}
