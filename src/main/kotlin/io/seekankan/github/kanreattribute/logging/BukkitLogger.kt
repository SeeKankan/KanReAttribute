package io.seekankan.github.kanreattribute.logging

import org.slf4j.helpers.FormattingTuple
import org.slf4j.helpers.MarkerIgnoringBase
import org.slf4j.helpers.MessageFormatter
import java.util.logging.Level
import java.util.logging.Logger

class BukkitLogger(
    name: String,
    private val julLogger: Logger
) : MarkerIgnoringBase() {

    // 构造时一次性解析并缓存短类名，避免每次打印日志都执行字符串分割
    private val shortName: String = name.substringAfterLast('.')

    override fun isTraceEnabled(): Boolean = julLogger.isLoggable(Level.FINEST)
    override fun trace(msg: String?) { julLogger.log(Level.FINEST, "[$shortName] $msg") }
    override fun trace(format: String?, arg: Any?) { formatAndLog(Level.FINEST, format, arg) }
    override fun trace(format: String?, arg1: Any?, arg2: Any?) { formatAndLog(Level.FINEST, format, arg1, arg2) }
    override fun trace(format: String?, vararg arguments: Any?) { formatAndLog(Level.FINEST, format, *arguments) }
    override fun trace(msg: String?, t: Throwable?) { julLogger.log(Level.FINEST, "[$shortName] $msg", t) }

    override fun isDebugEnabled(): Boolean = julLogger.isLoggable(Level.FINE)
    override fun debug(msg: String?) { julLogger.log(Level.FINE, "[$shortName] $msg") }
    override fun debug(format: String?, arg: Any?) { formatAndLog(Level.FINE, format, arg) }
    override fun debug(format: String?, arg1: Any?, arg2: Any?) { formatAndLog(Level.FINE, format, arg1, arg2) }
    override fun debug(format: String?, vararg arguments: Any?) { formatAndLog(Level.FINE, format, *arguments) }
    override fun debug(msg: String?, t: Throwable?) { julLogger.log(Level.FINE, "[$shortName] $msg", t) }

    override fun isInfoEnabled(): Boolean = julLogger.isLoggable(Level.INFO)
    override fun info(msg: String?) { julLogger.log(Level.INFO, "[$shortName] $msg") }
    override fun info(format: String?, arg: Any?) { formatAndLog(Level.INFO, format, arg) }
    override fun info(format: String?, arg1: Any?, arg2: Any?) { formatAndLog(Level.INFO, format, arg1, arg2) }
    override fun info(format: String?, vararg arguments: Any?) { formatAndLog(Level.INFO, format, *arguments) }
    override fun info(msg: String?, t: Throwable?) { julLogger.log(Level.INFO, "[$shortName] $msg", t) }

    override fun isWarnEnabled(): Boolean = julLogger.isLoggable(Level.WARNING)
    override fun warn(msg: String?) { julLogger.log(Level.WARNING, "[$shortName] $msg") }
    override fun warn(format: String?, arg: Any?) { formatAndLog(Level.WARNING, format, arg) }
    override fun warn(format: String?, arg1: Any?, arg2: Any?) { formatAndLog(Level.WARNING, format, arg1, arg2) }
    override fun warn(format: String?, vararg arguments: Any?) { formatAndLog(Level.WARNING, format, *arguments) }
    override fun warn(msg: String?, t: Throwable?) { julLogger.log(Level.WARNING, "[$shortName] $msg", t) }

    override fun isErrorEnabled(): Boolean = julLogger.isLoggable(Level.SEVERE)
    override fun error(msg: String?) { julLogger.log(Level.SEVERE, "[$shortName] $msg") }
    override fun error(format: String?, arg: Any?) { formatAndLog(Level.SEVERE, format, arg) }
    override fun error(format: String?, arg1: Any?, arg2: Any?) { formatAndLog(Level.SEVERE, format, arg1, arg2) }
    override fun error(format: String?, vararg arguments: Any?) { formatAndLog(Level.SEVERE, format, *arguments) }
    override fun error(msg: String?, t: Throwable?) { julLogger.log(Level.SEVERE, "[$shortName] $msg", t) }

    private fun formatAndLog(level: Level, format: String?, vararg arguments: Any?) {
        val ft: FormattingTuple = MessageFormatter.arrayFormat(format, arguments)
        julLogger.log(level, "[$shortName] ${ft.message}", ft.throwable)
    }
}