package io.seekankan.github.kanreattribute.attribute.data

import com.fasterxml.jackson.annotation.JsonValue
import io.seekankan.github.kanreattribute.common.SubAttributeKey

class ImmutableAttributeView(
    val map: Map<SubAttributeKey, Double> = emptyMap()
): AttributeView, Map<SubAttributeKey, Double> by map.toMap(){

    @JsonValue
    fun toMap(): Map<SubAttributeKey, Double> {
        return map
    }

}