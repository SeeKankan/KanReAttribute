package io.seekankan.github.kanreattribute.coroutines


@JvmInline
value class Ticks(
    val value: Long
)

val ONE_TICK = 1.ticks

inline val Int.ticks get() = Ticks(this.toLong())
inline val Int.seconds get() = Ticks((this * 20).toLong())