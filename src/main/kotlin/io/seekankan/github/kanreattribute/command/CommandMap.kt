package io.seekankan.github.kanreattribute.command

import org.bukkit.command.CommandSender

class CommandMap internal constructor(
    private val map: Map<String, SubCommand<*>>
) {

    operator fun get(commandName: String): SubCommand<*>? {
        return map[commandName.lowercase()]
    }
    fun matchCommandsByPrefix(commandSender: CommandSender, prefix: String?): List<String> {
        if(map.isEmpty()) return emptyList()
        if(prefix == null) return emptyList()

        return map.filter { (cmdName, subCommand) ->
            cmdName.startsWith(prefix, ignoreCase = true) && subCommand.hasPermissions(commandSender).isSuccess()
        }.keys.toList()
    }

}
fun commandMapOf(vararg commands: SubCommand<*>): CommandMap {
    val map = mutableMapOf<String, SubCommand<*>>()
    commands.forEach {
        map[it.lowerCaseCommand] = it
    }
    return CommandMap(map)
}
fun emptyCommandMap(): CommandMap {
    return CommandMap(emptyMap())
}