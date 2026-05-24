package io.seekankan.github.kanreattribute.common

import org.junit.jupiter.api.Assertions.*

fun assertPairEquals(vararg pair: Pair<*,*>) {
    pair.forEach { (first, second) ->
        assertEquals(second, first)
    }
}

infix fun Any?.shouldBe(excepted: Any?) {
    assertEquals(excepted, this)
}
infix fun Array<*>?.arrayShouldBe(excepted: Array<*>?) {
    assertArrayEquals(excepted, this)
}