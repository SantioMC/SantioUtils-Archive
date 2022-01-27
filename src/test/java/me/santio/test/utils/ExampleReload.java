package me.santio.test.utils;

import me.santio.utils.SupportReloads;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ExampleReload implements Listener {

    @EventHandler
    @SupportReloads
    private void onJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage(event.getPlayer().getDisplayName()+" joined!");
    }

}
