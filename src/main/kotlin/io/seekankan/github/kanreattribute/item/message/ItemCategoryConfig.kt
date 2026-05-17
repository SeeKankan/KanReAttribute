package io.seekankan.github.kanreattribute.item.message

import com.fasterxml.jackson.annotation.JsonProperty

data class ItemCategoryConfig(
    @param:JsonProperty("display-name") val displayName: String,
    @param:JsonProperty("lore-template") val loreTemplate: String
)