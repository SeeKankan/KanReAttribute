package io.seekankan.github.kanreattribute.coroutines.time

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import io.seekankan.github.kanreattribute.coroutines.ScheduleService
import io.seekankan.github.kanreattribute.coroutines.annotation.Delayable


@JvmInline
value class Ticks(
//    @get:JsonSerialize(using = TickSerializer::class)
//    @get:JsonDeserialize(using = TickDeserializer::class)
    val value: Long
): DelayTime {

    companion object {

        fun of(text: String): Ticks {
            val text = text.trim()

            val pureNumberTick = text.toLongOrNull()
//        if(text.all { it.isDigit() }) return Ticks(text.toLong())

            if(pureNumberTick != null) return Ticks(pureNumberTick)

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

        @JvmStatic
        @JsonCreator
        fun create(tickString: String): Ticks {
            return of(tickString)
        }

    }

    @Delayable
    override suspend fun delayBy(scheduleService: ScheduleService) {
        scheduleService.delayTicks(this)
    }

    @JsonValue
    fun serialize(): String {
        return "${value}t"
    }

    override fun toString(): String {
        return "${value}t"
    }

}

//class TickSerializer: JsonSerializer<Ticks>() {
//    override fun serialize(
//        value: Ticks,
//        gen: JsonGenerator,
//        provider: SerializerProvider
//    ) {
//        gen.writeString("${value.value}t")
//    }
//
//}
//class TickDeserializer: JsonDeserializer<Ticks>() {
//    override fun deserialize(
//        parser: JsonParser,
//        ctx: DeserializationContext
//    ): Ticks {
//
//    }
//
//}

val ONE_TICK = 1.ticks

inline val Int.ticks get() = Ticks(this.toLong())
inline val Int.tickSeconds get() = Ticks((this * 20).toLong())