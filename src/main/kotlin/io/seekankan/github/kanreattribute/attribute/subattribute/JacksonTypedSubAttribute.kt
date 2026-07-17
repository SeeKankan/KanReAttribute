package io.seekankan.github.kanreattribute.attribute.subattribute

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.subattribute.config.TypedAttributeConfig
import io.seekankan.github.kanreattribute.common.AttributeType
import io.seekankan.github.kanreattribute.common.SubAttributeKey
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.jackson.JacksonUtil

abstract class JacksonTypedSubAttribute<T: EventData, E: TypedAttributeConfig>(
    pluginInfo: PluginInfo,
    uniqueName: SubAttributeKey,
    eventDataType: Class<T>,
    val attributeConfigType: Class<E>,
): TypedSubAttribute<T, E>(
    pluginInfo, uniqueName, eventDataType
) {

    override fun writeConfig(config: E) {
        JacksonUtil.yamlMapper.writeValue(configFile, config)
    }
    override fun fetchConfig(): E {
        return JacksonUtil.yamlMapper.readValue(configFile, attributeConfigType)
    }

}