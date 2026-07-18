package io.seekankan.github.kanreattribute.registry

import java.util.function.IntFunction

class PipelineList<R: Registerable<E, R>, E>(
    private val pipelineList: List<R>
): List<R> by pipelineList {
    override fun <T> toArray(generator: IntFunction<Array<out T?>?>): Array<out T?>? {
        return (pipelineList as java.util.Collection<*>).toArray(generator)
    }
}