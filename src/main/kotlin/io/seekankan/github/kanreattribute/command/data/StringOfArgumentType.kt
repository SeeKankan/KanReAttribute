package io.seekankan.github.kanreattribute.command.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import io.seekankan.github.kanreattribute.common.StringOf

open class StringOfArgumentType<T>: CustomArgumentType.Converted<StringOf<T>, String> {
    override fun convert(nativeType: String): StringOf<T> {
        return StringOf.fromString(nativeType)
    }

    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.string()
    }
}