package io.seekankan.github.kanreattribute.attribute

interface Displayable {
    val displayName: String
    fun formatValue(value: Double): String

    companion object {
        const val DEFAULT_NUMBER_FORMAT_CONFIG =  "##.#"
        const val DEFAULT_PERCENT_FORMAT_CONFIG =  "0.00%"
    }
}