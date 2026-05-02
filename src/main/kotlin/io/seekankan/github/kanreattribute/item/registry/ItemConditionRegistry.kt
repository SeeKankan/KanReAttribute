package io.seekankan.github.kanreattribute.item.registry

import io.seekankan.github.kanreattribute.common.ItemConditionKey
import io.seekankan.github.kanreattribute.item.condition.ItemCondition
import io.seekankan.github.kanreattribute.util.AbstractPluginFunctionRegistry
import java.util.logging.Logger

class ItemConditionRegistry(
    logger: Logger
): AbstractPluginFunctionRegistry<ItemConditionKey, ItemCondition>(
    "ItemFinder",
    logger
) {

}