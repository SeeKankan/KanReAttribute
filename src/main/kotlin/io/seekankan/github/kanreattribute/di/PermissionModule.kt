package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.permission.PermissionService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class PermissionModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "PermissionModule"

    private val permissionService: PermissionService by inject()

    override val koinModule: Module = module {
        singleOf(::PermissionService)
    }
//    {
//        return module {
//            single<Message>(createdAtStart = true) {
//                Message.loadMessage(plugin)
//                Message.MARK_DO_NOT_USED
//            }
//            single<Config>(createdAtStart = true) {
//                plugin.saveDefaultConfig()
//                Config.snapshotDefaults()
//                Config.loadConfig(plugin)
//                Config
//            }
//        }
//    }

    override fun onEnable() {
        permissionService.loadPermission()
    }

    override fun onReload() {
        permissionService.loadPermission()
    }

}