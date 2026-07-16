package io.seekankan.github.kanreattribute.logging

import org.bukkit.plugin.Plugin
import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.Logger
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.helpers.NOPMDCAdapter
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider
import java.util.concurrent.ConcurrentHashMap

class BukkitSLF4JProvider : SLF4JServiceProvider {

    companion object {
        // 静态持有插件实例，由插件主类在 onLoad 时注入
        lateinit var plugin: Plugin
    }

    // 1. 核心组件全部提升为单例成员变量，绝不重复 new
    private val loggerFactory = BukkitLoggerFactory()
    private val markerFactory = BasicMarkerFactory()
    private val mdcAdapter = NOPMDCAdapter()

    override fun getLoggerFactory(): ILoggerFactory = loggerFactory
    override fun getMarkerFactory(): IMarkerFactory = markerFactory
    override fun getMDCAdapter(): MDCAdapter = mdcAdapter
    override fun getRequestedApiVersion(): String = "2.0.99"
    override fun initialize() { /* 初始化逻辑 */ }

    // 内部类：真正的 Logger 工厂
    private inner class BukkitLoggerFactory : ILoggerFactory {
        // 缓存池，保证同一个 name 只创建一个 Logger
        private val loggerMap = ConcurrentHashMap<String, Logger>()

        override fun getLogger(name: String): Logger {
            return loggerMap.computeIfAbsent(name) {
                // 在这里将 plugin.logger 精准注入给 BukkitLogger
                BukkitLogger(name, plugin.logger)
            }
        }
    }
}
