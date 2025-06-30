package me.kyssta.goosesync.listener;

import me.kyssta.goosesync.GooseSync;
import me.kyssta.goosesync.model.PlayerData;
import me.kyssta.goosesync.util.VersionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ConsumptionListener implements Listener {
    private final GooseSync plugin;

    public ConsumptionListener(GooseSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (!plugin.getConfigManager().isEnabled() || !plugin.getConfigManager().isConsumptionEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (playerData == null || playerData.getPing() < plugin.getConfigManager().getPingThreshold()) {
            return;
        }

        ItemStack item = event.getItem();

        if (VersionUtil.isGoldenApple(item) || VersionUtil.isCookedBeef(item)) {
            Integer lastConsumeTicks = playerData.getLastConsumeTicks();
            if (lastConsumeTicks != null && lastConsumeTicks > 8) {
                return;
            }
            
            // Apply consumption speed boost for high ping players
            player.setFoodLevel(player.getFoodLevel() + 1);
            playerData.setLastConsumeTicks(player.getTicksLived());
        }
    }
}