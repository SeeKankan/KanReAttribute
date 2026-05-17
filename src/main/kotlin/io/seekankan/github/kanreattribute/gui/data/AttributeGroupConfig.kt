package io.seekankan.github.kanreattribute.gui.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import org.bukkit.Material

data class AttributeGroupConfig(
    val material: Material,
    val name: String,
    @param:JsonProperty("show-attributes")
    val showAttributes: List<AttributeType>
) {
}