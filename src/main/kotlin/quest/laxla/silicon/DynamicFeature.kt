package quest.laxla.silicon

public interface DynamicFeature<T : Any> : Feature<T> {
    /**
     * Adds the provided [feature] to this feature set.
     */
    @Throws(IndexOutOfBoundsException::class)
    @Language.Api(Language.BytecodeLol)
    public fun add(feature: Feature<*>): Unit?
}
