package io.seekankan.github.kanreattribute.attribute.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class AttributeType(
    val namespace: String,
    val key: String
) {
    companion object {
        fun fromString(string: String): AttributeType? {
//            require(string.isNotEmpty()) { "string must not be empty" }
            if(string.isEmpty()) return null
            val parts = string.split(":", limit = 2)

//            require(parts.size == 2) { "Invalid format. Expected 'namespace:key' but got $parts" }
            if(parts.size != 2) return null

            val namespace = parts[0]
            val key = parts[1]
            return AttributeType(namespace, key)
        }

        @JvmStatic
        @JsonCreator
        fun create(string: String): AttributeType {
            require(string.isNotEmpty()) { "string must not be empty" }
            val parts = string.split(":", limit = 2)

            require(parts.size == 2) { "Invalid format. Expected 'namespace:key' but got $parts" }

            val namespace = parts[0]
            val key = parts[1]
            return AttributeType(namespace, key)
        }
    }


//    init {
//        Preconditions.checkArgument(
//            VALID_NAMESPACE.matcher(namespace).matches(),
//            "Invalid namespace. Must be [a-z0-9._-]: %s",
//            namespace
//        )
//        Preconditions.checkArgument(
//            VALID_KEY.matcher(key).matches(),
//            "Invalid key. Must be [a-z0-9/._-]: %s",
//            key
//        )
//        val string = toString()
//        Preconditions.checkArgument(string.length < 256, "NamespacedKey must be less than 256 characters", string)
//    }

    override fun toString(): String {
        return this.namespace + ":" + this.key
    }
    @JsonValue
    fun serialize(): String {
        return this.namespace + ":" + this.key
    }

}