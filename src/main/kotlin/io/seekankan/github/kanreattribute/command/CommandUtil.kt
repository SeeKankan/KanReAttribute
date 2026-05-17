package io.seekankan.github.kanreattribute.command

import java.util.*


class CommandUtil {
    companion object {
//        fun onTabComplete(args: Array<String>, list: MutableList<String>): List<String> {
//            var latest: String? = null
//
//            //你的代码，一般根据args的长度、玩家的权限去查找可能会补全的单词，添加进list即可
//            if (args.isNotEmpty()) {
//                latest = args[args.size - 1]
//            }
//            retainByPrefix(list, latest)
//            return list
//        }

    }
}
fun MutableList<String>.retainByPrefix(prefix: String?) {
    if (this.isEmpty()) return
    if(prefix == null) return
    val ll = prefix.lowercase(Locale.getDefault())
    this.removeIf { k: String -> !k.lowercase(Locale.getDefault()).startsWith(ll) }
}