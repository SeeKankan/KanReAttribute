package io.seekankan.github.kanreattribute.gui

import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.gui.data.AttributeGroupConfig
import net.axay.kspigot.gui.ForEveryInventory
import net.axay.kspigot.gui.ForInventory
import net.axay.kspigot.gui.GUI
import net.axay.kspigot.gui.GUIPageBuilder
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.InventorySlotCompound
import net.axay.kspigot.gui.SingleInventorySlot
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.elements.GUIRectSpaceCompound
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.name
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StateGUIService(
    private val messageManager: MessageService,
    private val attributeManager: AttributeManager,
    private val guiProtectService: GUIProtectService,
    private val config: GUIConfig
) {
    fun createGUI(player: Player): GUI<*> {
        val config = config.stateGUIConfig
        val type = config.guiType() as GUIType<ForEveryInventory>
        val gui = kSpigotGUI(type) {
            title = messageManager.toLegacyText(config.title, "player_name" to player.name)
//            page(1) {
//                val compound = createRectCompound<Material>(
//                    Slots.RowOneSlotOne, Slots.RowSixSlotEight,
//                    iconGenerator = {
//                        ItemStack(it)
//                    },
//                    onClick = { clickEvent, element ->
//                        clickEvent.player.sendMessage("")
//                    }
//                )
//                val materials = Material.entries.filter {
//                    !it.isItem
//                }.toList()
//                compound.addContent(materials)
//            }
            page(1) {
                setBackground()
                renderIcons(player)
//                fillPlaceholder()
            }
            onClickElement {
//                val bukkitInventory = it.bukkitEvent.inventory
                it.bukkitEvent.isCancelled = true
            }

        }
        return gui
    }
    fun openGUI(player: Player): GUI<*> {
        val gui = createGUI(player)
        val topView = player.openGUI(gui)?.topInventory
        topView?.let { guiProtectService.add(it) }
        return gui
    }
    private fun <T : ForEveryInventory> GUIPageBuilder<T>.fillPlaceholder() {
        val icon = config.stateGUIConfig.placeholderItemIcon
        val fillerStack = itemStack(icon.material) {
            this.itemMeta = this.itemMeta?.apply {
                name = icon.name
            }
        }
        placeholder(Slots.All as InventorySlotCompound<T>, fillerStack)

    }
    private fun GUIPageBuilder<*>.setBackground() {
        val icon = config.stateGUIConfig.borderItemIcon
        val fillerStack = itemStack(icon.material) {
            this.itemMeta = this.itemMeta?.apply {
                name = icon.name
            }
        }

        (this as GUIPageBuilder<ForEveryInventory>).placeholder(Slots.Border, fillerStack)
    }
    private fun <T : ForInventory> GUIPageBuilder<T>.renderIcons(player: Player) {
//        config.stateGUIConfig.attributeGroupMap.values.map { groupConfig ->
//            val playerAttributeData = attributeManager.getLivingEntityAttribute(player)
//            val attributeIcon = itemStack(groupConfig.material) {
//                this.itemMeta = this.itemMeta?.apply {
//                    name = messageManager.toLegacyText(groupConfig.name)
//                    val lore = groupConfig.showAttributes.map {
//                        it to attributeManager.subAttributeRegistry.get(it)
//                    }.filter {
//                        it.component2() is Displayable
//                    }.map {
//                        val (attrType, attr) = it
//                        val displayName = messageManager.toLegacyText((attr as Displayable).displayName)
//                        val formatAttributeValue = playerAttributeData.getOrDefault(attrType, attr.baseValue)
//                        "$displayName: $formatAttributeValue"
//                    }
//                    setLore(lore)
//                }
//            }
//            groupConfig.slots to attributeIcon
//        }.forEach { (slots, icon) ->
//            slots.forEach { slot ->
//                placeholder(Slots.of(slot) as InventorySlotCompound<T>, icon)
//            }
//        }
        val compound = createAttributeCompound(player)
//        config.stateGUIConfig.attributeGroupMap.forEach { (_, config) ->
//            compound.addContent(player to config)
//        }
        config.stateGUIConfig.sortAttributeGroupSet.forEach {
            compound.addContent(player to it)
        }
    }
    private fun genIcon(pair: Pair<Player, AttributeGroupConfig>): ItemStack {
        val (player, groupConfig) = pair
        val playerAttributeData = attributeManager.getLivingEntityAttribute(player)
        val attributeIcon = itemStack(groupConfig.material) {
            this.itemMeta = this.itemMeta?.apply {
                name = messageManager.toLegacyText(groupConfig.name)
                val lore = groupConfig.showAttributes.map {
                    it to attributeManager.subAttributeRegistry.get(it)
                }.filter {
                    it.component2() is Displayable
                }.map {
                    val (attrType, attr) = it
                    val displayName = messageManager.toLegacyText((attr as Displayable).displayName)
                    val formatAttributeValue = playerAttributeData.getOrDefault(attrType, attr.baseValue)
                    "$displayName: $formatAttributeValue"
                }
                setLore(lore)
            }
        }
        return attributeIcon

    }
    private fun <T : ForInventory> GUIPageBuilder<T>.createAttributeCompound(player: Player): GUIRectSpaceCompound<T, Pair<Player, AttributeGroupConfig>> {
        val stateConfig = config.stateGUIConfig
        val compound = createRectCompound(
            Slots.of(stateConfig.groupStart) as SingleInventorySlot<out T>,
            Slots.of(stateConfig.groupEnd) as SingleInventorySlot<out T>,
            ::genIcon
        ) { clickEvent, element ->
            clickEvent.bukkitEvent.isCancelled = true
        }
//        compoundSpace(
//            KanSlots.Center as InventorySlotCompound<T>,
//            compound
//        )
        return compound
    }
}