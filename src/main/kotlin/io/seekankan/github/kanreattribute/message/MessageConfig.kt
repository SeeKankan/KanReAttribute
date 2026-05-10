package io.seekankan.github.kanreattribute.message

data class MessageConfig(
    val command: CommandConfig
)
data class CommandConfig(
    val common: CommandCommonConfig,
    val plugin: CommandPluginConfig,
    val items: CommandItemConfig

)
class CommandCommonConfig(
    val noCommand: String,
    val mustBePlayer: String,
    val noPermission: String,
    val invalidArguments: String,
    val correctUsage: String
)
class CommandPluginConfig(
    val reloadStart: String,
    val reloadStartBySb: String,
    val reloadSuccess: String,
    val reloadFail: String,

    val queryRegistered: String
)
class CommandItemConfig(
    val invalidItemAmount: String,
    val itemTypeNotFound: String,
    val itemInstanceNotFound: String
)