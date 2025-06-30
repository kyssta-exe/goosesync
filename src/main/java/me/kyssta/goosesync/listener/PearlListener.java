package me.kyssta.goosesync.listener;

import me.kyssta.goosesync.GooseSync;
import me.kyssta.goosesync.model.PlayerData;
import me.kyssta.goosesync.util.VersionUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PearlListener implements Listener {
    private final GooseSync plugin;

    public PearlListener(GooseSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!plugin.getConfigManager().isEnabled() || !plugin.getConfigManager().isPearlEnabled()) {
            return;
        }

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }

        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (playerData == null || playerData.getPing() < plugin.getConfigManager().getPingThreshold()) {
            return;
        }

        Integer lastPearlTicks = playerData.getLastPearlTicks();
        if (lastPearlTicks != null && lastPearlTicks > 8) {
            return;
        }

        // Reduce ender pearl cooldown for high ping players (version-safe)
        int currentCooldown = VersionUtil.getCooldown(player, Material.ENDER_PEARL);
        int newCooldown = (int)(currentCooldown * (1 - plugin.getConfigManager().getPearlCooldownReduction()));
        VersionUtil.setCooldown(player, Material.ENDER_PEARL, newCooldown);
        
        playerData.setLastPearlTicks(player.getTicksLived());
    }
}