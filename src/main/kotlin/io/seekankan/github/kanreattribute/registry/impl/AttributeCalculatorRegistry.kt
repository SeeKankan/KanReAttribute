package io.seekankan.github.kanreattribute.registry.impl

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.attributecalculator.AttributeCalculator
import io.seekankan.github.kanreattribute.common.AttributeCalculatorTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.registryKeyOf
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry

class AttributeCalculatorRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<AttributeCalculator, AttributeCalculatorTag>() {
    override val registerableTypeName: String = "AttributeCalculator"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "attribute_calculator")

}