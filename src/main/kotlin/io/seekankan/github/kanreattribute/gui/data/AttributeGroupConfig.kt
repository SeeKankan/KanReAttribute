package io.seekankan.github.kanreattribute.gui.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import org.bukkit.Material

data class AttributeGroupConfig(
    val priority: Int = 0,
    val material: Material,
    val name: String,
    @field:JsonProperty("show-attributes")
    val showAttributes: List<AttributeType>
): Comparable<AttributeGroupConfig> {
    override fun compareTo(other: AttributeGroupConfig): Int {
        return compareValuesBy(this,
            other,
            { it.priority },
            { it.name },
            { it.material }
            )
    }
}