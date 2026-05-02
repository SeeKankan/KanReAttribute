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
//            filter(list, latest)
//            return list
//        }

         fun filter(list: MutableList<String>, latest: String?) {
            if (list.isEmpty() || latest == null) return
            val ll = latest.lowercase(Locale.getDefault())
            list.removeIf { k: String -> !k.lowercase(Locale.getDefault()).startsWith(ll) }
        }
    }
}