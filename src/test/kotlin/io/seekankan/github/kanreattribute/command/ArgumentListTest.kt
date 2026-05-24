package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.common.arrayShouldBe
import io.seekankan.github.kanreattribute.common.assertPairEquals
import io.seekankan.github.kanreattribute.common.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArgumentListTest {
    lateinit var args: ArgumentList

    @BeforeEach
    fun initArgumentList() {
        args = ArgumentList(arrayOf(
            "1",
            "2",
            "3",
            "4"
        ))
    }
    @AfterEach
    fun clearArgumentList() {

    }

    @Test
    fun pop() {
        args.run {
            assertPairEquals(
                pop() to "1",
                pop() to "2",
                pop() to "3",
                pop() to "4",
                pop() to null
            )
        }

    }

//    @Test
//    fun peek() {
//        args.run {
//            assertPairEquals(
//                peek() to "1",
//                pop() to "1",
//                pop() to "2",
//                peek() to "3",
//                pop() to "3"
//            )
//        }
//    }

    @Test
    fun hasNext() {
        args.run {
            pop()
            pop()
            pop()
            pop()
            assertPairEquals(
                hasNext() to false
            )
        }
    }

//    @Test
//    fun skip() {
//        args.run {
//            assertPairEquals(
//                pop() to "1"
//            )
//            skip()
//            assertPairEquals(
//                pop() to "3"
//            )
//            skip()
//            skip()
//            assertPairEquals(
//                hasNext() to false
//            )
//        }
//    }

//    @Test
//    fun back() {
//        args.run {
//            assertPairEquals(
//                pop() to run {
//                    back()
//                    pop()
//                }
//            )
//        }
//    }

    @Test
    fun iterator() {
        val args2 = ArgumentList(arrayOf(
            "1",
            "2",
            "3",
            "4"
        ))
        args2.pop()
        args2.pop()
        val iter2 = args2.iterator()
        iter2.next() shouldBe "3"
        iter2.hasNext() shouldBe true
        iter2.next() shouldBe "4"
        iter2.hasNext() shouldBe false
    }

    @Test
    fun toArray() {
        args.pop()
        val arr = args.toArray()
        arr arrayShouldBe arrayOf(
            "2",
            "3",
            "4"
        )
    }

    @Test
    fun getFullArgs() {
        args.pop()
        args.pop()
        args.pop()
        args.fullArgs arrayShouldBe arrayOf(
            "1",
            "2",
            "3",
            "4"
        )
    }

}