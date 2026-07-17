package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.attribute.LivingEntityAttributeCache
import io.seekankan.github.kanreattribute.helper.PlayerPreAttackCooldownCache
import io.seekankan.github.kanreattribute.util.TransientEntityDataCache
import io.seekankan.github.kanreattribute.util.TransientEntityDataCacheManager
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class EntityHelperModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "EntityHelperModule"

    override val koinModule: Module = module {

        singleOf(::TransientEntityDataCacheManager)
        singleOf(::PlayerPreAttackCooldownCache) bind TransientEntityDataCache::class

        singleOf(::LivingEntityAttributeCache) bind TransientEntityDataCache::class

    }

    override fun onEnable() {
        val koin = getKoin()

        val cacheManager = koin.get<TransientEntityDataCacheManager>()
        val caches = koin.getAll<TransientEntityDataCache<*, *>>()

        cacheManager.registerAll(caches)
    }

    override fun onReload() {

    }

}