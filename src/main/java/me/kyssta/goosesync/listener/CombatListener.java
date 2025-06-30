package me.kyssta.goosesync.listener;

import me.kyssta.goosesync.GooseSync;
import me.kyssta.goosesync.model.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

public class CombatListener implements Listener {
    private final GooseSync plugin;

    public CombatListener(GooseSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerVelocity(PlayerVelocityEvent event) {
        if (!plugin.getConfigManager().isEnabled()) {
            return;
        }

        Player victim = event.getPlayer();
        EntityDamageEvent entityDamageEvent = victim.getLastDamageCause();
        
        if (entityDamageEvent == null) {
            return;
        }

        if (entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }

        Entity attacker = ((EntityDamageByEntityEvent)entityDamageEvent).getDamager();
        if (!(attacker instanceof Player)) {
            return;
        }

        PlayerData playerData = plugin.getPlayerDataManager().getPlayerData(victim.getUniqueId());
        if (playerData == null || !playerData.isCompensationEnabled()) {
            return;
        }

        if (playerData.getPing() < plugin.getConfigManager().getPingThreshold()) {
            return;
        }

        Integer damageTicks = playerData.getLastDamageTicks();
        if (damageTicks != null && damageTicks > 8) {
            return;
        }

        Vector velocity = event.getVelocity();
        Double verticalVelocity = playerData.getVerticalVelocity();
        
        if (verticalVelocity == null || !playerData.isOnGround(velocity.getY())) {
            return;
        }

        // Simply stabilize the vertical component while keeping horizontal intact
        Vector adjustedVelocity = velocity.clone().setY(verticalVelocity);
        event.setVelocity(adjustedVelocity);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!plugin.getConfigManager().isEnabled()) {
            return;
        }

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        
        if (!(entity instanceof Player) || !(damager instanceof Player)) {
            return;
        }
        
        Player victim = (Player) entity;

        PlayerData victimData = plugin.getPlayerDataManager().getPlayerData(victim.getUniqueId());
        if (victimData == null) {
            return;
        }

        victimData.setLastDamageTicks(victim.getTicksLived());
        victimData.setVerticalVelocity(victim.getVelocity().getY());
    }
}