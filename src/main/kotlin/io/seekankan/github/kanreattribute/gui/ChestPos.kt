package io.seekankan.github.kanreattribute.gui

import org.jetbrains.annotations.Range

/**
 *
 * @constructor x和y
 * x与y使用1索引
 * ---------->x
 * | AAAAAAAAA
 * | AAAAAAAAA
 * | AAAAAAAAA
 * |
 * y
 */
data class ChestPos(
    val x: @Range(from = 1, to = 9) Int,
    val y: @Range(from = 1, to = 6) Int
) {

    init {
        require(x in 1..9) { "x must between 1 and 9, but was $x" }
        require(y in 1..6) { "y must between 1 and 6, but was $y" }
    }

    val absoluteIndex: Int = (y - 1) * 9 + (x - 1)
}

fun Int.toChestPos(): ChestPos {
    return ChestPos(
        this % 9 + 1,
        this / 9 + 1
    )
}