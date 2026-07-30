package io.seekankan.github.kanreattribute.extensions

fun <T: Throwable> Iterable<T>.toThrowable(): T? {

    val iterator = this.iterator()
    if(!iterator.hasNext()) return null
    val firstThrowable = iterator.next()
    while (iterator.hasNext()) {
        val throwable = iterator.next()
        firstThrowable.addSuppressed(throwable)
    }
    return firstThrowable
}