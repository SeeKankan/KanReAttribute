package io.seekankan.github.kanreattribute.command.extensions

import com.mojang.brigadier.builder.ArgumentBuilder

fun <S, T : ArgumentBuilder<S, T>> ArgumentBuilder<S, T>.applyChild(child: ArgumentBuilder<S, *>): ArgumentBuilder<S, T> {
    return this.then(child)
}