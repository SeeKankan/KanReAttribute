package io.seekankan.github.kanreattribute.message

import io.seekankan.github.kanreattribute.registry.Registerable
import io.seekankan.github.kanreattribute.registry.RegistrySnapshot
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration

class RegistryMessageService(
    private val messageConfigHolder: MessageConfigHolder,
    private val messageService: MessageService
) {

    fun <R: Registerable<E, R>, E> toKeyComponent(snapshot: RegistrySnapshot<R, E>): Component {
        val messageConfig = messageConfigHolder.currentConfig
        val queryConfig = messageConfig.registry.keyList

        val separator = messageService.getComponent(queryConfig.separator)
        val registerableList = snapshot.pipeline.map { registerable ->
            val namespacedKeyOf = registerable.uniqueName
            val format = if(registerable.isPersistent) {
                queryConfig.persistentRegisterable
            } else {
                queryConfig.transientRegisterable
            }
            messageService.getComponent(format, "namespaced_key" to namespacedKeyOf)
        }
        val joinedRegisterable = Component.join(
            JoinConfiguration.separator(separator),
            registerableList
        )
        val formatList = messageService.getComponent(queryConfig.format, "register_list" to joinedRegisterable)
        return formatList
    }

}