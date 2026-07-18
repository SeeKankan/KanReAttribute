package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.seekankan.github.kanreattribute.command.data.ItemInstanceArgumentType
import io.seekankan.github.kanreattribute.command.data.ItemTypeArgumentType
import io.seekankan.github.kanreattribute.command.extensions.applyChild
import io.seekankan.github.kanreattribute.common.ItemInstanceConfigKey
import io.seekankan.github.kanreattribute.extensions.spawnItemStacksOnFeet
import io.seekankan.github.kanreattribute.item.itemcreate.EmptyItemCreateContext
import io.seekankan.github.kanreattribute.item.itemcreate.ItemFactory
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.permission.PermissionNode
import net.kyori.adventure.text.Component
import org.bukkit.entity.HumanEntity
import org.koin.core.component.inject

// "/kanreattribute admin giveitem <item_type> [item_instance_type] <amount>"
class GiveItemCommand: BaseBrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>() {

    companion object {
        val ERROR_UNKNOWN_ITEM_INSTANCE = DynamicCommandExceptionType { namespacedKeyString ->
            return@DynamicCommandExceptionType MessageComponentSerializer.message().serialize(Component.text("Unknown item instance key: $namespacedKeyString"))
        }
    }

    private val itemFactory: ItemFactory by inject()

    override val command: String = "giveitem"

    override val requirePermissions: List<PermissionNode> = listOf(
        PermissionNode.Item.Give
    )

    override fun isValidExecutor(sourceStack: CommandSourceStack): Boolean {
        return sourceStack.executor is HumanEntity
    }

    fun buildNode(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal(command)
    }

    override fun init(): LiteralArgumentBuilder<CommandSourceStack> {
        return buildNode().apply {
            requires { ctx ->
                checkRequires(ctx)
            }
        }.apply {
            applyChild(
                Commands.argument("item_type", ItemTypeArgumentType()).applyChild(
                    Commands.argument("item_instance", ItemInstanceArgumentType()).applyChild(
                        Commands.argument("amount", IntegerArgumentType.integer(1, 32767))
                            .suggests { context, builder ->
                                listOf(
                                    1, 16, 32, 64
                                ).forEach {
                                    builder.suggest(it)
                                }
                                builder.buildFuture()
                            }
                            .executes { ctx ->
                                handleCommand(ctx)
                            }
                    )
                )
            )
        }.apply {
            applyChildren(this)
        }
    }

    private fun handleCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val itemType = ctx.getArgument("item_type", ItemType::class.java)
        val itemInstanceKey = ctx.getArgument("item_instance", ItemInstanceConfigKey::class.java)
        val amount = IntegerArgumentType.getInteger(ctx, "amount")

        val itemInstance = itemType.instanceConfig[itemInstanceKey] ?: throw ERROR_UNKNOWN_ITEM_INSTANCE.create(itemInstanceKey)

        val createContext = EmptyItemCreateContext(
            itemType,
            itemInstance,
            amount
        )

        val itemStack = itemFactory.createItemStack(createContext)

        val executor = ctx.source.executor as? HumanEntity ?: return 0

        val overflowedItems = executor.inventory.addItem(itemStack).map { (_, item) ->
            item
        }
        if(overflowedItems.isNotEmpty()) { //some item need to overflow
            executor.spawnItemStacksOnFeet(overflowedItems)
            messageService.sendParsed(executor) {
                this.command.items.itemDropBecauseInventoryFull
            }
        }

        return Command.SINGLE_SUCCESS
    }
}