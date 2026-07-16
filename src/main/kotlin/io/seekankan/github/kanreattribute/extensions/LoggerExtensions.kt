package io.seekankan.github.kanreattribute.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Any.findLogger(): Logger {
    return LoggerFactory.getLogger(this.javaClass)
}