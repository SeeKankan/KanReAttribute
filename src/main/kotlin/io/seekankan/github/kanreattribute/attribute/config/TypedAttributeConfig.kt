package io.seekankan.github.kanreattribute.attribute.config

import com.fasterxml.jackson.annotation.JsonUnwrapped

abstract class TypedAttributeConfig {
    abstract val common: CommonAttributeConfig
    abstract val display: DisplayAttributeConfig
}