package io.seekankan.github.kanreattribute.util

import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.key
import io.seekankan.github.kanreattribute.common.namespace
import kotlin.math.pow
import kotlin.reflect.KClass

object MathUtil {
    fun <T: Named<K>,K: NamespacedKeyOf<*>> compare(
        self: T,
        selfPriority: Int,
        other: T,
        otherPriority: Int,
    ): Int {
        val num1 = selfPriority.compareTo(otherPriority)
        return if(num1 != 0) num1 else {
            val num2 = self.uniqueName.namespace.compareTo(self.uniqueName.namespace)
            if(num2 != 0) num2 else self.uniqueName.key.compareTo(other.uniqueName.key)
        }
    }

    inline fun <reified T: Number> convertNumber(number: Number): T {
        return when(T::class.java) {
            Byte::class.java -> number.toByte()
            Short::class.java -> number.toShort()
            Int::class.java -> number.toInt()
            Long::class.java -> number.toLong()
            Float::class.java -> number.toFloat()
            Double::class.java -> number.toDouble()
            else -> throw NumberFormatException("Invalid number type")
        } as T
    }
    fun Double.divAndPow(divisor: Double, exponent: Double): Double {
        return (this / divisor).pow(exponent)
    }
}
