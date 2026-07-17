package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf

class KeyOfModule: SimpleModule() {

    init {
        addKeyDeserializer(NamespacedKeyOf::class.java, NamespacedKeyOfKeyDeserializer())
    }

}