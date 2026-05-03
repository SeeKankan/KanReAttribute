package io.seekankan.github.kanreattribute.item.message

import com.fasterxml.jackson.annotation.JsonProperty

data class ItemCategoryConfig(
    @field:JsonProperty("display-name") val displayName: String,
    @field:JsonProperty("lore-template") val loreTemplate: String
)