package io.seekankan.github.kanreattribute.registry

import java.util.Collections
import java.util.SortedSet

abstract class AbstractFunctionRegistry<K,V: Named<K>>(
    protected val pipeLineSet: SortedSet<V> = sortedSetOf(),
) {
    val persistentRegisterMap: MutableMap<K,V> = hashMapOf()
    val transientRegisterMap: MutableMap<K,V> = hashMapOf()


    val pipeLineView: SortedSet<V> by lazy {
        Collections.unmodifiableSortedSet(pipeLineSet)
    }

    fun forEach(action: (V) -> Unit) {
        pipeLineSet.forEach { value ->
            action(value)
        }
    }
    private fun register0(value: V, map: MutableMap<K,V>): RegisterResult {
        if(pipeLineSet.contains(value)) {
            onDupeRegister(value)
            return RegisterResult.Failure.Duplicate(value.uniqueName.toString())
        }
        val modifyValue = map.put(value.uniqueName, value)
        pipeLineSet.add(value)
        return RegisterResult.Success //如果是null，null代表他没有覆盖掉map的旧的值，说明是全新的插入，所以true
    }

    open fun registerPersistent(value: V): RegisterResult { //返回值代表是不是新插入的，如果是，则true，如果之前被插入了，那么false
//        if(pipeLineSet.contains(value)) {
//            onDupeRegister(value)
//            return false
//        }
//        val modifyValue = persistentRegisterMap.put(value.uniqueName, value)
//        pipeLineSet.add(value)
//        return modifyValue == null //如果是null，null代表他没有覆盖掉map的旧的值，说明是全新的插入，所以true
        return register0(value, persistentRegisterMap)
    }
    open fun registerTransient(value: V): RegisterResult {
        return register0(value, transientRegisterMap)
    }
    open fun unregister(value: V): UnregisterResult { //若删除成功，则true，反之false
        if(value !in pipeLineSet) return UnregisterResult.Failure.NotFound(value.uniqueName.toString())
        pipeLineSet.remove(value)
//        return deleteValueP != null || deleteValueV != null//如果删除成功，他会返回那个被删除的key，即不为null，返回true
        return UnregisterResult.Success
    }
    open fun clear() {
        persistentRegisterMap.clear()
        transientRegisterMap.clear()
        pipeLineSet.clear()
    }
    open fun clearPersistent() {
        persistentRegisterMap.forEach { (k, v) ->
            pipeLineSet.remove(v)
        }
        persistentRegisterMap.clear()
    }
    open fun clearTransient() {
        transientRegisterMap.forEach { (k, v) ->
            pipeLineSet.remove(v)
        }
        transientRegisterMap.clear()
    }

    open fun <E> forEachMap(map: Map<K,E>, action: (K,V,E?) -> Unit) {//map<string,double>
        pipeLineSet.forEach { named ->
            val uniqueName = named.uniqueName
            action(uniqueName,named,map[uniqueName])
        }
    }
    open fun get(key: K): V? {
        return if(persistentRegisterMap.containsKey(key)) {
            persistentRegisterMap[key]
        } else transientRegisterMap[key]
    }

    protected open fun onDupeRegister(value: V) {

    }

    abstract fun reloadAndClearTransient()
}