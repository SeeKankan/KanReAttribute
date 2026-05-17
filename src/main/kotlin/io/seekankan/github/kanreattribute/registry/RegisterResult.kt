package io.seekankan.github.kanreattribute.registry

sealed class RegisterResult {
    object Success: RegisterResult()
    sealed class Failure(val reason: String): RegisterResult() {
        data class Duplicate(val dupeKey: String): Failure("The key '$dupeKey' is already registered")
    }
}