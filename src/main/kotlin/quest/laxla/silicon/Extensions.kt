@file:JvmName("SiliconUtilities")
@file:Language.Api(Language.BytecodeLol)

package quest.laxla.silicon

import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Language.Api(Language.Kotlin)
public infix fun String.at(namespace: String): Identifier = Identifier(namespace, this)

@Language.Api(Language.Kotlin)
public infix fun String.at(namespace: NamespaceProvider): Identifier = this at namespace.namespace

@Language.Api(Language.Kotlin)
public infix fun <T> NamespaceProvider.registry(path: String): RegistryKey<Registry<T>> = RegistryKey.ofRegistry(path at this)

@Language.Api(Language.Kotlin)
public infix fun <T> Identifier.asTagIn(registry: Registry<T>): TagKey<T> = TagKey.of(registry.key, this)

@Language.Api(Language.Kotlin)
public fun String.toIdentifier(): Identifier = Identifier(this)

@Language.Api(Language.BytecodeLol, Language.Kotlin, Language.Java)
@get:JvmName("getIdentifierOf")
public val Feature<*>.identifier: Identifier? get() = FeatureSetRegistry.getId(this)

@Language.Api(Language.Kotlin)
public operator fun DynamicFeature<*>.plusAssign(feature: Feature<*>) {
    add(feature)
}

@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.BadPerformance)
public operator fun <T : Feature<*>> T.provideDelegate(thisRef: DynamicFeature<*>, property: KProperty<*>): FeatureDelegate<T> {
    thisRef += this
    return FeatureDelegate(this)
}

@Language.Api(Language.Kotlin)
public fun <T : Any> reference(
    registry: Registry<in T>,
    subtag: String,
    kClass: KClass<T>
): SimpleFeature<T> = SimpleFeature(subtag, registry, kClass)

@Language.Api(Language.Java)
public fun <T : Any> reference(
    registry: Registry<in T>,
    subtag: String,
    clazz: Class<T>
): SimpleFeature<T> = reference(registry, subtag, clazz.kotlin)

@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
public inline fun <reified T : Any> reference(
    registry: Registry<in T>,
    subtag: String,
): SimpleFeature<T> = reference(registry, subtag, T::class)

@Language.Api(Language.Kotlin)
public fun <T : Any> feature(
    registry: Registry<in T>,
    subtag: String,
    kClass: KClass<T>
): SimpleDynamicFeature<T> = SimpleDynamicFeature(subtag, registry, kClass)

@Language.Api(Language.Java)
public fun <T : Any> feature(
    registry: Registry<in T>,
    subtag: String,
    clazz: Class<T>
): SimpleDynamicFeature<T> = feature(registry, subtag, clazz.kotlin)

@Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
public inline fun <reified T : Any> feature(
    registry: Registry<in T>,
    subtag: String
): SimpleDynamicFeature<T> = feature(registry, subtag, T::class)

@Language.Api(Language.Kotlin)
public operator fun <T> Registry<in T>.set(identifier: Identifier, entry: T): T = Registry.register(this, identifier, entry)


public const val Button: String = "button"
public const val Door: String = "door"
public const val Trapdoor: String = "trapdoor"
public const val Fence: String = "fence"
public const val FenceGate: String = "fence_gate"
public const val Lever: String = "lever"
public const val PressurePlate: String = "pressure_plate"
public const val Slab: String = "slab"
public const val Stairs: String = "stairs"
public const val Wall: String = "wall"
public const val Block: String = "block"
public const val ShapedBlock: String = "shaped_block"
