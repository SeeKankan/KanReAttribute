package io.seekankan.github.kanreattribute.item.registry

import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.util.AbstractPluginFunctionRegistry
import java.util.logging.Logger

class ItemTypeRegistry(
    logger: Logger
): AbstractPluginFunctionRegistry<ItemTypeKey, ItemType>(
    "ItemType",
    logger
) {

}