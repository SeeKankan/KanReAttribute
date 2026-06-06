package io.seekankan.github.kanreattribute.registry

sealed class RegisterResult {
    object Success: RegisterResult()
    sealed class Failure(val reason: String): RegisterResult() {
        data class Duplicate(val dupeKey: String): Failure("The key '$dupeKey' is already registered")
        data class ExecutionError(val keyName: String, val exception: Throwable): Failure(
            "An ${exception.toString()} Exception happen on register key '$keyName'"
        )
    }
}