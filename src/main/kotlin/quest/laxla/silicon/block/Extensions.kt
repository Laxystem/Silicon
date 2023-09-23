@file:JvmName("SiliconBlockUtils")

package quest.laxla.silicon.block

import net.minecraft.block.Block
import net.minecraft.registry.Registry
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import quest.laxla.silicon.GeneratorContext
import quest.laxla.silicon.Language
import kotlin.reflect.KClass

/**
 * Creates a [BlockFeature].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin)
public fun <T : Block> blockFeature(registry: Registry<in T>, kClass: KClass<T>): BlockFeature<T> =
    BlockFeature(registry, kClass)

/**
 * Creates a [BlockFeature].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Java)
public fun <T : Block> blockFeature(registry: Registry<in T>, clazz: Class<T>): BlockFeature<T> =
    blockFeature(registry, clazz.kotlin)

/**
 * Creates a [BlockFeature].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
public inline fun <reified T : Block> blockFeature(registry: Registry<in T>): BlockFeature<T> =
    blockFeature(registry, T::class)

/**
 * Copies [base][GeneratorContext.base]'s [BlockSettings][net.minecraft.block.AbstractBlock.Settings],
 * and modifies it to match the [context][GeneratorContext].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Java, Language.Kotlin)
@get:JvmName("blockSettingsOf")
public val <T : Block> GeneratorContext<T>.blockSettings: QuiltBlockSettings
    get() = QuiltBlockSettings.copyOf(base).sounds(soundType)
