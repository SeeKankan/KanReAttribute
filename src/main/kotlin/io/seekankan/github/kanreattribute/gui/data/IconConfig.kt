package io.seekankan.github.kanreattribute.gui.data

import org.bukkit.Material

data class IconConfig(
    val material: Material,
    val name: String,
    val lore: List<String> = listOf(),
    val slots: List<Int> = listOf()
)
