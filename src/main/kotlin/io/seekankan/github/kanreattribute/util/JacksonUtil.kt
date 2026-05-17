package io.seekankan.github.kanreattribute.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.util.JacksonUtil.yamlMapper
import org.bukkit.plugin.Plugin
import java.io.File

object JacksonUtil {
    val jsonMapper: ObjectMapper = jacksonObjectMapper().applyModule()

    val yamlMapper: ObjectMapper = ObjectMapper(YAMLFactory()).applyModule().apply {
        propertyNamingStrategy = PropertyNamingStrategies.KEBAB_CASE
    }

}
private fun ObjectMapper.applyModule(): ObjectMapper {
//     registerKotlinModule().apply {
//        registerModule()
//
//
//    }
    registerModule(
        KotlinModule.Builder()
            .configure(KotlinFeature.NullToEmptyCollection, true)
            .configure(KotlinFeature.NullToEmptyMap, true)
            .configure(KotlinFeature.NullIsSameAsDefault, true)
            .configure(KotlinFeature.SingletonSupport, true)
            .build()
    )
    registerModule(ParameterNamesModule())
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    addMixIn(GUIType::class.java, GUITypeMixin::class.java)
    return this
}


private fun saveYAMLFile(pluginInfo: PluginInfo, resourcePath: String): File {
    if(!resourcePath.endsWith(".yml")) {
        if(resourcePath.endsWith(".yaml")) {
            pluginInfo.logger.warning("YAML file does not end with .yml")
        } else pluginInfo.logger.warning("File '$resourcePath' is not a YAML file")
    }
    val file = File(pluginInfo.dataFolder, resourcePath)
    if(!file.exists()) {
        pluginInfo.logger.info("Create file $resourcePath")
        pluginInfo.saveResource(resourcePath, false)
    }
    return file
}
/**
 * @param pluginInfo 插件info
 * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
 * @return 被解析好的对象
 */
inline fun <reified T> saveFileAndReadYAML(pluginInfo: PluginInfo, resourcePath: String): T {
    return saveFileAndReadYAML(pluginInfo, T::class.java, resourcePath)
}
/**
 * @param pluginInfo 插件info
 * @param clazz 配置文件的class
 * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
 * @return 被解析好的对象
 */
fun <T> saveFileAndReadYAML(pluginInfo: PluginInfo, clazz: Class<T>, resourcePath: String): T {
    val file = saveYAMLFile(pluginInfo, resourcePath)
    return yamlMapper.readValue(file, clazz)
}

/**
 * @param pluginInfo 插件info
 * @param typeRef 想要转化的类型
 * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
 * @return 被解析好的对象
 */
fun <T> saveFileAndReadYAML(pluginInfo: PluginInfo, typeRef: TypeReference<T>, resourcePath: String): T {
    val file = saveYAMLFile(pluginInfo, resourcePath)
    return yamlMapper.readValue(file, typeRef)
}

