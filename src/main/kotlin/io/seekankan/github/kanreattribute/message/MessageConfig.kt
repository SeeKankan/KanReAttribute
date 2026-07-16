package io.seekankan.github.kanreattribute.message

import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

data class MessageConfig(
    val senderTypes: Map<String, String> = mapOf(
        CommandSender::class.java.simpleName to "命令执行者",
        Entity::class.java.simpleName to "实体",
        LivingEntity::class.java.simpleName to "有生命的实体",
        HumanEntity::class.java.simpleName to "玩家或者NPC",
        Player::class.java.simpleName to "玩家",
    ),
    val command: CommandConfig = CommandConfig()
)
data class CommandConfig(
    val common: CommandCommonConfig = CommandCommonConfig(),
    val mainCommand: CommandMainCommandConfig = CommandMainCommandConfig(),
    val plugin: CommandPluginConfig = CommandPluginConfig(),
    val items: CommandItemConfig = CommandItemConfig()

)
data class CommandCommonConfig(
    val noCommand: String = "<red>您输入的命令不正确. 请查阅这个命令的相关使用方法.</red>",
//    val mustBePlayer: String,
//    val mustBeConsole: String,
    val invalidSenderType: String = "<red>只有<sender_type>才能执行此命令!</red>",
    val noPermission: String = "<red>您没有权限使用这个命令!</red>",
    val missingPermission: String = "<red>您缺失这一些权限: [<aqua><missing_permission></aqua>] </red>",
    val invalidArguments: String = "<red>您输入的参数不正确. 请查阅这个命令的相关使用方法.</red>",
    val correctUsage: String = "<green>正确的用法: </green><white><usage></white>"
)
data class CommandMainCommandConfig(
    val introduction: List<String> = listOf(
        "--- <green>KanReAttribute</green> ---",
        "/kra reload - <click:suggest_command:'/kra reload'><red>重载插件</red></click>",
        "<gray>/kra admin - <click:suggest_command:'/kra admin'><red>管理员相关指令</red></click></gray>",
        "/kra state - <click:run_command:'/kra state'><red>查看自身属性</red></click>"
    )
)
data class CommandPluginConfig(
    val reloadStart: String = "<aqua>插件开始重载!</aqua>",
    val reloadStartBySb: String = "<aqua><sender>开始了KanReAttribute插件的重载!</aqua>",
    val reloadSuccess: String = "<green>插件重新加载成功!</green>",
    val reloadFail: String = "<red>插件重新加载失败!</red>",

    val queryRegistered: String = "<white>注册的 <register_type>: [</white><green><register_list><green><white>]</white>"
)
data class CommandItemConfig(
    val invalidItemAmount: String = "<red>无效的物品数量: <amount></red>",
    val itemTypeMissing: String = "<red>缺失物品id.</red>",
    val itemTypeNotFound: String = "<red>未找到物品类型: <item_type></red>",
    val itemInstanceNotFound: String = "<red>未找到属性实例化配置: <item_instance></red>"
)