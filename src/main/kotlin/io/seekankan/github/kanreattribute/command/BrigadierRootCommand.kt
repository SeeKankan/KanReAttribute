package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack

abstract class BrigadierRootCommand: BrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>() {

    fun build(): LiteralCommandNode<CommandSourceStack> {
        val builder = this.init()
        return builder.build()
    }

}