package io.seekankan.github.kanreattribute.eventhandle

import io.seekankan.github.kanreattribute.attribute.effectapplier.EffectApplier
import io.seekankan.github.kanreattribute.common.EventHandleBehaviorKey
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.extensions.isSuperOrSelfOf
import io.seekankan.github.kanreattribute.registry.LifeCycle
import io.seekankan.github.kanreattribute.registry.Named
import io.seekankan.github.kanreattribute.util.MathUtil

interface EventHandleBehavior<in T>: Named<EventHandleBehaviorKey>,
    Comparable<EventHandleBehavior<*>>,
    LifeCycle {
    val priority: Int
    val targetEventDataClass: Class<in T>

    fun handleEventData(eventData: T)

    override fun compareTo(other: EventHandleBehavior<*>): Int {
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }

    override fun onBeforeRegister() {}
    override fun onEnable() {}
    override fun onReload() {}
    override fun onDisable() {}


    fun <T2> castUnchecked(anotherTargetClass: Class<T2>): EventHandleBehavior<T2> {
        @Suppress("UNCHECKED_CAST")
        return this as EventHandleBehavior<T2>
    }
    fun <T2> castOrNull(anotherTargetClass: Class<T2>): EventHandleBehavior<T2>? {
        return if(targetEventDataClass isSuperOrSelfOf anotherTargetClass) {
            castUnchecked(anotherTargetClass)
        } else null
    }
    fun <T2> castOrThrow(anotherTargetClass: Class<T2>): EventHandleBehavior<T2> {
        return if(targetEventDataClass isSuperOrSelfOf anotherTargetClass) {
            castUnchecked(anotherTargetClass)
        } else throw ClassCastException("Cannot contravariance(in) ${targetEventDataClass.simpleName} to ${anotherTargetClass.simpleName}")
    }
}