package io.seekankan.github.kanreattribute.registry

import java.util.Collections
import java.util.SortedSet

open class RegistryPipeLine<K,V: Named<K>>(
    val registerMap: MutableMap<K,V> = hashMapOf(),
    private val pipeLineSet: SortedSet<V> = sortedSetOf(),
) {
    val pipeLineView: SortedSet<V> by lazy {
        Collections.unmodifiableSortedSet(pipeLineSet)
    }

    open fun register(value: V): Boolean { //返回值代表是不是新插入的，如果是，则true，如果之前被插入了，那么false
        if(pipeLineSet.contains(value)) {
            onDupeRegister(value)
            return false
        }
        val modifyValue = registerMap.put(value.uniqueName, value)
        pipeLineSet.add(value)
        return modifyValue == null //如果是null，null代表他没有覆盖掉map的旧的值，说明是全新的插入，所以true
    }
    open fun unregister(value: V): Boolean { //若删除成功，则true，反之false
        val deleteValue = registerMap.remove(value.uniqueName)
        pipeLineSet.remove(value)
        return deleteValue != null //如果删除成功，他会返回那个被删除的key，即不为null，返回true
    }
    open fun clear() {
        registerMap.clear()
        pipeLineSet.clear()
    }
    open fun get(uniqueName: K): V? {
        return registerMap[uniqueName]
    }

    open fun <E> forEachMap(map: Map<K,E>, action: (K,V,E?) -> Unit) {//map<string,double>
        pipeLineSet.forEach { named ->
            val uniqueName = named.uniqueName
            action(uniqueName,named,map[uniqueName])
        }
    }

    protected open fun onDupeRegister(value: V) {

    }

}