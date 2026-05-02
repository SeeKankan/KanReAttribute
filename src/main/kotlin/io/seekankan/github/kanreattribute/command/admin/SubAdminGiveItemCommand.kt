package io.seekankan.github.kanreattribute.command.admin

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.command.CommandUtil
import io.seekankan.github.kanreattribute.command.SubCommand
import io.seekankan.github.kanreattribute.common.ITEM_INSTANCE_CONFIG_KEY_DEFAULT
import io.seekankan.github.kanreattribute.common.ItemInstanceConfigKey
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.common.ItemTypeTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.itemInstConfigKey
import io.seekankan.github.kanreattribute.item.itemcreate.EmptyItemCreateContext
import io.seekankan.github.kanreattribute.item.itemcreate.ItemFactory
import io.seekankan.github.kanreattribute.item.itemcreate.ItemInstanceConfig
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import io.seekankan.github.kanreattribute.message.Message
import net.axay.kspigot.extensions.bukkit.give
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.koin.core.component.inject

class SubAdminGiveItemCommand(
    val plugin: KanReAttribute
): SubCommand(
    command = "giveitem"
) {
    private val itemTypeManager: ItemTypeManager by inject()
    private val itemFactory: ItemFactory by inject()

    private fun sendCorrectUsage(sender: CommandSender) {
        messageManager.sendTo(sender, Message.COMMAND__INVALID_ARGUMENTS)
        messageManager.sendTo(sender,
            Message.COMMAND__CORRECT_USAGE,
            "usage" to "/kanreattribute admin giveitem <item_type>:[item_instance_type] <amount>")
    }
    private fun createItem(itemType: ItemType, instConfigKey: ItemInstanceConfig, amount: Int): ItemStack {
        val itemCreateContext = EmptyItemCreateContext(
            itemType,
            instConfigKey,
            amount
        )
        return itemFactory.createItemStack(itemCreateContext)
    }
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if(sender !is Player) {
            messageManager.sendTo(sender, Message.COMMAND__MUST_BE_PLAYER)
            return true
        }
        if(args.size < 2) {
            sendCorrectUsage(sender)
            return true
        }
        val itemTypeAndInstanceConfig = args[0]
        val itemAmount = args[1].toIntOrNull()
        if(itemAmount == null || itemAmount <= 0) {
            messageManager.sendTo(sender, Message.COMMAND__INVALID_ITEM_AMOUNT, "amount" to args[1])
            return true
        }

        val splitConfig = itemTypeAndInstanceConfig.split(":")


        when (splitConfig.size) {
            2 -> { //input "custom:myitem" create with default instance type
                val itemType: ItemType
                val instConfig: ItemInstanceConfig

                val itemTypeKey = NamespacedKeyOf.fromString<ItemTypeTag>(itemTypeAndInstanceConfig)
                val nItemType = itemTypeManager.getItemType(itemTypeKey)
                if(nItemType == null) {
                    messageManager.sendTo(sender, Message.COMMAND__ITEM_TYPE_NOT_FOUND)
                    return true
                }
                itemType = nItemType
                if(!nItemType.instanceConfig.containsKey(ITEM_INSTANCE_CONFIG_KEY_DEFAULT)) {
                    messageManager.sendTo(sender, Message.COMMAND__ITEM_INSTANCE_NOT_FOUND)
                    return true
                }
                instConfig = nItemType.instanceConfig[ITEM_INSTANCE_CONFIG_KEY_DEFAULT]!!

                sender.give(createItem(itemType, instConfig, itemAmount!!))
            }
            3 -> { //input "custom:myitem:myinsttype"
                val itemType: ItemType
                val instConfig: ItemInstanceConfig

                val itemTypeKey = NamespacedKeyOf.fromString<ItemTypeTag>("${splitConfig[0]}:${splitConfig[1]}")
                val nItemType = itemTypeManager.getItemType(itemTypeKey)
                if(nItemType == null) {
                    messageManager.sendTo(sender, Message.COMMAND__ITEM_TYPE_NOT_FOUND)
                    return true
                }
                itemType = nItemType
                val instConfigKey = itemInstConfigKey(splitConfig[2])
                if(!nItemType.instanceConfig.containsKey(instConfigKey)) {
                    messageManager.sendTo(sender, Message.COMMAND__ITEM_INSTANCE_NOT_FOUND)
                    return true
                }
                instConfig = nItemType.instanceConfig[instConfigKey]!!

                sender.give(createItem(itemType, instConfig, itemAmount!!))
            }
            else -> {
                sendCorrectUsage(sender)
                return true
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (args.size == 1) {
            val list: MutableList<String> = itemTypeManager.getAllItemTypeInstanceString().toMutableList()
            val latest = args[0]
            CommandUtil.filter(list, latest)
            return list
        }
//        val subCommandName = args[0]
//        val subCommand: SubCommand = subCommands.firstOrNull {
//            it.command.equals(subCommandName, ignoreCase = true)
//        } ?: return null
//        val subArgs = args.copyOfRange(1, args.size)
//        return subCommand.onTabComplete(sender, command, alias, subArgs)
        if(args.size == 2) {
            val itemAmount = args[1].toIntOrNull()
            return if (itemAmount == null || itemAmount <= 0) emptyList() else emptyList()
        }
        return emptyList()
    }
}