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
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.coroutines.CoroutineManager
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry
import io.seekankan.github.kanreattribute.registry.RegistryRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.asCompletableFuture
import net.kyori.adventure.text.Component
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.CompletableFuture

//class RegistryArgumentType(
//    private val filterOnSuggest: Boolean = true
//): CustomArgumentType.Converted<CopyOnWriteRegistry<*, *>, String>, KoinComponent {
//
//    companion object {
//        val ERROR_UNKNOWN_REGISTRY = DynamicCommandExceptionType { namespacedKeyString ->
//            return@DynamicCommandExceptionType MessageComponentSerializer.message().serialize(Component.text("Cannot find $namespacedKeyString registry!"))
//        }
//    }
//
//    private val registryRegistry: RegistryRegistry by inject()
//    private val coroutineManager: CoroutineManager by inject()
//    override fun getNativeType(): ArgumentType<String> {
//        return StringArgumentType.string()
//    }
//
//    override fun <S : Any> listSuggestions(
//        context: CommandContext<S>,
//        builder: SuggestionsBuilder
//    ): CompletableFuture<Suggestions> {
//        val snapshot = registryRegistry.snapshot
//        val inputRegistryKeyString = builder.remainingLowerCase.trim().trimStart('\"').trimEnd('\"')
//        return coroutineManager.asyncIn(Dispatchers.IO) {
//            snapshot.pipeline.filter { registry ->
//                registry.uniqueName.toString().lowercase().startsWith(inputRegistryKeyString)
//            }.forEach { registry ->
//                val registryKey = registry.uniqueName
//                builder.suggest("\"$registryKey\"")
//            }
//            builder.build()
//        }.asCompletableFuture()
//    }
//
//    override fun convert(nativeType: String): CopyOnWriteRegistry<*, *> {
//        val registryKey =  NamespacedKeyOf.fromString<RegistryTag>(nativeType) ?: throw ERROR_INVALID_NAMESPACED_KEY_OF.create(nativeType)
//        val registry = registryRegistry.snapshot.registerableMap[registryKey] ?: throw ERROR_UNKNOWN_REGISTRY.create(nativeType)
//
//        return registry
//    }
//
//}

class RegistryArgumentType: RegisterableArgumentType<CopyOnWriteRegistry<*, *>, RegistryTag>() {
    override val registry: CopyOnWriteRegistry<CopyOnWriteRegistry<*, *>, RegistryTag> by inject<RegistryRegistry>()
}