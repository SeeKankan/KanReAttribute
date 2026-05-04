package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.text.DecimalFormat

abstract class ConfigurableSubAttribute(
    private val plugin: KanReAttribute,
    override val uniqueName: AttributeType
): SubAttribute, Displayable {

    open val configFile: File = plugin.dataFolder
        .resolve("attribute")
        .resolve(uniqueName.namespace)
        .resolve(uniqueName.key + ".yml")
    lateinit var configuration: YamlConfiguration

    override val isPersistent: Boolean = true
    override val priority: Int
        get() = configuration.getInt(AttributeKeys.PRIORITY, 0)
    override val minValue: Double
        get() = configuration.getDouble(AttributeKeys.MIN_VALUE, Double.MIN_VALUE)
    override val maxValue: Double
        get() = configuration.getDouble(AttributeKeys.MAX_VALUE, Double.MAX_VALUE)
    override val baseValue: Double
        get() = configuration.getDouble(AttributeKeys.BASE_VALUE, 0.0)
    override val displayName: String
        get() = configuration.getString(AttributeKeys.DISPLAY_NAME, uniqueName.key) ?: uniqueName.key
    protected val formatterConfig: String
        get() = configuration.getString(AttributeKeys.FORMATTER, uniqueName.key) ?: Displayable.DEFAULT_NUMBER_FORMAT_CONFIG
    protected lateinit var formatter: Lazy<DecimalFormat>

    abstract fun getDefaults(): Map<String, Any>
    @Throws(IOException::class)
    protected open fun initConfig() {
        val parentDir = configFile.parentFile
        if(parentDir != null && !parentDir.exists()) {
            val created = parentDir.mkdirs()
            if(!created) {
                plugin.logger.warning("Cannot create directory: " + parentDir.absolutePath)
            }
        }
        if(!configFile.exists()) {
            plugin.logger.info("Create config file: ${configFile.name}")
            configFile.createNewFile()
            val tempConfig = YamlConfiguration()
            val defaults = getDefaults()
            defaults.forEach { (key, value) ->
                tempConfig.set(key, value)
            }
            tempConfig.save(configFile)
        }
        configuration = YamlConfiguration.loadConfiguration(configFile)
        formatter = lazy {
            DecimalFormat(formatterConfig)
        }
    }

    override fun onBeforeRegister() {
        super.onEnable()
        initConfig()
    }
    override fun onEnable() {
        super.onEnable()
//        initConfig()
    }
    override fun onReload() {
        super.onReload()
        initConfig()
    }
    override fun formatValue(value: Double): String {
        return formatter.value.format(value)
    }
}