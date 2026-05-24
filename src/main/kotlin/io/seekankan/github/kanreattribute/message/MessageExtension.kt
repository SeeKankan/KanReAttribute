package io.seekankan.github.kanreattribute.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver


fun Array<out Pair<String, *>>.toPlaceholderArray():  Array<TagResolver>{
    return map { (papiName, value) ->
        if(value is Component) {
            Placeholder.component(papiName, value)
        } else Placeholder.parsed(papiName, value.toString())
    }.toTypedArray()
}

fun Component.splitByNewLine(): List<Component> {
    val lines = mutableListOf<Component>()
    var current: TextComponent? = null

    fun flushLine() {
        if(current != Component.empty()) {
            if(current != null) lines += current!!
        }
        current = null
    }

    fun walk(node: Component, inherited: Style) {
        // Merge parent style into this node's style for correct inheritance.
        val effectiveStyle = inherited.merge(node.style())

        if (node is TextComponent) {
            val parts = node.content().split('\n')
            for (i in parts.indices) {
                val part = parts[i]
                if (part.isNotEmpty()) { //delete part.isNotBlank
                    current = if(current == null) {
                        Component.text(part, effectiveStyle)
                    } else {
                        current!!.append(Component.text(part, effectiveStyle))
                    }

                }
                if (i != parts.lastIndex) {
                    flushLine()
                }
            }
        }

        node.children().forEach { child ->
            walk(child, effectiveStyle)
        }
    }

    walk(this, this.style())
    flushLine()

    return lines
}