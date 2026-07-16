package io.seekankan.github.kanreattribute.registry

import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.extensions.findLogger
import kotlin.math.log

//TODO Logger
abstract class CopyOnWriteRegistry<R: Registerable<E, R>, E> {

    protected val log = findLogger()

    private val writeLock: Any = Any()

    @Volatile
    var snapshot: RegistrySnapshot<R, E> = RegistrySnapshot()
    private set

    protected abstract val registerableTypeName: String

    fun registerAll(values: List<R>): BatchRegisterResult{
        return synchronized(writeLock) {
            val oldSnapshot = snapshot
            val oldPipeline = oldSnapshot.pipeline
            val oldMap = oldSnapshot.registerableMap

            val results = mutableListOf<RegisterResult>()

            val filteredValues = mutableListOf<R>()

            log.info("Trying to register $registerableTypeName(s): ${values.map{ it.uniqueName }.joinToString()}")
            values.forEach { value ->
                if(!oldMap.containsKey(value.uniqueName)) {
                    try {
                        value.onBeforeRegister()
                        filteredValues.add(value)
                    } catch (e: Exception) {
                        log.error("Register $registerableTypeName >>> [${value.uniqueName}] failed while executing onBeforeRegister() method!", e)
                        results.add(RegisterResult.Failure.ExecutionError(value.uniqueName.toString(), e))
                    }
                } else {
                    results.add(RegisterResult.Failure.Duplicate(value.uniqueName.toString()))
                }
            }
            val newMutablePipeline = ArrayList<R>(oldPipeline.size + filteredValues.size)
            newMutablePipeline.addAll(oldPipeline)
            newMutablePipeline.addAll(filteredValues)

            filteredValues.sort()
            newMutablePipeline.sort()

            val exceptionInEnableValues = mutableSetOf<R>()
            filteredValues.forEach { value ->
                try {
                    value.onEnable()
                    results.add(RegisterResult.Success)
                } catch (ex: Exception) {
                    try {
                        log.error("Register $registerableTypeName >>> [${value.uniqueName}] failed while executing onEnable() method!", ex)
                        log.error("Trying to disable $registerableTypeName >>> [${value.uniqueName}]")
                        value.onDisable()
                    } catch (e: Exception) {
                        log.error("An Exception happen on invoking onDisable() method", e)
                        ex.addSuppressed(e)
                    } finally {
                        exceptionInEnableValues.add(value)
                        results.add(RegisterResult.Failure.ExecutionError(value.uniqueName.toString(), ex))
                    }
                }

            }
            newMutablePipeline.removeAll(exceptionInEnableValues)

            val immutablePipeline = newMutablePipeline.toList()
            val immutableMap = newMutablePipeline.associateBy {
                it.uniqueName
            }

            val newSnapshot = RegistrySnapshot(
                immutablePipeline,
                immutableMap
            )
            snapshot = newSnapshot

            val batchResult = BatchRegisterResult(results)

            log.info("Success to register ${batchResult.successCount} $registerableTypeName(s)")
            if(batchResult.failureResults.isNotEmpty()) {
                log.warn("Failed to register ${batchResult.failureResults.size} $registerableTypeName(s)")
                log.warn("Because:")
                log.warn(batchResult.failureResults.joinToString(separator = "\n") { it.reason })
            }

            batchResult
        }
    }
    fun unregisterAll(values: List<R>): BatchUnregisterResult {
        return unregisterAllByKey(values.map {
            it.uniqueName
        })
    }
    fun unregisterAllByKey(keys: List<NamespacedKeyOf<E>>): BatchUnregisterResult {
        return unregisterAllByKey(keys.toSet())
    }
    fun unregisterAllByKey(keys: Set<NamespacedKeyOf<E>>): BatchUnregisterResult {
        return synchronized(writeLock) {
            val oldSnapshot = snapshot
            val oldPipeline = oldSnapshot.pipeline
//            val oldMap = oldSnapshot.registerableMap

            val results = mutableListOf<UnregisterResult>()

            val unremovedKeySet = keys.toMutableSet()
            val newMutablePipeline = ArrayList<R>(oldPipeline.size)


            log.info("Trying to unregister $registerableTypeName(s): ${keys.joinToString()}")
            oldPipeline.forEach { value ->
                val uniqueName = value.uniqueName
                if(uniqueName in keys) { //this value need to remove
                    try {
                        value.onDisable()
                        results.add(UnregisterResult.Success)
                    } catch (e: Exception) {
                        log.error("Unregister $registerableTypeName >>> [${value.uniqueName}] failed while executing onDisable() method!", e)
                        results.add(UnregisterResult.Failure.ExecutionError(uniqueName.toString(), e))
                    } finally {
                        unremovedKeySet.remove(uniqueName)
                    }
                } else {
                    newMutablePipeline.add(value)
                }
            }

            unremovedKeySet.forEach { unremovedKey ->
                results.add(UnregisterResult.Failure.NotFound(unremovedKey.toString()))
            }

            val immutablePipeline = newMutablePipeline.toList()
            val immutableMap = newMutablePipeline.associateBy {
                it.uniqueName
            }

            snapshot = RegistrySnapshot(
                immutablePipeline,
                immutableMap
            )
            val batchResult = BatchUnregisterResult(results)

            log.info("Success to unregister ${batchResult.successCount} $registerableTypeName(s)")
            if(batchResult.failureResults.isNotEmpty()) {
                log.warn("Failed to unregister ${batchResult.failureResults.size} $registerableTypeName(s)")
                log.warn("Because:")
                log.warn(batchResult.failureResults.joinToString(separator = "\n") { it.reason })
            }

            batchResult
        }
    }

    fun reloadAll(): BatchReloadResult {
        return synchronized(writeLock) {
            val oldSnapshot = snapshot
            val oldPipeline = oldSnapshot.pipeline

            val results = mutableListOf<ReloadResult>()

//            val exceptionMap = mutableMapOf<String, Throwable>()
            val newMutablePipeline = ArrayList<R>(oldPipeline.size)

            log.info("Trying to reload ${oldPipeline.size} $registerableTypeName(s)")

            oldPipeline.forEach {
                if(it.isPersistent) {
                    try {
                        it.onReload()
                        results.add(ReloadResult.Success)
                    } catch (e: Exception) {
                        log.error("Reload $registerableTypeName >>> [${it.uniqueName}] failed while executing onReload() method!", e)
//                        exceptionMap[it.uniqueName.toString()] = e
                        results.add(ReloadResult.Failure.ExecutionError(it.uniqueName.toString(), e))
                    } finally {
                        newMutablePipeline.add(it)
                    }
                } else {
                    try {
                        it.onDisable()
                        results.add(ReloadResult.Success)
                    } catch (e: Exception) {
                        log.error("Unregister(because $registerableTypeName isn't persistent) $registerableTypeName >>> [${it.uniqueName}] failed while executing onDisable() method!", e)
//                        exceptionMap[it.uniqueName.toString()] = e
                        results.add(ReloadResult.Failure.ExecutionError(it.uniqueName.toString(), e))
                    }
                }
            }

            newMutablePipeline.sort()
            val immutablePipeline = newMutablePipeline.toList()
            val immutableMap = newMutablePipeline.associateBy {
                it.uniqueName
            }
            val newSnapshot = RegistrySnapshot(
                immutablePipeline,
                immutableMap
            )
            snapshot = newSnapshot

//            val result = if(exceptionMap.isEmpty()) {
//                BatchReloadResult.Success
//            } else BatchReloadResult.Failed.ExecutionError(exceptionMap)
            val batchResult = BatchReloadResult(results)

            log.info("Success to reload/unregister ${batchResult.successCount} $registerableTypeName(s)")
            if(batchResult.failureResults.isNotEmpty()) {
                log.warn("Failed to reload/unregister ${batchResult.failureResults.size} $registerableTypeName(s)")
                log.warn("Because:")
                log.warn(batchResult.failureResults.joinToString(separator = "\n") { it.reason })
            }

            batchResult
        }
    }

}
