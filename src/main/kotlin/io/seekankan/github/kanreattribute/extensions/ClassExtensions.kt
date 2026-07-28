package io.seekankan.github.kanreattribute.extensions

infix fun Any?.isInstanceOf(clazz: Class<*>): Boolean {
    return clazz.isInstance(this)
}
infix fun Class<*>.isSuperOrSelfOf(clazz: Class<*>): Boolean {
    return this.isAssignableFrom(clazz)
}
infix fun Class<*>.isSubOrSelfOf(clazz: Class<*>): Boolean {
    return clazz.isAssignableFrom(this)
}