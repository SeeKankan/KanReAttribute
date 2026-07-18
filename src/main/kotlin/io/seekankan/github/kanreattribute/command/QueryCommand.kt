package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.seekankan.github.kanreattribute.ExecutableBrigadierCommand
import io.seekankan.github.kanreattribute.command.data.RegistryArgumentType
import io.seekankan.github.kanreattribute.extensions.findLogger
import io.seekankan.github.kanreattribute.message.RegistryMessageService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//format: /kra query <registry key>
class QueryCommand: ExecutableBrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>(), KoinComponent {
    private val logger = findLogger()

    private val registryMessageService: RegistryMessageService by inject()

    override val command: String = "query"
    override val requirePermissions: List<PermissionNode> = listOf(
        PermissionNode.User
    )

    override fun isValidExecutor(sourceStack: CommandSourceStack): Boolean {
        return sourceStack.executor != null
    }

    //效果: 注册的<registry_type>: [foo:bar, kan:hehe]
    override fun buildNode(): LiteralArgumentBuilder<CommandSourceStack> {
        val argumentHolder = Commands.argument("registry", RegistryArgumentType())
            .executes { ctx ->
                val registry = ctx.getArgument("registry", CopyOnWriteRegistry::class.java)
                val snapshot = registry.snapshot

                val keyComponent = registryMessageService.toKeyComponent(snapshot)

                val executor = ctx.source.executor ?: return@executes 0

                messageService.sendParsed(executor, "register_type" to registry.uniqueName, "registerable_list" to keyComponent) {
                    this.command.plugin.queryRegistered
                }

                return@executes Command.SINGLE_SUCCESS
            }

        return Commands.literal(command).then(argumentHolder)
    }

    override fun init(): LiteralArgumentBuilder<CommandSourceStack> {
        return buildNode().requires { ctx ->
            val sender = ctx.sender
            hasPermissions(sender) && isValidSender(ctx) && isValidExecutor(ctx) && otherRequires(ctx)
        }
    }
    override fun handleCommand(ctx: CommandContext<CommandSourceStack>): Int {
        return Command.SINGLE_SUCCESS
    }
}