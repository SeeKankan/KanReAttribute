package io.seekankan.github.kanreattribute.gui

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.gui.data.StateGUIConfig
import io.seekankan.github.kanreattribute.util.saveFileAndReadYAML
import java.io.File

class GUIConfig(
    private val pluginInfo: PluginInfo
) {
    private val guiConfigDir = "gui"

    private val stateGUIConfigFile = File(guiConfigDir, "state.yml").path

    lateinit var stateGUIConfig: StateGUIConfig

    fun loadConfig() {
        stateGUIConfig = saveFileAndReadYAML(pluginInfo, stateGUIConfigFile)
//        plugin.logger.info("stateGUIConfig: $stateGUIConfig")
    }
}