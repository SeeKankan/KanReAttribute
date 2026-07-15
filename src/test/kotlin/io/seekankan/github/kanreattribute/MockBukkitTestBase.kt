package io.seekankan.github.kanreattribute

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock

abstract class MockBukkitTestBase {
    protected lateinit var server: ServerMock
    protected lateinit var plugin: KanReAttribute

    @BeforeEach
    open fun setup() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(MockKanReAttribute::class.java)

        MockBukkit.getOrCreateMock().pluginManager.registerLoadedPlugin(plugin)
    }

    @AfterEach
    open fun teardown() {
        MockBukkit.unmock()
    }
}