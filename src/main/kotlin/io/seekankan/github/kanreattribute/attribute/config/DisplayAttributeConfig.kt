package io.seekankan.github.kanreattribute.attribute.config

import io.seekankan.github.kanreattribute.attribute.Displayable

abstract class DisplayAttributeConfig {
    open val displayName: String = "UnnamedAttribute"
    open val formatterConfig: String = Displayable.DEFAULT_NUMBER_FORMAT_CONFIG
}