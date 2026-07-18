package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack as PaperCommandSourceStack
import io.seekankan.github.kanreattribute.command.extensions.applyChild
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import io.seekankan.github.kanreattribute.permission.PermissionService
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface BrigadierCommand<T: ArgumentBuilder<PaperCommandSourceStack, T>>: KoinComponent {

    fun init(): T

}