package io.seekankan.github.kanreattribute.command.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.papermc.paper.command.brigadier.MessageComponentSerializer
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import io.seekankan.github.kanreattribute.command.data.NamespacedKeyArgumentType.Companion.ERROR_INVALID_NAMESPACED_KEY_OF
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.coroutines.CoroutineManager
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry
import io.seekankan.github.kanreattribute.registry.Registerable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.asCompletableFuture
import net.kyori.adventure.text.Component
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.CompletableFuture
import kotlin.getValue

abstract class RegisterableArgumentType<R : Registerable<E, R>, E>: CustomArgumentType.Converted<R, String>, KoinComponent {
    protected val coroutineManager: CoroutineManager by inject()

    abstract val registry: CopyOnWriteRegistry<R, E>
    open val errorUnknownRegisterable  = DynamicCommandExceptionType { namespacedKeyString ->
        return@DynamicCommandExceptionType MessageComponentSerializer.message().serialize(Component.text("Cannot find $namespacedKeyString in ${registry.uniqueName}!"))
    }

    override fun convert(nativeType: String): R {
        val key =  NamespacedKeyOf.fromString<E>(nativeType) ?: throw ERROR_INVALID_NAMESPACED_KEY_OF.create(nativeType)
        val registerable = registry.snapshot.registerableMap[key] ?: throw errorUnknownRegisterable.create(nativeType)

        return registerable
    }

    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.string()
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val snapshot = registry.snapshot
        val inputRegistryKeyString = builder.remainingLowerCase.trim().trimStart('\"').trimEnd('\"')
        return coroutineManager.asyncIn(Dispatchers.IO) {
            snapshot.pipeline.filter { registry ->
                registry.uniqueName.toString().lowercase().startsWith(inputRegistryKeyString)
            }.forEach { registry ->
                val registryKey = registry.uniqueName
                builder.suggest("\"$registryKey\"")
            }
            builder.build()
        }.asCompletableFuture()
    }
}