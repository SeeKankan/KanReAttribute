package io.seekankan.github.kanreattribute.command.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import net.kyori.adventure.text.Component

open class NamespacedKeyArgumentType<T>: CustomArgumentType.Converted<NamespacedKeyOf<T>, String> {

    companion object {
        val ERROR_INVALID_NAMESPACED_KEY_OF = DynamicCommandExceptionType { namespacedKeyString ->
            return@DynamicCommandExceptionType MessageComponentSerializer.message().serialize(Component.text("$namespacedKeyString is not a valid namespacedKey"))
        }
    }

    @Throws(CommandSyntaxException::class)
    override fun convert(nativeType: String): NamespacedKeyOf<T> {
        return NamespacedKeyOf.fromString(nativeType) ?: throw ERROR_INVALID_NAMESPACED_KEY_OF.create(nativeType)
    }

    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.string()
    }

}