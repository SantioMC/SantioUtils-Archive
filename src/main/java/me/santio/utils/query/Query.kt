package me.santio.utils.query

import me.santio.utils.text.colored
import org.bukkit.entity.Player
import java.util.UUID
import java.util.function.Consumer

@Suppress("unused")
class Query(val player: Player, val message: String) {

    companion object {
        val queries = mutableMapOf<UUID, Query>()
    }

    private var listeners: MutableList<Consumer<String>> = mutableListOf()
    private var delay: Long = -1
    private var cancellable: Boolean = false

    fun onComplete(answer: Consumer<String>): Query {
        listeners.add(answer)
        return this
    }

    fun timeout(seconds: Long): Query {
        this.delay = seconds * 20L
        return this
    }

    fun noTimeout(): Query {
        this.delay = -1
        return this
    }

    fun setTicks(ticks: Long): Query {
        this.delay = ticks
        return this
    }

    @JvmOverloads
    fun cancellable(cancellable: Boolean = true): Query {
        this.cancellable = cancellable
        return this
    }

    fun isCancellable(): Boolean {
        return cancellable
    }

    fun timeout(): Long {
        return delay
    }

    @JvmOverloads
    fun send(onComplete: Consumer<String>? = null): Query {
        queries[player.uniqueId] = this
        player.sendMessage(message.colored())
        player.closeInventory()
        onComplete?.let { listeners.add(it) }
        return this
    }

    fun cancel(): Query {
        queries.remove(player.uniqueId)
        player.sendMessage("&7&oThe query has been cancelled.".colored())
        return this
    }

    fun answer(answer: String): Query {
        listeners.forEach { it.accept(answer) }
        queries.remove(player.uniqueId)
        return this
    }

}