package io.seekankan.github.kanreattribute.registry

import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.util.MathUtil

interface Registerable<E, T: Registerable<E, T>>: Named<NamespacedKeyOf<E>>, Comparable<T>, LifeCycle {
    val priority: Int
    val isPersistent: Boolean

    override fun compareTo(other: T): Int {
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }
}