package io.seekankan.github.kanreattribute.coroutines

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize


@JvmInline
value class Ticks(
    @get:JsonSerialize(using = TickSerializer::class)
    @get:JsonDeserialize(using = TickDeserializer::class)
    val value: Long
)

class TickSerializer: JsonSerializer<Ticks>() {
    override fun serialize(
        value: Ticks,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) {
        gen.writeString("${value.value}t")
    }

}
class TickDeserializer: JsonDeserializer<Ticks>() {
    override fun deserialize(
        parser: JsonParser,
        ctx: DeserializationContext
    ): Ticks {
        val text = parser.text.trim()

        if(text.all { it.isDigit() }) return Ticks(text.toLong())

        val suffix = text.last().lowercaseChar()
        val number = text.dropLast(1).toDoubleOrNull() ?: throw IllegalArgumentException("Invalid ticks format: $text")

        val tickNum =  when (suffix) {
            't' -> number.toLong() //Tick
            's' -> (number * 20).toLong() //Second 1s = 20t
            'm' -> (number * 20 * 60).toLong() //Minutes 1m = 1200t
            'h' -> (number * 20 * 3600).toLong() //Hours hm = 72000t
            'd' -> (number * 20 * 24000).toLong() //Minecraft Days 1d = 480000t
            else -> throw IllegalArgumentException("Unknown time unit: $suffix")
        }
        return Ticks(tickNum)
    }

}

val ONE_TICK = 1.ticks

inline val Int.ticks get() = Ticks(this.toLong())
inline val Int.tickSeconds get() = Ticks((this * 20).toLong())