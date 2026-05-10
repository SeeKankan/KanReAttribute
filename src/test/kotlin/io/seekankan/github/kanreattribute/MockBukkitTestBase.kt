package io.seekankan.github.kanreattribute

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.yaml.snakeyaml.Yaml

abstract class MockBukkitTestBase {
    protected lateinit var server: ServerMock
    protected lateinit var plugin: KanReAttribute

    @BeforeEach
    @Suppress("UNREACHABLE_CODE")
    open fun setup() {
        println(
            Yaml::class.java.toString()
        )
        server = MockBukkit.mock()
        plugin = MockBukkit.load(KanReAttribute::class.java)

        MockBukkit.getOrCreateMock().pluginManager.registerLoadedPlugin(plugin)
    }

    @AfterEach
    open fun teardown() {
        MockBukkit.unmock()
    }
}