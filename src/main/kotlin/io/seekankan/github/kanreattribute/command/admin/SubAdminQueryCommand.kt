package io.seekankan.github.kanreattribute.command.admin

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.command.ArgumentList
import io.seekankan.github.kanreattribute.command.SubCommand
import io.seekankan.github.kanreattribute.command.commandMapOf
import io.seekankan.github.kanreattribute.item.manager.ItemConditionManager
import io.seekankan.github.kanreattribute.item.manager.ItemFinderManager
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubAdminQueryCommand(
    val plugin: KanReAttribute
): SubCommand<CommandSender>(
    command = "query",
    subCommands = commandMapOf(
        SubQueryRegItemFindersCommand(),
        SubQueryRegItemConditionsCommand(),
        SubQueryRegItemTypeCommand(),
        SubQueryRegAttributeCalculatorCommand(),
        SubQueryRegSubAttribute()
    ),
    requirePermissions = listOf(
        PermissionNode.Admin.Manage
    ),
    usage = "/kra admin query <regitemfinders|regitemconditions|regitemtypes|regattributecalculators|regsubattributes>"
)

private fun sendQuery(messageService: MessageService, sender: CommandSender, registerType: String, regListStr: String) {
//    messageManager.sendTo(sender, Message.COMMAND__QUERY_REGISTERED, "register_type" to registerType, "register_list" to regListStr)
    messageService.sendParsed(sender, "register_type" to registerType, "register_list" to regListStr) {
        command.plugin.queryRegistered
    }
}

class SubQueryRegItemFindersCommand: SubCommand<CommandSender>(
    "regitemfinders",
    requirePermissions = listOf(
        PermissionNode.Admin.Manage
    ),
    usage = "/kra admin query regitemfinders",
), KoinComponent {
    val itemFinderManager: ItemFinderManager by inject()

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        val regItemFinders = itemFinderManager.itemFinderRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageService, sender, "ItemFinders", regItemFinders)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("ItemFinders", regItemFinders))
        return true
    }
}
class SubQueryRegItemConditionsCommand: SubCommand<CommandSender>(
    "regitemconditions",
    requirePermissions = listOf(
        PermissionNode.Admin.Manage
    ),
    usage = "/kra admin query regitemconditions"

), KoinComponent {
    val itemConditionManager: ItemConditionManager by inject()

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        val regItemConditions = itemConditionManager.itemConditionRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageService, sender, "ItemConditions", regItemConditions)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("ItemConditions", regItemConditions))
        return true
    }
}
class SubQueryRegItemTypeCommand: SubCommand<CommandSender>(
    "regitemtypes",
    requirePermissions = listOf(
        PermissionNode.Admin.Manage
    ),
    usage = "/kra admin query regitemtypes"
), KoinComponent {
    val itemTypeManager: ItemTypeManager by inject()

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        val regItemTypes = itemTypeManager.itemTypeRegistry.itemSetView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageService, sender, "ItemTypes", regItemTypes)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("ItemTypes", regItemTypes))
        return true
    }
}

class SubQueryRegAttributeCalculatorCommand: SubCommand<CommandSender>(
    "regattributecalculators",
    requirePermissions = listOf(
        PermissionNode.Admin.Manage
    ),
    usage = "/kra admin query regattributecalculators"
), KoinComponent {
    val attributeManager: AttributeManager by inject()

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        val regCalc = attributeManager.attributeCalculatorRegistry.pipeLineView.joinToString {
            it.uniqueName
        }
        sendQuery(messageService, sender, "AttributeCalculator", regCalc)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("AttributeCalculator", regCalc))
        return true
    }
}

class SubQueryRegSubAttribute: SubCommand<CommandSender>(
    "regsubattributes",
    requirePermissions = listOf(
        PermissionNode.Admin.Manage
    ),
    usage = "/kra admin query regsubattributes"
), KoinComponent {
    val attributeManager: AttributeManager by inject()

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        val regAttr = attributeManager.subAttributeRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageService, sender, "SubAttributes", regAttr)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("SubAttributes", regAttr))
        return true
    }
}