package io.seekankan.github.kanreattribute.extensions

infix fun Any?.isInstanceOf(clazz: Class<*>): Boolean {
    return clazz.isInstance(this)
}