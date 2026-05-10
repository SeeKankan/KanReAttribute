package io.seekankan.github.kanreattribute

class PluginReloader(
    private val reloadFunction: () -> (Unit),
) {
    fun reload() {
        reloadFunction()
    }
}