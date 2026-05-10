package io.seekankan.github.kanreattribute.command.admin

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.message.Message
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.command.SubCommand
import io.seekankan.github.kanreattribute.item.manager.ItemConditionManager
import io.seekankan.github.kanreattribute.item.manager.ItemFinderManager
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubAdminQueryCommand(
    val plugin: KanReAttribute
): SubCommand(
    command = "query",
    subCommands = listOf(
        SubQueryRegItemFindersCommand(),
        SubQueryRegItemConditionsCommand(),
        SubQueryRegItemTypeCommand(),
        SubQueryRegAttributeCalculatorCommand(),
        SubQueryRegSubAttribute()
    )
) {

}
private fun sendQuery(messageManager: MessageService, sender: CommandSender, registerType: String, regListStr: String) {
    messageManager.sendTo(sender, Message.COMMAND__QUERY_REGISTERED, "register_type" to registerType, "register_list" to regListStr)
}

class SubQueryRegItemFindersCommand: SubCommand(
    "regitemfinders"
), KoinComponent {
    val itemFinderManager: ItemFinderManager by inject()

    override fun onSubCommand(sender: CommandSender, command: Command, label: String): Boolean {
        val regItemFinders = itemFinderManager.itemFinderRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageManager, sender, "ItemFinders", regItemFinders)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("ItemFinders", regItemFinders))
        return true
    }
}
class SubQueryRegItemConditionsCommand: SubCommand(
    "regitemconditions"
), KoinComponent {
    val itemConditionManager: ItemConditionManager by inject()

    override fun onSubCommand(sender: CommandSender, command: Command, label: String): Boolean {
        val regItemConditions = itemConditionManager.itemConditionRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageManager, sender, "ItemConditions", regItemConditions)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("ItemConditions", regItemConditions))
        return true
    }
}
class SubQueryRegItemTypeCommand: SubCommand(
    "regitemtypes"
), KoinComponent {
    val itemTypeManager: ItemTypeManager by inject()

    override fun onSubCommand(sender: CommandSender, command: Command, label: String): Boolean {
        val regItemTypes = itemTypeManager.itemTypeRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageManager, sender, "ItemTypes", regItemTypes)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("ItemTypes", regItemTypes))
        return true
    }
}

class SubQueryRegAttributeCalculatorCommand: SubCommand(
    "regattributecalculators"
), KoinComponent {
    val attributeManager: AttributeManager by inject()

    override fun onSubCommand(sender: CommandSender, command: Command, label: String): Boolean {
        val regCalc = attributeManager.attributeCalculatorRegistry.pipeLineView.joinToString {
            it.uniqueName
        }
        sendQuery(messageManager, sender, "AttributeCalculator", regCalc)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("AttributeCalculator", regCalc))
        return true
    }
}

class SubQueryRegSubAttribute: SubCommand(
    "regsubattributes"
), KoinComponent {
    val attributeManager: AttributeManager by inject()

    override fun onSubCommand(sender: CommandSender, command: Command, label: String): Boolean {
        val regAttr = attributeManager.subAttributeRegistry.pipeLineView.joinToString {
            it.uniqueName.toString()
        }
        sendQuery(messageManager, sender, "SubAttributes", regAttr)
//        sender.sendMessage(Message.COMMAND__QUERY_REGISTERED.getMessage("SubAttributes", regAttr))
        return true
    }
}