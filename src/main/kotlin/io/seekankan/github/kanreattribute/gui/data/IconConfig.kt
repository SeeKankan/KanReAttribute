package io.seekankan.github.kanreattribute.gui.data

import org.bukkit.Material

data class IconConfig(
    val material: Material = Material.BARRIER,
    val name: String = "UNKNOWN NAME",
    val lore: List<String> = listOf(),
    val slots: List<Int> = listOf()
)
