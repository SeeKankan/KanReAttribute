package io.seekankan.github.kanreattribute.command

class ArgumentList(
    val fullArgs: Array<String>
): Iterable<String> {
    private var readIndex: Int = 0
    fun pop(): String? {
        return fullArgs.getOrNull(readIndex++)
    }
//    fun peek(): String? {
//        return fullArgs.getOrNull(readIndex)
//    }
    fun hasNext(): Boolean {
        return readIndex < fullArgs.size
    }
//    fun skip() {
//        readIndex++
//    }
//    fun back(){
//        readIndex--
//    }

    override fun iterator(): Iterator<String> {
        return fullArgs.sliceArray(readIndex until fullArgs.size).iterator()
    }
    fun toArray(): Array<String> {
        return fullArgs.sliceArray(readIndex until fullArgs.size)
    }
}