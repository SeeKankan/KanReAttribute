package io.seekankan.github.kanreattribute.command.data

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.seekankan.github.kanreattribute.common.ItemInstanceConfigTag
import java.util.concurrent.CompletableFuture

class ItemInstanceArgumentType: StringOfArgumentType<ItemInstanceConfigTag>() {

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        builder.suggest("default")
        return builder.buildFuture()
    }

}