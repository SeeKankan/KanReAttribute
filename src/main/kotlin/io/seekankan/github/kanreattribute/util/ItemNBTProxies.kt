package io.seekankan.github.kanreattribute.util

import de.tr7zw.nbtapi.wrapper.NBTProxy
import de.tr7zw.nbtapi.wrapper.NBTTarget

interface ItemStackInterface: NBTProxy {
    @get:NBTTarget(value = "display")
    val displayInterface: DisplayInterface
}
interface DisplayInterface: NBTProxy {
    @get:NBTTarget(value = "Name")
    @set:NBTTarget(value = "Name")
    var gsonDisplayName: String

//    @get:NBTTarget(value = "Lore")
//    @set:NBTTarget(value = "Lore")
//    var gsonLore: List<String>
}