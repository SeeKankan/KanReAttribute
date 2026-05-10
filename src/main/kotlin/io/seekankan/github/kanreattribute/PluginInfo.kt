package io.seekankan.github.kanreattribute

import java.io.File
import java.util.logging.Logger

abstract class PluginInfo {
    abstract val name: String
    abstract val version: String
    abstract val dataFolder: File

    abstract val logger: Logger

    abstract fun saveResource(resourcePath: String, replace: Boolean)
}