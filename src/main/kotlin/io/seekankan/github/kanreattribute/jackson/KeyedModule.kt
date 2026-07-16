package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import org.bukkit.Material

class KeyedModule: SimpleModule() {

    init {
        addSerializer(KeyedSerializer())

        addDeserializer(Material::class.java, MaterialDeserializer())
    }

}