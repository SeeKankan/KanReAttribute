package io.seekankan.github.kanreattribute.extensions

import org.bukkit.entity.Item
import java.util.UUID

fun Item.setOwnerAndThrower(uuid: UUID?) {
    this.owner = uuid
    this.thrower = uuid
}