package me.kyssta.goosesync.task;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PingUpdateTask extends BukkitRunnable {
    private final GooseSync plugin;

    public PingUpdateTask(GooseSync plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            plugin.getPlayerDataManager().updatePlayerPing(player);
        }
    }
}