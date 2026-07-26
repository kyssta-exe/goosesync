package me.kyssta.goosesync.listener;

import me.kyssta.goosesync.GooseSync;
import me.kyssta.goosesync.config.ConfigManager;
import me.kyssta.goosesync.model.PlayerData;
import me.kyssta.goosesync.util.KnockbackUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class CombatListener implements Listener {
    private static final int DAMAGE_WINDOW_TICKS = 20;

    private final GooseSync plugin;

    public CombatListener(GooseSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerVelocity(PlayerVelocityEvent event) {
        ConfigManager config = plugin.getConfigManager();
        if (!config.isEnabled() || !config.isKnockbackEnabled()) {
            return;
        }

        Player victim = event.getPlayer();
        PlayerData playerData = plugin.getPlayerDataManager().getOrCreatePlayerData(victim);
        if (!playerData.shouldCompensate(config.getPingThreshold())) {
            return;
        }

        if (!isRecentPlayerAttack(victim, playerData)) {
            return;
        }

        event.setVelocity(KnockbackUtil.reduceHorizontal(event.getVelocity(), config.getKnockbackMultiplier()));
    }

    private boolean isRecentPlayerAttack(Player victim, PlayerData playerData) {
        EntityDamageEvent damageCause = victim.getLastDamageCause();
        if (!(damageCause instanceof EntityDamageByEntityEvent)) {
            return false;
        }

        Entity damager = ((EntityDamageByEntityEvent) damageCause).getDamager();
        if (!(damager instanceof Player)) {
            return false;
        }

        return playerData.isWithinTicks(playerData.getLastDamageTicks(), victim.getTicksLived(), DAMAGE_WINDOW_TICKS);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getConfigManager().isEnabled()) {
            return;
        }

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player victim = (Player) event.getEntity();
        plugin.getPlayerDataManager().getOrCreatePlayerData(victim).setLastDamageTicks(victim.getTicksLived());
    }
}
