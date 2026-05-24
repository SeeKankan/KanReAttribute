package io.seekankan.github.kanreattribute.message

data class MessageConfig(
    val senderTypes: Map<String, String>,
    val command: CommandConfig
)
data class CommandConfig(
    val common: CommandCommonConfig,
    val mainCommand: CommandMainCommandConfig,
    val plugin: CommandPluginConfig,
    val items: CommandItemConfig

)
data class CommandCommonConfig(
    val noCommand: String,
//    val mustBePlayer: String,
//    val mustBeConsole: String,
    val invalidSenderType: String,
    val noPermission: String,
    val missingPermission: String,
    val invalidArguments: String,
    val correctUsage: String
)
data class CommandMainCommandConfig(
    val introduction: List<String>
)
data class CommandPluginConfig(
    val reloadStart: String,
    val reloadStartBySb: String,
    val reloadSuccess: String,
    val reloadFail: String,

    val queryRegistered: String
)
data class CommandItemConfig(
    val invalidItemAmount: String,
    val itemTypeMissing: String,
    val itemTypeNotFound: String,
    val itemInstanceNotFound: String
)