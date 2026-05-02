package io.seekankan.github.kanreattribute.exception

class ConfigurationDeserializeException(
    message: String,
    cause: Throwable? = null
): RuntimeException(message, cause) {
//    companion object {
//        fun missingField(configName: String, field: String, fileLocation: String = ""): ConfigurationDeserializeException {
//            return ConfigurationDeserializeException("$configName is missing required field $field")
//        }
//    }
}