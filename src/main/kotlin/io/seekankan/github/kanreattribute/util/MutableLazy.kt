package io.seekankan.github.kanreattribute.util

import kotlin.reflect.KProperty

class MutableLazy<T>(private val initializer: () -> T) {
    private var _value: T? = null
    private var isInitialized = false

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if(!isInitialized) {
            _value = initializer()
            isInitialized = true
        }
        return _value!!
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _value = value
        isInitialized = true
    }
}
fun <T> mutableLazy(initializer: () -> T): MutableLazy<T> {
    return MutableLazy(initializer)
}