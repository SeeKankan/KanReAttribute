package io.seekankan.github.kanreattribute.registry

sealed class ReloadResult {
    object Success: ReloadResult()
    sealed class Failure(val reason: String): ReloadResult() {
        data class ExecutionError(val keyName: String, val exception: Throwable): Failure(
            "An ${exception.toString()} Exception happen on reload/unregister key '$keyName'"
        )
    }
}