package io.seekankan.github.kanreattribute.util

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

object KanRandom: Random(){
    private val delegate
        get() = ThreadLocalRandom.current()
    override fun nextBits(bitCount: Int): Int = delegate.nextInt( bitCount)

    override fun nextInt(): Int = delegate.nextInt()
    override fun nextInt(until: Int): Int = delegate.nextInt(until)
    override fun nextInt(from: Int, until: Int): Int = delegate.nextInt(from, until)

    override fun nextLong(): Long = delegate.nextLong()
    override fun nextLong(until: Long): Long = delegate.nextLong(until)
    override fun nextLong(from: Long, until: Long): Long = delegate.nextLong(from, until)

    override fun nextBoolean(): Boolean = delegate.nextBoolean()

    override fun nextDouble(): Double = delegate.nextDouble()
    override fun nextDouble(until: Double): Double = delegate.nextDouble(until)
    override fun nextDouble(from: Double, until: Double): Double = delegate.nextDouble(from, until)

    override fun nextFloat(): Float = delegate.nextFloat()

    fun chance(chance: Double): Boolean {
        if(chance <= 0.0) return false
        if(chance >= 1.0) return true

        return nextDouble() < chance
    }
    fun roundWithChance(probability: Double): Long {
        if(probability < 0 ) return -roundWithChance(-probability)

        val integral = probability.toLong()
        val fractional = probability - integral

        if(fractional == 0.0) return integral
        return integral + if(chance(fractional)) 1 else 0
    }

    fun generateUUIDFromSeed(seed: ByteArray): UUID {
        return UUID.nameUUIDFromBytes(seed)
    }
    fun generateUUIDFromSeed(seed: String): UUID {
        return generateUUIDFromSeed(seed.toByteArray())
    }
}