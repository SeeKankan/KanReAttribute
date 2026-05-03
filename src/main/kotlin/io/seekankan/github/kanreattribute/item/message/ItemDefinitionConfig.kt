package io.seekankan.github.kanreattribute.item.message

import com.fasterxml.jackson.annotation.JsonProperty
import io.seekankan.github.kanreattribute.item.data.ItemCategory
import io.seekankan.github.kanreattribute.item.data.ItemSlot

data class ItemDefinitionConfig(
    @field:JsonProperty("slots") val slots: Map<ItemSlot, String>,
    @field:JsonProperty("item-categories") val itemCategories: Map<ItemCategory, ItemCategoryConfig>
)
