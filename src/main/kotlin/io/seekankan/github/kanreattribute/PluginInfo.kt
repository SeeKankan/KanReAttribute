package io.seekankan.github.kanreattribute

import com.fasterxml.jackson.core.type.TypeReference
import java.io.File
import java.util.logging.Logger

abstract class PluginInfo {
    abstract val name: String
    abstract val version: String
    abstract val dataFolder: File

    abstract val logger: Logger

    abstract fun saveResource(resourcePath: String, replace: Boolean)

    /**
     * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
     * @return 被解析好的对象
     */
    inline fun <reified T> loadYAML(resourcePath: String): T {
        return loadYAML(T::class.java, resourcePath)
    }

    /**
     * @param clazz 配置文件的class
     * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
     * @return 被解析好的对象
     */
    abstract fun <T> loadYAML(clazz: Class<T>, resourcePath: String): T

    /**
     * @param typeRef 想要转化的类型
     * @param resourcePath 文件的相对路径(比如需要读取resource/foo/bar.yml, 传入foo/bar.yml)
     * @return 被解析好的对象
     */
    abstract fun <T> loadYAML(typeRef: TypeReference<T>, resourcePath: String): T
}