package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.seekankan.github.kanreattribute.ExecutableBrigadierCommand

abstract class BrigadierRootCommand: ExecutableBrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>() {

    fun build(): LiteralCommandNode<CommandSourceStack> {
        val builder = this.init()
        return builder.build()
    }

}