package io.seekankan.github.kanreattribute.util

inline fun <reified T: Enum<T>> enumValueOfOrDefault(name: String, defaultFun: () -> T): T {
    return try {
        enumValueOf<T>(name)
    } catch (_: IllegalArgumentException) {
        defaultFun()
    }
}