package io.seekankan.github.kanreattribute.item.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.Locale.getDefault

@JsonDeserialize(keyUsing = ItemCategoryDeserializer::class)
enum class ItemCategory {
    GEAR,
    MATERIAL;

    @JsonValue
    fun toValue(): String {
        return this.name
    }
    companion object {
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        @JvmStatic
        fun fromValue(value: String): ItemCategory {
            return ItemCategory.valueOf(value.uppercase(getDefault()))
        }
    }
}
class ItemCategoryDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): Any {
        return ItemCategory.fromValue(key)
    }
}