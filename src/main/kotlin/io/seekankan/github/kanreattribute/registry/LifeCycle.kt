package io.seekankan.github.kanreattribute.registry

interface LifeCycle {
    fun onBeforeRegister() {}
    fun onEnable()
    fun onReload() {}
    fun onDisable()
}