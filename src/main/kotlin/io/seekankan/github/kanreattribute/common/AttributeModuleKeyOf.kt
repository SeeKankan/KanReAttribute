package io.seekankan.github.kanreattribute.common

import io.seekankan.github.kanreattribute.PluginInfo

interface AttributeCalculatorTag
interface SubAttributeTag
interface EffectApplierTag

typealias AttributeCalculatorKey = NamespacedKeyOf<AttributeCalculatorTag>
typealias SubAttributeKey = NamespacedKeyOf<SubAttributeTag>
typealias EffectApplierKey = NamespacedKeyOf<EffectApplierTag>

fun attributeCalculatorKeyOf(pluginInfo: PluginInfo, key: String): AttributeCalculatorKey {
    return attributeCalculatorKeyOf(pluginInfo.snakeCaseName, key)
}
fun attributeCalculatorKeyOf(namespace: String, key: String): AttributeCalculatorKey {
    return keyOf(namespace, key)
}

fun subAttributeKeyOf(pluginInfo: PluginInfo, key: String): SubAttributeKey {
    return subAttributeKeyOf(pluginInfo.snakeCaseName, key)
}
fun subAttributeKeyOf(namespace: String, key: String): SubAttributeKey {
    return keyOf(namespace, key)
}

fun effectApplierKeyOf(pluginInfo: PluginInfo, key: String): EffectApplierKey {
    return effectApplierKeyOf(pluginInfo.snakeCaseName, key)
}
fun effectApplierKeyOf(namespace: String, key: String): EffectApplierKey {
    return keyOf(namespace, key)
}