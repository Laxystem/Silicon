@file:JvmName("SiliconUtils") @file:Language.Api(Language.BytecodeLol)

package quest.laxla.silicon

import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.SystemDetails
import quest.laxla.silicon.mixin.TagEntryMixin
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

public const val Block: String = "block"
public const val BlockItem: String = "block_item"
public const val Boat: String = "boat"
public const val Button: String = "button"
public const val Door: String = "door"
public const val Fence: String = "fence"
public const val FenceGate: String = "fence_gate"
public const val HangingSign: String = "hanging_sign"
public const val Item: String = "item"
public const val Lever: String = "lever"
public const val PressurePlate: String = "pressure_plate"
public const val ShapedBlock: String = "shaped_block"
public const val Sign: String = "sign"
public const val Slab: String = "slab"
public const val Stairs: String = "stairs"
public const val Trapdoor: String = "trapdoor"
public const val Wall: String = "wall"

@Language.Api(Language.Kotlin)
public infix fun String.at(namespace: String): Identifier = Identifier(namespace, this)

@Language.Api(Language.Kotlin)
public infix fun String.at(namespace: NamespaceProvider): Identifier = this at namespace.namespace

@Language.Api(Language.Kotlin)
public infix fun <T> NamespaceProvider.registry(path: String): RegistryKey<Registry<T>> =
    RegistryKey.ofRegistry(path at this)

@Language.Api(Language.Kotlin)
public infix fun <T> Identifier.asTagIn(registry: Registry<T>): TagKey<T> = TagKey.of(registry.key, this)

@Language.Api(Language.Kotlin)
public fun String.toIdentifier(): Identifier = Identifier(this)

@Language.Api(Language.BytecodeLol, Language.Kotlin, Language.Java)
@get:JvmName("getIdentifierOfOrNull")
public val Feature<*>.identifier: Identifier? get() = FeatureRegistry.getId(this)

@Language.Api(Language.BytecodeLol, Language.Kotlin, Language.Java)
@get:JvmName("getIdentifierOf")
public val Feature<*>.identifierOrThrow: Identifier get() = identifier ?: error("Feature [$this] isn't registered.")

@Language.Api(Language.Kotlin)
public operator fun Feature<*>.plusAssign(feature: Feature<*>) {
    add(feature)
}


/**
 * Adds the receiver feature to the [thisRef] feature.
 *
 * In explicit API mode, the [property]'s generic type of [Feature] must be exactly the same as the receiver's.
 *
 * For example, the following code won't compile,
 * as [SlabFeature][quest.laxla.silicon.block.SlabFeature]'s generic type is [SlabBlock][net.minecraft.block.SlabBlock],
 * and it is a top-level declaration - it has to be inside a feature.
 * ```kotlin
 * val slab: Feature<Block> by SlabFeature
 * ```
 * But this will:
 * ```kotlin
 * object MyFancyBlockSet : Feature<Block> by blockFeature(Registries.Block, subtag = "fancy") {
 *     /**
 *      * Adds [SlabFeature] to [MyFancyBlockSet] and provides a fancy way to access it.
 *      *
 *      * btw this comment isn't required it's just to help you understand what's going on.
 *      */
 *     val slab: Feature<SlabBlock> by SlabFeature
 * }
 * ```
 *
 * @return The Receiver
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.BadPerformance)
public operator fun <T : Feature<*>> T.provideDelegate(
    thisRef: Feature<*>, property: KProperty<*>
): FeatureDelegate<T> {
    thisRef += this
    return FeatureDelegate(this)
}

/**
 * Creates a [Feature].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin)
public fun <T : Any> feature(
    registry: Registry<in T>, kClass: KClass<T>
): SimpleFeature<T> = SimpleFeature(registry, kClass)

/**
 * Creates a [Feature].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Java)
public fun <T : Any> feature(registry: Registry<in T>, clazz: Class<T>): SimpleFeature<T> =
    feature(registry, clazz.kotlin)

/**
 * Creates a [Feature].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
public inline fun <reified T : Any> feature(registry: Registry<in T>): SimpleFeature<T> = feature(registry, T::class)

/**
 * Creates a [FeatureGenerator].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin)
public fun <Base : Any, Output : Any> generator(
    base: KClass<in Base>, output: KClass<out Output>, generator: Generator<Base, Output>
): FeatureGenerator<Base, Output> = SimpleFeatureGenerator(base, output, generator)


/**
 * Creates a [FeatureGenerator].
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Java)
public fun <Base : Any, Output : Any> generator(
    base: Class<in Base>, output: Class<out Output>, generator: Generator<Base, Output>
): FeatureGenerator<Base, Output> = generator(base.kotlin, output.kotlin, generator)


/**
 * Creates a [FeatureGenerator].
 *
 * @sample quest.laxla.silicon.block.SlabFeature
 * @author Laxystem
 * @since 0.0.1-alpha
 * @receiver just for syntax - does nothing.
 */
@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
public inline infix fun <reified Base : Any, reified Output : Any> Feature.Companion.generator(
    generator: Generator<Base, Output>
): FeatureGenerator<Base, Output> = generator(Base::class, Output::class, generator)

/**
 * Provides a fancy registration syntax.
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 * @see Registry.register
 */
@Language.Api(Language.Kotlin)
public operator fun <T> Registry<in T>.set(identifier: Identifier, entry: T): T =
    Registry.register(this, identifier, entry)

/**
 * Flattens a [map][Map] of [map][Map]s of [iterable][Iterable]s into a [Sequence] of [Triple]s.
 *
 * @author Laxystem
 * @since 0.0.1-alpha
 */
@Language.Api(Language.Kotlin)
public fun <A, B, C> Sequence<Map.Entry<A, Map<B, Iterable<C>>>>.flatten(): Sequence<Triple<A, B, C>> = flatMap { (key, entry) ->
    entry.asSequence().flatMap {
        it.value.asSequence().map { value ->
            Triple(key, it.key, value)
        }
    }
}

internal operator fun SystemDetails.plusAssign(property: KProperty<*>) = addSection("${Silicon.name}.${property.name}") { property.getter.call().toString() }

/**
 * @author Laxystem
 * @since 0.0.1-alpha
 * @see TagEntry.id
 */
@Language.Api(Language.Kotlin)
public val TagEntry.id: Identifier get() = (this as TagEntryMixin).id

/**
 * @author Laxystem
 * @since 0.0.1-alpha
 * @see TagEntry.isTag
 */
@Language.Api(Language.Kotlin)
public val TagEntry.isTag: Boolean get() = (this as TagEntryMixin).isTag
