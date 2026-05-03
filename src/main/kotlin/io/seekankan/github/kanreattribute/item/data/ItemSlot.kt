package io.seekankan.github.kanreattribute.item.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.Locale.getDefault

@JsonDeserialize(keyUsing = ItemSlotDeserializer::class)
enum class ItemSlot {
    MAIN_HAND,
    OFF_HAND,

    HEAD,
    CHEST,
    LEGS,
    FEET,

    RPG_EQUIPMENT,
    RPG_ACCESSORY,
    RPG_PET,

    OTHERS;

    @JsonValue
    fun toValue(): String {
        return this.name
    }
    companion object {
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        @JvmStatic
        fun fromValue(value: String): ItemSlot {
            return ItemSlot.valueOf(value.uppercase(getDefault()))
        }
    }
}
class ItemSlotDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): Any {
        return ItemSlot.fromValue(key)
    }
}