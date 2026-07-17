package io.seekankan.github.kanreattribute.attribute.effectapplier

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.effectapplier.config.TypedEffectApplierConfig
import io.seekankan.github.kanreattribute.common.EffectApplierKey
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.jackson.JacksonUtil

abstract class JacksonTypedEffectApplier<T: EventData, E: TypedEffectApplierConfig>(
    pluginInfo: PluginInfo,
    uniqueName: EffectApplierKey,
    eventDataType: Class<T>,
    val effectApplierConfigClass: Class<E>,
): TypedEffectApplier<T, E>(
    pluginInfo, uniqueName, eventDataType
) {

    override val isPersistent: Boolean = true

    override fun writeConfig(config: E) {
        JacksonUtil.yamlMapper.writeValue(configFile, config)
    }
    override fun fetchConfig(): E {
        return JacksonUtil.yamlMapper.readValue(configFile, effectApplierConfigClass)
    }

}