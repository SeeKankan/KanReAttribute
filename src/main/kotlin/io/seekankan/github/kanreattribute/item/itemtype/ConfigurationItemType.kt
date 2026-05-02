package io.seekankan.github.kanreattribute.item.itemtype

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.ItemInstanceConfigKey
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.common.itemInstConfigKey
import io.seekankan.github.kanreattribute.common.key
import io.seekankan.github.kanreattribute.exception.ConfigurationDeserializeException
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import io.seekankan.github.kanreattribute.item.itemcreate.ItemInstanceConfig
import io.seekankan.github.kanreattribute.message.ItemStyleKey
import io.seekankan.github.kanreattribute.message.wrapTag
import io.seekankan.github.kanreattribute.util.JacksonUtil
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable

data class ConfigurationItemType(
    @field:JsonProperty("unique-name") override val uniqueName: ItemTypeKey,
    @field:JsonProperty("display-name") override val displayName: String = uniqueName.key,
    @field:JsonProperty("priority") override val priority: Int = 0,
    @field:JsonProperty("material") override val material: Material,
    @field:JsonProperty("slots") override val slots: List<ItemSlot> = emptyList(),
    @field:JsonProperty("attributes") override val attrMap: AttributeMap = AttributeMap(),
    @field:JsonProperty("introduction") override val introduction: String? = null,
    @field:JsonProperty("lore") override val lore: List<String> = listOf(ItemStyleKey.FORMAT_FULL_ITEM_LORE.wrapTag()),
    @field:JsonProperty("custom-config") override val customConfig: Map<String, Any> = mapOf(),
    @field:JsonProperty("instance-config") override val instanceConfig: Map<ItemInstanceConfigKey, ItemInstanceConfig> = mapOf(
        itemInstConfigKey("default") to ItemInstanceConfig()
    )
) : ItemType(), ConfigurationSerializable{

    companion object {
//        const val UNIQUE_NAME_KEY = "unique-name"
//        const val PRIORITY_KEY = "priority"
//        const val SLOTS_KEY = "slots"
//        const val ATTRIBUTES_KEY = "attributes"
//        const val CUSTOM_CONFIG_KEY = "custom-config"

        @JvmStatic
        @Suppress("unused")
        @Throws(ConfigurationDeserializeException::class)
        fun deserialize(map: Map<String, Any>): ConfigurationItemType {
            return JacksonUtil.jsonMapper.convertValue(map, ConfigurationItemType::class.java)
//            val koin = GlobalContext.get()
//
//            val namespacedKeyString = map.getValueOrThrow<String>(UNIQUE_NAME_KEY)
//            val uniqueName = NamespacedKey.fromString(namespacedKeyString)?.let { ItemTypeKey(it) }
//            if(uniqueName == null) {
//                throw ConfigurationDeserializeException("Illegal uniqueName format")
//            }
//            val priority = map.getOrDefault(PRIORITY_KEY, 0).let {
//                it as? Int ?: Integer.valueOf(it.toString())
//            }
//            val slots = if(map.containsKey(SLOTS_KEY)) {
//                mutableListOf<ItemSlot>()
//                val slotStringList = map[SLOTS_KEY] as List<*>
//                slotStringList.map {
//                    ItemSlot.valueOf(it.toString())
//                }.toList()
//            } else emptyList()
//            val attrMap = AttributeMap(if(map.containsKey(ATTRIBUTES_KEY)) {
//                val attributeManager = koin.get<AttributeManager>()
//                val subAttrStringMap = map[ATTRIBUTES_KEY] as Map<String, Double?>
//                subAttrStringMap.mapKeys { (attrStrType, attrValue) ->
//                    AttributeType.fromString(attrStrType)
//                }.mapNotNull { (attrType, attrValue) ->
//                    if(attrValue != null) attrType to attrValue
//                    else null
//                }.toMap()
//            } else emptyMap())
//            val customConfig = if(map.containsKey(CUSTOM_CONFIG_KEY)) {
//                map[CUSTOM_CONFIG_KEY] as Map<String, Any>
//            } else emptyMap()
//            return ConfigurationItemType(
//                map,
//                uniqueName,
//                priority,
//                slots,
//                attrMap,
//                customConfig
//            )
        }
    }

    override fun serialize(): Map<String, Any> {
        return JacksonUtil.jsonMapper.convertValue(this, object: TypeReference<Map<String, Any>>(){})
    }

    override fun onEnable() {

    }
    override fun onDisable() {

    }
}
