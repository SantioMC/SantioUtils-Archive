package me.santio.utils.queries;

import lombok.Getter;
import me.santio.utils.SantioUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public final class QueryUtils {
    
    @Getter private static final HashMap<UUID, Query> queries = new HashMap<>();
    private final SantioUtils utils;
    
    public QueryUtils(SantioUtils utils) {
        this.utils = utils;
    }
    
    public void awaitMessage(Player player, Consumer<String> response) {
        awaitMessage(player, response, null, 10);
    }
    
    public void awaitMessage(Player player, Consumer<String> response, Predicate<String> filter) {
        awaitMessage(player, response, filter, 10);
    }
    
    public void awaitMessage(Player player, Consumer<String> response, Predicate<String> filter, int delay) {
        if (queries.containsKey(player.getUniqueId())) queries.get(player.getUniqueId()).delete();
        queries.put(player.getUniqueId(), new Query(player.getUniqueId(), response, filter, new BukkitRunnable() {
            @Override public void run() {
                if (delay <= 0) return;
                queries.remove(player.getUniqueId());
                if (!player.isOnline()) return;
                player.sendMessage("§7The query has automatically been cancelled!");
            }
        }.runTaskLater(utils.getPlugin(), delay*20L)));
    }
    
}
