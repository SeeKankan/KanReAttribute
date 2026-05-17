package io.seekankan.github.kanreattribute.permission

sealed class PermissionResult {
    object Success: PermissionResult()
    data class Failed(
        val requirePermissions: List<PermissionNode>,
        val missingPermissions: List<PermissionNode>
    ): PermissionResult()

    fun isSuccess(): Boolean {
        return this == Success
    }
}