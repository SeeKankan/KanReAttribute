package io.seekankan.github.kanreattribute.permission

sealed class PermissionNode(val node: String) {
    object User: PermissionNode("kra.user")

    sealed class Admin(base: String): PermissionNode("kra.admin.$base") {
        object Manage: Admin("manage")
    }
    sealed class Item(base: String): PermissionNode("kra.item.$base") {
        object Give: Item("give")
    }
}