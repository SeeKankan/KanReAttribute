package io.seekankan.github.kanreattribute.registry

import kotlin.jvm.Throws

interface LifeCycle {

    @Throws(Exception::class)
    fun onBeforeRegister() {}
    @Throws(Exception::class)
    fun onEnable()
    @Throws(Exception::class)
    fun onReload() {}
    @Throws(Exception::class)
    fun onDisable()
}