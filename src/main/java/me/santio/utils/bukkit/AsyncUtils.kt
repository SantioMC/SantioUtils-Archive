package me.santio.utils.bukkit

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class AsyncUtils(private val plugin: JavaPlugin) {
    fun async(code: Consumer<BukkitTask>) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, code)
    }

    fun sync(code: Consumer<BukkitTask>) {
        plugin.server.scheduler.runTask(plugin, code)
    }

    fun delay(code: Consumer<BukkitTask>, ticks: Int) {
        plugin.server.scheduler.runTaskLater(plugin, code, ticks.toLong())
    }

    fun timer(code: Consumer<BukkitTask>, times: Int, delay: Int) {
        val ran = AtomicInteger()
        plugin.server.scheduler.runTaskTimer(plugin, { it: BukkitTask ->
            code.accept(it)
            ran.getAndIncrement()
            if (ran.get() == times) it.cancel()
        }, 0, delay.toLong())
    }
}