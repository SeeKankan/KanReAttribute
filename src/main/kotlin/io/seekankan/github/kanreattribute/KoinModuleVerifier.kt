package io.seekankan.github.kanreattribute

import org.koin.core.module.dsl.singleOf

//class KoinModuleVerifier(
//    val pluginModuleManager: PluginModuleManager
//) {
//    fun verifyAll() {
//        try {
//            pluginModuleManager.pluginModules.first().koinModule
//
//        }
//    }
//}
//sealed class VerifyResult {
//    object Success: VerifyResult()
//    data class Failed(
//        val exception: Exception
//    ): VerifyResult()
//}