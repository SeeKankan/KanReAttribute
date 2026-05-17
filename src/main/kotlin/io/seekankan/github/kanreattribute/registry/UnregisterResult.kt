package io.seekankan.github.kanreattribute.registry

sealed class UnregisterResult {
    object Success: UnregisterResult()
    sealed class Failure(val reason: String): UnregisterResult() {
        data class NotFound(val missingKey: String): Failure("The key '$missingKey' isn't exist")
    }
}