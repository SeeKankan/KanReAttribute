package io.seekankan.github.kanreattribute.gui

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import net.axay.kspigot.gui.GUIType

val GUIType.Companion.valueMap: Map<String, GUIType<*>> by lazy {
     GUIType.run {
        mapOf(
            "ONE_BY_NINE" to ONE_BY_NINE,
            "TWO_BY_NINE" to TWO_BY_NINE,
            "THREE_BY_NINE" to THREE_BY_NINE,
            "FOUR_BY_NINE" to FOUR_BY_NINE,
            "FIVE_BY_NINE" to FIVE_BY_NINE,
            "SIX_BY_NINE" to SIX_BY_NINE,
            "ONE_BY_FIVE" to ONE_BY_FIVE,
            "THREE_BY_THREE" to THREE_BY_THREE
        )
    }
}
class GUITypeDeserializer: JsonDeserializer<GUIType<*>>() {
    override fun deserialize(
        p: JsonParser,
        ctx: DeserializationContext
    ): GUIType<*> {
        val typeString = p.text.trim().uppercase()
        return GUIType.valueMap[typeString] ?: throw IllegalArgumentException("Unknown GUIType: $typeString")
    }
}