package io.seekankan.github.kanreattribute.eventhandle

import io.seekankan.github.kanreattribute.common.EventHandleBehaviorTag
import io.seekankan.github.kanreattribute.extensions.isSuperOrSelfOf
import io.seekankan.github.kanreattribute.registry.Registerable
import io.seekankan.github.kanreattribute.util.MathUtil

interface EventHandleBehavior<in T>: Registerable<EventHandleBehaviorTag, EventHandleBehavior<*>> {
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