package io.seekankan.github.kanreattribute.registry

import io.seekankan.github.kanreattribute.common.NamespacedKeyOf

data class RegistrySnapshot<R: Registerable<E, R>, E>(
    val pipeline: List<R> = listOf(),
    val registerableMap: Map<NamespacedKeyOf<E>, R> = mapOf()
) {
    val keyList: List<NamespacedKeyOf<E>> = pipeline.map {
        it.uniqueName
    }
}
