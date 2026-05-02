package io.seekankan.github.kanreattribute.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.seekankan.github.kanreattribute.util.JacksonUtil.yamlMapper
import net.axay.kspigot.gui.GUIType
import org.bukkit.plugin.Plugin
import java.io.File

object JacksonUtil {
    val jsonMapper: ObjectMapper = jacksonObjectMapper().applyModule()

    val yamlMapper: ObjectMapper = ObjectMapper(YAMLFactory()).applyModule()

}
private fun ObjectMapper.applyModule(): ObjectMapper {
     registerKotlinModule().apply {
        registerModule(KotlinModule.Builder()
            .configure(KotlinFeature.NullToEmptyCollection, true)
            .configure(KotlinFeature.NullToEmptyMap, true)
            .configure(KotlinFeature.NullIsSameAsDefault, true)
            .build())

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
//    addMixIn(GUIType::class.java, GUITypeMixin::class.java)
    return this
}


private fun saveYAMLFile(plugin: Plugin, resourcePath: String): File {
    if(!resourcePath.endsWith(".yml")) {
        if(resourcePath.endsWith(".yaml")) {
            plugin.logger.warning("YAML file does not end with .yml")
        } else plugin.logger.warning("File '$resourcePath' is not a YAML file")
    }
    val file = File(plugin.dataFolder, resourcePath)
    if(!file.exists()) {
        plugin.logger.info("Create file $resourcePath")
        plugin.saveResource(resourcePath, false)
    }
    return file
}
/**
 * @param plugin 插件
 * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
 * @return 被解析好的对象
 */
inline fun <reified T> saveFileAndReadYAML(plugin: Plugin, resourcePath: String): T {
//    if(!resourcePath.endsWith(".yml")) {
//        if(resourcePath.endsWith(".yaml")) {
//            plugin.logger.warning("YAML file does not end with .yml")
//        } else plugin.logger.warning("File '$resourcePath' is not a YAML file")
//    }
//    val file = File(plugin.dataFolder, resourcePath)
//    if(!file.exists()) {
//        plugin.logger.info("Create file $resourcePath")
//        plugin.saveResource(resourcePath, false)
//    }
//    return yamlMapper.readValue(file, T::class.java)
    return saveFileAndReadYAML(plugin, T::class.java, resourcePath)
}
/**
 * @param plugin 插件
 * @param clazz 配置文件的class
 * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
 * @return 被解析好的对象
 */
fun <T> saveFileAndReadYAML(plugin: Plugin, clazz: Class<T>, resourcePath: String): T {
    val file = saveYAMLFile(plugin, resourcePath)
    return yamlMapper.readValue(file, clazz)
}

/**
 * @param plugin 插件
 * @param typeRef 想要转化的类型
 * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
 * @return 被解析好的对象
 */
fun <T> saveFileAndReadYAML(plugin: Plugin, typeRef: TypeReference<T>, resourcePath: String): T {
    val file = saveYAMLFile(plugin, resourcePath)
    return yamlMapper.readValue(file, typeRef)
}

