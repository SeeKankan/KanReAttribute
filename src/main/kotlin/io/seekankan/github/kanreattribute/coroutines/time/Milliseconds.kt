package io.seekankan.github.kanreattribute.coroutines.time

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.seekankan.github.kanreattribute.coroutines.ScheduleService
import io.seekankan.github.kanreattribute.coroutines.annotation.Delayable

@JvmInline
value class Milliseconds(
    @get:JsonSerialize(using = MillisecondsSerializer::class)
    @get:JsonDeserialize(using = MillisecondsDeserializer::class)
    val value: Long
): DelayTime {
    @Delayable
    override suspend fun delayBy(scheduleService: ScheduleService) {
        scheduleService.delayMillis(this)
    }
}

class MillisecondsSerializer: JsonSerializer<Milliseconds>() {
    override fun serialize(
        value: Milliseconds,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) {
        gen.writeString("${value.value}")
    }

}
class MillisecondsDeserializer: JsonDeserializer<Milliseconds>() {
    override fun deserialize(
        parser: JsonParser,
        ctx: DeserializationContext
    ): Milliseconds {
        val text = parser.text.trim()

        return Milliseconds(text.toLongOrNull() ?: throw IllegalArgumentException("Illegal Milliseconds '$text'"))
    }

}