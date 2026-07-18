package io.seekankan.github.kanreattribute

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.seekankan.github.kanreattribute.command.BaseBrigadierCommand

abstract class ExecutableBrigadierCommand<T : ArgumentBuilder<CommandSourceStack, T>>: BaseBrigadierCommand<T>() {

    protected abstract fun buildNode(): T


    protected abstract fun handleCommand(ctx: CommandContext<CommandSourceStack>): Int

    override fun init(): T {
        return buildNode().requires { ctx ->
            checkRequires(ctx)
        }.executes { ctx ->
            handleCommand(ctx)
        }.apply {
            applyChildren(this)
        }
    }
}