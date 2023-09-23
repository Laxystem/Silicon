package quest.laxla.silicon

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableMap
import net.fabricmc.fabric.api.`object`.builder.v1.block.type.BlockSetTypeBuilder
import net.fabricmc.fabric.api.`object`.builder.v1.block.type.WoodTypeBuilder
import net.minecraft.block.BlockSetType
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.sign.SignType
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.*

/**
 * Provides context about a [Feature] generation.
 */
public class GeneratorContext<Base : Any> {
    /**
     * The [Feature] the generator is based on.
     *
     * For example, Stone Bricks Slab's base is Stone Bricks.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val base: Base

    /**
     * The [Identifier] of the [tag created][Feature.createTag] by [base]'s [Feature].
     *
     * For example, Stone Bricks Slab's base tag is the one that contains Stone Bricks.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val baseIdentifier: Identifier?

    /**
     * The [Identifier] of the [tag created][Feature.createTag] by the top-level feature.
     *
     * For example, Stone Bricks Slab's top level tag is the one that contains Stone.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val topLevelIdentifier: Identifier

    /**
     * The [Identifier] of the [tag created][Feature.createTag] by the [Feature] that invoked this generation,
     * and will contain its output.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val identifier: Identifier

    /**
     * The actual options this [GeneratorContext] stores.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val options: ImmutableMap<Identifier, Any?>

    /**
     * The [SignType] created for this [GeneratorContext].
     *
     * Used to provide sounds and options to some blocks.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val signType: SignType

    /**
     * The [BlockSetType] created for this [GeneratorContext].
     *
     * Used to provide sounds and options to some blocks.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    public val blockSetType: BlockSetType get() = signType.setType

    /**
     * Can door-like blocks be opened manually, by hand?
     * @see BlockSetType.canOpenByHand
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see blockSetType
     */
    public val isManuallyOpenable: Boolean get() = this[GeneratorContext.isManuallyOpenable] ?: true

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.soundType
     * @see SignType.soundType
     * @see blockSetType
     * @see signType
     */
    public val soundType: BlockSoundGroup
        get() = this[GeneratorContext.soundType] ?:
        BlockSoundGroup.WOOD

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see SignType.hangingSignSoundType
     * @see signType
     */
    public val hangingSignSoundType: BlockSoundGroup
        get() = this[GeneratorContext.hangingSignSoundType] ?: BlockSoundGroup.HANGING_SIGN

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.doorClose
     * @see blockSetType
     */
    public val doorCloseSound: SoundEvent
        get() = this[GeneratorContext.doorCloseSound] ?: SoundEvents.BLOCK_WOODEN_DOOR_CLOSE

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.doorOpen
     * @see blockSetType
     */
    public val doorOpenSound: SoundEvent
        get() = this[GeneratorContext.doorOpenSound] ?: SoundEvents.BLOCK_WOODEN_DOOR_OPEN

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.trapdoorClose
     * @see blockSetType
     */
    public val trapdoorCloseSound: SoundEvent
        get() = this[GeneratorContext.trapdoorCloseSound] ?: SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.trapdoorOpen
     * @see blockSetType
     */
    public val trapdoorOpenSound: SoundEvent
        get() = this[GeneratorContext.trapdoorOpenSound] ?: SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see SignType.fenceGateClose
     * @see signType
     */
    public val fenceGateCloseSound: SoundEvent
        get() = this[GeneratorContext.fenceGateCloseSound] ?: SoundEvents.BLOCK_FENCE_GATE_CLOSE

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see SignType.fenceGateOpen
     * @see signType
     */
    public val fenceGateOpenSound: SoundEvent
        get() = this[GeneratorContext.fenceGateOpenSound] ?: SoundEvents.BLOCK_FENCE_GATE_OPEN

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.pressurePlateClickOff
     * @see blockSetType
     */
    public val pressurePlateClickOffSound: SoundEvent
        get() = this[GeneratorContext.pressurePlateClickOffSound] ?: SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.pressurePlateClickOn
     * @see blockSetType
     */
    public val pressurePlateClickOnSound: SoundEvent
        get() = this[GeneratorContext.pressurePlateClickOnSound] ?: SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.buttonClickOff
     * @see blockSetType
     */
    public val buttonClickOffSound: SoundEvent
        get() = this[GeneratorContext.buttonClickOffSound] ?: SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see BlockSetType.buttonClickOn
     * @see blockSetType
     */
    public val buttonClickOnSound: SoundEvent
        get() = this[GeneratorContext.buttonClickOnSound] ?: SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see net.minecraft.block.PressurePlateBlock
     * @see net.minecraft.block.WeightedPressurePlateBlock
     */
    public val isPressurePlateWeighted: Boolean
        get() = this[GeneratorContext.isPressurePlateWeighted] ?: false

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     *
     * @see net.minecraft.block.WeightedPressurePlateBlock.weight
     */
    public val pressurePlateWeight: Int
        get() = this[GeneratorContext.pressurePlateWeight] ?: 15

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     *
     * @see net.minecraft.block.PressurePlateBlock.type
     */
    public val pressurePlateActivationRule: PressurePlateBlock.ActivationRule
        get() = this[GeneratorContext.pressurePlateActivationRule] ?: PressurePlateBlock.ActivationRule.EVERYTHING

    public val buttonActivationLength: Int
        get() = this[GeneratorContext.buttonActivationLength] ?: 20

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     * @see net.minecraft.block.AbstractButtonBlock.activatedByProjectile
     */
    public val isButtonTriggeredByProjectiles: Boolean
        get() = this[GeneratorContext.isButtonTriggeredByProjectiles] ?: true

    /**
     * Create a new [GeneratorContext].
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    internal constructor(base: Base, options: ImmutableMap<Identifier, Any?>, identifier: Identifier) {
        this.base = base
        baseIdentifier = null
        topLevelIdentifier = identifier
        this.identifier = identifier
        this.options = options
        signType = createSignType()
    }

    /**
     * Create a new [GeneratorContext] based on another.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    internal constructor(
        base: GeneratorContext<*>,
        newBase: Base,
        newIdentifier: Identifier,
        modification: (MutableMap<Identifier, *>) -> Unit
    ) {
        this.base = newBase
        baseIdentifier = base.identifier
        topLevelIdentifier = base.topLevelIdentifier
        identifier = newIdentifier
        options = base.options.toMutableMap().apply(modification).toImmutableMap()

        signType = when {
            blockSetTypeAffectors.any { options[it] != base.options[it] } -> createSignType()
            signTypeAffectors.any { options[it] != base.options[it] } -> createSignType(base.blockSetType)
            else -> base.signType
        }
    }

    /**
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    private fun createSignType(
        blockSetType: BlockSetType = BlockSetTypeBuilder().openableByHand(isManuallyOpenable).soundGroup(soundType)
            .doorCloseSound(doorCloseSound).doorOpenSound(doorOpenSound).trapdoorCloseSound(trapdoorCloseSound)
            .trapdoorOpenSound(trapdoorOpenSound).pressurePlateClickOffSound(pressurePlateClickOffSound)
            .pressurePlateClickOnSound(pressurePlateClickOnSound).buttonClickOffSound(buttonClickOffSound)
            .buttonClickOnSound(buttonClickOnSound).register(identifier)
    ) = WoodTypeBuilder().soundGroup(soundType).hangingSignSoundGroup(hangingSignSoundType)
        .fenceGateCloseSound(fenceGateCloseSound).fenceGateOpenSound(fenceGateOpenSound)
        .register(identifier, blockSetType)


    /**
     * Returns the option of type [T] stored under [identifier], or null if not found.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
    public inline operator fun <reified T> get(identifier: Identifier): T? = options[identifier] as? T

    /**
     * Returns the option of type [T] stored under [identifier], and throws if not found.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Language.Api(Language.Kotlin, violationResult = Language.ViolationResult.WontCompile)
    @JvmName("kotlinGetOrThrow")
    public inline fun <reified T> getOrThrow(identifier: Identifier): T = this[identifier]
        ?: throw NoSuchElementException("No context option found for [$identifier] of type [${T::class.qualifiedName}].")

    /**
     * Returns the option stored under [identifier], or null if not found.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Language.Api(Language.Java, Language.BytecodeLol)
    public fun getOrNull(identifier: Identifier): Any? = options[identifier]

    /**
     * Returns the option stored under [identifier].
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Language.Api(Language.Java)
    @JvmName("get")
    public fun getAsOptional(identifier: Identifier): Optional<Any> = Optional.ofNullable(getOrNull(identifier))

    /**
     * Returns the option stored under [identifier], and throws if not found.
     *
     * @author Laxystem
     * @since 0.0.1-alpha
     */
    @Language.Api(Language.Java, Language.BytecodeLol)
    public fun getOrThrow(identifier: Identifier): Any =
        getOrNull(identifier) ?: throw NoSuchElementException("No context option found for [$identifier]")

    public companion object {
        public val isManuallyOpenable: Identifier = "is_manually_openable" at Silicon
        public val soundType: Identifier = "sound_type" at Silicon
        public val hangingSignSoundType: Identifier = "hanging_sign_sound_type" at Silicon
        public val doorCloseSound: Identifier = "door_close_sound" at Silicon
        public val doorOpenSound: Identifier = "door_open_sound" at Silicon
        public val trapdoorCloseSound: Identifier = "trapdoor_close_sound" at Silicon
        public val trapdoorOpenSound: Identifier = "trapdoor_open_sound" at Silicon
        public val fenceGateCloseSound: Identifier = "fence_gate_close_sound" at Silicon
        public val fenceGateOpenSound: Identifier = "fence_gate_open_sound" at Silicon
        public val pressurePlateClickOffSound: Identifier = "pressure_plate_click_off_sound" at Silicon
        public val pressurePlateClickOnSound: Identifier = "pressure_plate_click_on_sound" at Silicon
        public val buttonClickOffSound: Identifier = "button_click_off_sound" at Silicon
        public val buttonClickOnSound: Identifier = "button_click_on_sound" at Silicon
        public val isPressurePlateWeighted: Identifier = "is_pressure_plate_weighted" at Silicon
        public val pressurePlateWeight: Identifier = "pressure_plate_weight" at Silicon
        public val pressurePlateActivationRule: Identifier = "pressure_plate_activation_rule" at Silicon
        public val buttonActivationLength: Identifier = "button_activation_length" at Silicon
        public val isButtonTriggeredByProjectiles: Identifier = "is_button_triggered_by_projectiles" at Silicon

        /**
         * Context options that changing them also forces [blockSetType] to change.
         *
         * @author Laxystem
         * @since 0.0.1-alpha
         */
        public val blockSetTypeAffectors: ImmutableList<Identifier> = persistentListOf(
            isManuallyOpenable,
            soundType,
            doorCloseSound,
            doorOpenSound,
            trapdoorCloseSound,
            trapdoorOpenSound,
            pressurePlateClickOffSound,
            pressurePlateClickOnSound,
            buttonClickOffSound,
            buttonClickOnSound
        )

        /**
         * Context options that changing them also forces [signType] to change.
         *
         * @author Laxystem
         * @since 0.0.1-alpha
         */
        public val signTypeAffectors: ImmutableList<Identifier> = persistentListOf(
            hangingSignSoundType, fenceGateCloseSound, fenceGateOpenSound
        )

        /**
         * Represents parts of a context options' representable UI.
         *
         * @author Laxystem
         * @since 0.0.1-alpha
         */
        @Language.Api(Language.BytecodeLol)
        public enum class OptionPart {
            Name {
                override fun getFallbackText(identifier: Identifier): String = identifier.toString()
            },

            Description,

            Default {
                override fun getFallbackText(identifier: Identifier): String =
                    "Translation for $partName not provided. Hover for details."
            };

            public val partName: String = name.lowercase()

            @Language.Api(Language.BytecodeLol)
            public open fun getFallbackText(identifier: Identifier): String = "No $partName provided."

            /**
             * Creates a translatable text representing the provided [option].
             *
             * @author Laxystem
             * @since 0.0.1-alpha
             */
            @Language.Api(Language.Kotlin, Language.BytecodeLol)
            public infix fun of(option: Identifier): Text =
                Text.translatable(option.toTranslationKey("silicon.option", partName), getFallbackText(option))
        }
    }
}
