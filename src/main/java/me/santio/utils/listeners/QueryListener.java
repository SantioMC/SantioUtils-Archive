package me.santio.utils.listeners;

import me.santio.utils.ChatUtils;
import me.santio.utils.queries.Query;
import me.santio.utils.queries.QueryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QueryListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    private void onChat(AsyncPlayerChatEvent event) {
        if (!QueryUtils.getQueries().containsKey(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
        
        Player player = event.getPlayer();
        String message = ChatUtils.strip(event.getMessage());
        Query query = QueryUtils.getQueries().get(player.getUniqueId());
        
        if (message.equalsIgnoreCase("cancel") || message.equalsIgnoreCase("stop")) {
            query.respond(null);
            return;
        }
        
        if (query.getFilter() != null && !query.getFilter().test(message)) player.sendMessage("§cInvalid query! Please try again...");
        else query.respond(message);
    }
    
    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        if (QueryUtils.getQueries().containsKey(event.getPlayer().getUniqueId())) QueryUtils.getQueries().get(event.getPlayer().getUniqueId()).delete();
    }
    
}
