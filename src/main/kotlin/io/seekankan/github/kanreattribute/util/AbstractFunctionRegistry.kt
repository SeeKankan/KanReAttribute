package io.seekankan.github.kanreattribute.util

import java.util.Collections
import java.util.SortedSet

abstract class AbstractFunctionRegistry<K,V: Named<K>>(
    private val pipeLineSet: SortedSet<V> = sortedSetOf(),
) {
    val persistentRegisterMap: MutableMap<K,V> = hashMapOf()
    val volatileRegisterMap: MutableMap<K,V> = hashMapOf()


    val pipeLineView: SortedSet<V> by lazy {
        Collections.unmodifiableSortedSet(pipeLineSet)
    }

    fun forEach(action: (V) -> Unit) {
        pipeLineSet.forEach { value ->
            action(value)
        }
    }
    private fun register0(value: V, map: MutableMap<K,V>): Boolean {
        if(pipeLineSet.contains(value)) {
            onDupeRegister(value)
            return false
        }
        val modifyValue = map.put(value.uniqueName, value)
        pipeLineSet.add(value)
        return modifyValue == null //如果是null，null代表他没有覆盖掉map的旧的值，说明是全新的插入，所以true
    }

    open fun registerPersistent(value: V): Boolean { //返回值代表是不是新插入的，如果是，则true，如果之前被插入了，那么false
//        if(pipeLineSet.contains(value)) {
//            onDupeRegister(value)
//            return false
//        }
//        val modifyValue = persistentRegisterMap.put(value.uniqueName, value)
//        pipeLineSet.add(value)
//        return modifyValue == null //如果是null，null代表他没有覆盖掉map的旧的值，说明是全新的插入，所以true
        return register0(value, persistentRegisterMap)
    }
    open fun registerTransient(value: V): Boolean {
        return register0(value, volatileRegisterMap)
    }
    open fun unregister(value: V): Boolean { //若删除成功，则true，反之false
        val deleteValueP = persistentRegisterMap.remove(value.uniqueName)
        val deleteValueV = volatileRegisterMap.remove(value.uniqueName)
        pipeLineSet.remove(value)
        return deleteValueP != null || deleteValueV != null//如果删除成功，他会返回那个被删除的key，即不为null，返回true
    }
    open fun clear() {
        persistentRegisterMap.clear()
        volatileRegisterMap.clear()
        pipeLineSet.clear()
    }
    open fun clearPersistent() {
        persistentRegisterMap.forEach { (k, v) ->
            pipeLineSet.remove(v)
        }
        persistentRegisterMap.clear()
    }
    open fun clearTransient() {
        volatileRegisterMap.forEach { (k, v) ->
            pipeLineSet.remove(v)
        }
        volatileRegisterMap.clear()
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
        } else volatileRegisterMap[key]
    }

    protected open fun onDupeRegister(value: V) {

    }

}