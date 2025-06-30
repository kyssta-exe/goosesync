package me.kyssta.goosesync.listener;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    private final GooseSync plugin;

    public PlayerConnectionListener(GooseSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getPlayerDataManager().createPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getPlayerDataManager().removePlayerData(event.getPlayer().getUniqueId());
    }
}