package io.seekankan.github.kanreattribute.item.manager

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.item.data.ItemTypePDCType
import io.seekankan.github.kanreattribute.item.itemtype.ConfigurationItemType
import io.seekankan.github.kanreattribute.item.registry.ItemTypeRegistry
import io.seekankan.github.kanreattribute.util.getItemData
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.koin.core.component.KoinComponent
import java.io.File
import java.util.logging.Logger

class ItemTypeManager(
    private val plugin: KanReAttribute,
    private val logger: Logger
): KoinComponent {
//    val itemTypePDCNamespaceKey = NamespacedKey(plugin, "itemTypeNamespace")
//    val itemTypePDCNameKey = NamespacedKey(plugin, "itemTypeName")
    val itemTypePDCKey = NamespacedKey(plugin, "itemType")

    val itemTypeRegistry = ItemTypeRegistry(logger)

    fun loadYMLItemTypes(file: File) {
        val yaml = YamlConfiguration.loadConfiguration(file)
        yaml.getKeys(false).forEach { key ->
//            val itemType = yaml.getSerializable(key, ConfigurationItemType::class.java) ?: return@forEach
            val itemType = yaml.get(key) as ItemType
            itemTypeRegistry.registerTransient(itemType)
        }
    }

    fun loadAllYMLItemTypes() {
        val itemDataFolder = plugin.dataFolder.resolve("items")
        if(!itemDataFolder.exists()) {
            itemDataFolder.mkdirs()
            return
        }
        itemDataFolder.walk()
            .filter { it.isFile }
            .filter { it.extension == "yml" }
            .forEach { file ->
                try {
                    plugin.logger.info("Loading ItemType file ${file.absolutePath}")
                    loadYMLItemTypes(file)
                } catch(e: Exception) {
                    plugin.logger.warning("Failed to load ItemType file ${file.absolutePath}")
                    e.printStackTrace()
                }
            }
    }

    fun getItemType(itemStack: ItemStack): ItemType? {
//        val namespace = itemStack.getItemData(itemTypePDCNamespaceKey, PersistentDataType.STRING) ?: return null
//        val name = itemStack.getItemData(itemTypePDCNameKey, PersistentDataType.STRING) ?: return null
        val itemTypeKey = itemStack.getItemData(itemTypePDCKey, ItemTypePDCType) ?: return null
        return itemTypeRegistry.get(itemTypeKey)
    }
    fun getItemType(itemTypeKey: ItemTypeKey?): ItemType? {
        if(itemTypeKey == null) return null
        return itemTypeRegistry.get(itemTypeKey)
    }

    /**
     * 例如:
     * custom:dragonsword:default
     * custom:zombie_drop_sword:with_gemstone
     * some_third_plugin:magic_wand:with_enchanted
     * @return 所有的 ItemType和ItemInstance实例类型字符串，格式为 "item_type_namespace:item_type_key:item_instance"
     */
    fun getAllItemTypeInstanceString(): List<String> {
        return itemTypeRegistry.pipeLineView.flatMap { itemType ->
            itemType.instanceConfig.map { (instanceTypeKey, _) ->
                "${itemType.uniqueName}:${instanceTypeKey}"
            }
        }
    }
}