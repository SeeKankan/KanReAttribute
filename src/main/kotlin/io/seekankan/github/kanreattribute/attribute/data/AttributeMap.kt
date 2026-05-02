package io.seekankan.github.kanreattribute.attribute.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.message.ItemStyleKey

class AttributeMap(
    val map: Map<AttributeType, Double> = emptyMap()
): MutableMap<AttributeType, Double> by map.toMutableMap() {

//    constructor(map: Map<AttributeType, Double> = mapOf()): this(map.toMutableMap())

//    constructor(): super() {
//    }
//    constructor(initialCapacity: Int): super(initialCapacity) {
//    }
//    constructor(map: Map<AttributeType, Double>): super(map) {
//
//    }
    companion object {
        @JvmStatic
        @JsonCreator
        fun create(
//            @JsonProperty("attributes")
            @JsonDeserialize(
                keyUsing = AttributeTypeKeyDeserializer::class,
                contentAs = Double::class
            )
            attributes: Map<AttributeType, Double>
        ): AttributeMap {
            return AttributeMap(attributes)
        }
    }

    fun add(key: AttributeType, value: Double): Double {
        val oldVal = this[key] ?: 0.0
        val newVal = oldVal + value
        this[key] = newVal
        return newVal
    }
    fun subtract(key: AttributeType, value: Double): Double = add(key, -value)
    fun multiply(key: AttributeType, factor: Double): Double {
        val oldVal = this[key] ?: 0.0
        val newVal = oldVal * factor
        this[key] = newVal
        return newVal
    }
    fun divide(key: AttributeType, divisor: Double): Double {
        val oldVal = this[key] ?: 0.0
        val newVal = oldVal / divisor
        this[key] = newVal
        return newVal
    }
//    fun handleEventData(eventData: EventData) {
//        plugin.attributeManager.subAttributeRegistry.forEachMap(this) { attrType, subAttribute, attrValue ->
//            if(attrValue != null) subAttribute.calculateEventNumber(attrValue, this, eventData)
//        }
//    }
    fun toMiniMessageLoreData(manager: AttributeManager): List<Map<String, String>> {
        return this.mapNotNull { (key, value) ->
            val subAttribute = manager.subAttributeRegistry.get(key)
            if(subAttribute == null || subAttribute !is Displayable) { //ignore non-displayable attribute
//                mapOf(
//                    ItemStyleKey.ATTRIBUTE_DISPLAY_NAME to key,
//                    ItemStyleKey.ATTRIBUTE_VALUE to value.toString()
//                )
                null
            } else {
                mapOf(
                    ItemStyleKey.ATTRIBUTE_DISPLAY_NAME to subAttribute.displayName,
                    ItemStyleKey.ATTRIBUTE_VALUE to subAttribute.formatValue(value)
                )
            }

        }

    }
    @JsonValue
    fun toMap(): Map<AttributeType, Double> {
        return map
    }
}