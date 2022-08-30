package me.santio.utils.query

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerQuitEvent

object QueryListener: Listener {

    @EventHandler
    private fun onChat(event: AsyncPlayerChatEvent) {
        val query = Query.queries[event.player.uniqueId] ?: return
        event.isCancelled = true

        if (query.isCancellable() && event.message.equals("cancel", true)) {
            query.cancel()
            return
        }

        query.answer(event.message)
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        val query = Query.queries[event.player.uniqueId] ?: return
        query.cancel()
    }

}