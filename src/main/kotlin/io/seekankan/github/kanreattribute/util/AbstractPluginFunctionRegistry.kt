package io.seekankan.github.kanreattribute.util

import java.util.logging.Logger

abstract class AbstractPluginFunctionRegistry<K,V>(
    val functionTypeName: String,
    val logger: Logger,
): AbstractFunctionRegistry<K, V>() where V: Named<K>, V: LifeCycle {
    private fun register0(value: V, isPersistent: Boolean, calledSuperRegisterFunction: (V) -> Boolean): Boolean {
        val typeStr = if(isPersistent) "Persistent" else "Transient"
        logger.info("Register $typeStr $functionTypeName >>> [${value.uniqueName}]!")
        return try {
            val b = calledSuperRegisterFunction(value)
            value.onEnable()
            b
        } catch (e: Exception) {
            logger.severe("Register $typeStr $functionTypeName >>> [${value.uniqueName} failed! ${e.message}")
            e.printStackTrace()
            false
        }
    }
    override fun registerPersistent(value: V): Boolean {
//        logger.info("Register $functionTypeName >>> [${value.uniqueName}]!")
//        return try {
//            val b = super.registerPersistent(value)
//            value.onEnable()
//            b
//        } catch (e: Exception) {
//            logger.severe("Register $functionTypeName >>> [${value.uniqueName} failed! ${e.message}")
//            e.printStackTrace()
//            false
//        }
        return register0(value,true) {
            super.registerPersistent(value)
        }
    }

    override fun registerTransient(value: V): Boolean {
//        logger.info("Register $functionTypeName >>> [${value.uniqueName}]!")
//        return try {
//            val b = super.registerPersistent(value)
//            value.onEnable()
//            b
//        } catch (e: Exception) {
//            logger.severe("Register $functionTypeName >>> [${value.uniqueName} failed! ${e.message}")
//            e.printStackTrace()
//            false
//        }
        return register0(value,false) {
            super.registerTransient(value)
        }
    }
    override fun unregister(value: V): Boolean {
        logger.info("Unregister $functionTypeName >>> [${value.uniqueName}]!")
        return try {
            value.onDisable()
            super.unregister(value)
        } catch (e: Exception) {
            logger.severe("Unregister $functionTypeName >>> [${value.uniqueName}] failed! ${e.message}")
            e.printStackTrace()
            false
        }
    }

    override fun onDupeRegister(value: V) {
        super.onDupeRegister(value)
        logger.warning("Same $functionTypeName Id！An $functionTypeName(${value.uniqueName}) was duplicate.")
    }
}