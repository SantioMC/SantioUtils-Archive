package me.santio.utils.queries;

import lombok.Getter;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Query {
    
    @Getter private final UUID player;
    @Getter private final Consumer<String> response;
    @Getter private final Predicate<String> filter;
    @Getter private final BukkitTask expireTask;
    
    public Query(UUID player, Consumer<String> response, Predicate<String> filter, BukkitTask expireTask) {
        this.player = player;
        this.response = response;
        this.filter = filter;
        this.expireTask = expireTask;
    }
    
    public void delete() {
        expireTask.cancel();
        QueryUtils.getQueries().remove(player);
    }
    
    public void respond(String response) {
        this.response.accept(response);
        delete();
    }
    
}
