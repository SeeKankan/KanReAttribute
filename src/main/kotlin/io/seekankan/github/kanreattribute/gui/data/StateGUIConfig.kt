package io.seekankan.github.kanreattribute.gui.data

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.seekankan.github.kanreattribute.gui.valueMap
import net.axay.kspigot.gui.GUIType
import org.bukkit.Material
import java.util.SortedSet
import java.util.TreeSet

data class StateGUIConfig(
    val title: String,
//    @field:JsonDeserialize(using = GUITypeDeserializer::class, contentAs = Any::class)
    val type: String,
    @field:JsonProperty("border-item")
    val borderItemIcon: IconConfig = IconConfig(
        Material.GRAY_STAINED_GLASS_PANE,
        " "
    ),
    @field:JsonProperty("placeholder-item")
    val placeholderItemIcon: IconConfig = IconConfig(
        Material.WHITE_STAINED_GLASS_PANE,
        " "
    ),
    @field:JsonProperty("groups-start")
    val groupStart: Int,
    @field:JsonProperty("groups-end")
    val groupEnd: Int,
    @field:JsonProperty("attribute-groups")
    val attributeGroupMap: Map<String, AttributeGroupConfig> = emptyMap()
) {
    @get:JsonIgnore
    val sortAttributeGroupSet: SortedSet<AttributeGroupConfig> by lazy {
        attributeGroupMap.map { (key, conf) ->
            conf
        }.toSortedSet()
    }
    fun guiType(): GUIType<*> {
        val typeString = type.trim().uppercase()
        return GUIType.valueMap[typeString] ?: throw IllegalArgumentException("Unknown GUIType: $typeString")
    }
}