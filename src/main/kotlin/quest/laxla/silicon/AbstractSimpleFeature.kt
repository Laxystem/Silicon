package quest.laxla.silicon

import net.minecraft.registry.Registry
import net.minecraft.registry.tag.TagKey
import org.jetbrains.annotations.MustBeInvokedByOverriders
import kotlin.reflect.KClass

/**
 * Utility class for implementing [Feature] on your own,
 * containing default implementations that should almost never be overridden.
 *
 * @author Laxystem
 */
public interface AbstractSimpleFeature<T : Any> : Feature<T> {
    public val registry: Registry<in T>
    public val type: KClass<T>

    /**
     * Can this [feature] be added as a [subfeature][subfeatures]?
     *
     * @author Laxystem
     */
    @MustBeInvokedByOverriders
    public fun isAddable(feature: Feature<*>): Boolean =
        !Silicon.isFrozen && this !== feature && feature !in this && this !in feature


    /**
     * @author Laxystem
     * @see Feature.createTag
     */
    override fun createTag(baseTagPath: String): TagKey<in T> =
        "$baseTagPath/${identifierOrThrow.namespace}/${identifierOrThrow.path}" at Silicon asTagIn registry

    /**
     * @author Laxystem
     * @see Feature.accepts
     */
    override fun accepts(kClass: KClass<*>): Boolean = type.java.isAssignableFrom(kClass.java)
}
