package me.kyssta.goosesync.listener;

import me.kyssta.goosesync.GooseSync;
import me.kyssta.goosesync.model.PlayerData;
import me.kyssta.goosesync.util.VersionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PotionListener implements Listener {
    private final GooseSync plugin;

    public PotionListener(GooseSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerThrowPotion(PlayerInteractEvent event) {
        if (!plugin.getConfigManager().isEnabled() || !plugin.getConfigManager().isPotionsEnabled()) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = VersionUtil.getMainHandItem(player);

        if (!VersionUtil.isSplashPotion(item)) {
            return;
        }

        PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (playerData == null || playerData.getPing() < plugin.getConfigManager().getPingThreshold()) {
            return;
        }

        Integer lastPotionTicks = playerData.getLastPotionTicks();
        if (lastPotionTicks != null && lastPotionTicks > 8) {
            return;
        }

        // Apply potion throw speed compensation
        playerData.setLastPotionTicks(player.getTicksLived());
    }
}