package io.seekankan.github.kanreattribute.coroutines.annotation

@Target(AnnotationTarget.FUNCTION)

annotation class BlockingIO

@Target(AnnotationTarget.FUNCTION)
annotation class CPUIntensive

@Target(AnnotationTarget.FUNCTION)
annotation class MainThreadOnly

@Target(AnnotationTarget.FUNCTION)
annotation class LaunchesCoroutine

@Target(AnnotationTarget.FUNCTION)
annotation class EnqueuesTask