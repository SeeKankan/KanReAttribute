package io.seekankan.github.kanreattribute.registry

import java.util.logging.Logger

abstract class AbstractPluginFunctionRegistry<K,V>(
    val functionTypeName: String,
    val logger: Logger,
): AbstractFunctionRegistry<K, V>() where V: Named<K>, V: LifeCycle {
    private fun register0(value: V, isPersistent: Boolean, calledSuperRegisterFunction: (V) -> RegisterResult): RegisterResult {
        val typeStr = if(isPersistent) "Persistent" else "Transient"
        logger.info("Register $typeStr $functionTypeName >>> [${value.uniqueName}]!")
        return try {
            value.onBeforeRegister()
            val result = calledSuperRegisterFunction(value)
            if(result is RegisterResult.Failure) return result
            value.onEnable()
            result
        } catch (e: Exception) {
            logger.severe("Register $typeStr $functionTypeName >>> [${value.uniqueName} failed! ${e.message}")
            e.printStackTrace()

            logger.severe("Trying to unregister this $typeStr")
            try {
                value.onDisable()
            } catch (ex: Exception) {
                logger.severe("Unregister $typeStr $functionTypeName >>> [${value.uniqueName} failed! ${ex.message}")
                ex.printStackTrace()
                e.addSuppressed(ex)
            }

            RegisterResult.Failure.ExecutionError(
                value.uniqueName.toString(), e
            )
        }
    }
    override fun registerPersistent(value: V): RegisterResult {
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

    override fun registerTransient(value: V): RegisterResult {
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
    override fun unregister(value: V): UnregisterResult {
        logger.info("Unregister $functionTypeName >>> [${value.uniqueName}]!")
        return try {
            value.onDisable()
            super.unregister(value)
        } catch (e: Exception) {
            logger.severe("Unregister $functionTypeName >>> [${value.uniqueName}] failed! ${e.message}")
            e.printStackTrace()
            UnregisterResult.Failure.ExecutionError(
                value.uniqueName.toString(), e
            )
        }
    }

    override fun onDupeRegister(value: V) {
        super.onDupeRegister(value)
        logger.warning("Same $functionTypeName Id！An $functionTypeName(${value.uniqueName}) was duplicate.")
    }

    override fun reloadAndClearTransient() {
        pipeLineSet.clear()
        persistentRegisterMap.forEach { (k, v) ->
            v.onReload()
        }
        transientRegisterMap.forEach { (k, v) ->
            v.onDisable()
        }
        transientRegisterMap.clear()
        pipeLineSet.addAll(persistentRegisterMap.map {
            it.value
        })
    }
}