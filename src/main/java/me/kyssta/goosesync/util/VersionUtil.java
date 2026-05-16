package me.kyssta.goosesync.util;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * Utility class for handling version-specific compatibility
 */
public class VersionUtil {
    
    /**
     * Check if the server version supports a specific feature
     */
    public static boolean supportsFeature(GooseSync plugin, String feature) {
        switch (feature) {
            case "cooldown_api":
                return plugin.isVersion117OrHigher(); // Cooldown API was stable in 1.17+
            case "offhand":
                return true; // Offhand was introduced in 1.9, we support 1.16+
            case "new_combat":
                return plugin.isVersion120OrHigher(); // New combat system in 1.20+
            default:
                return true;
        }
    }
    
    /**
     * Get the main hand item for a player (compatible with all versions)
     */
    public static ItemStack getMainHandItem(Player player) {
        return player.getInventory().getItemInMainHand();
    }
    
    /**
     * Check if an item is a splash potion (version-safe)
     */
    public static boolean isSplashPotion(ItemStack item) {
        return item != null && item.getType() == Material.SPLASH_POTION;
    }
    
    /**
     * Check if an item is a golden apple (version-safe)
     */
    public static boolean isGoldenApple(ItemStack item) {
        return item != null && (item.getType() == Material.GOLDEN_APPLE || item.getType() == Material.ENCHANTED_GOLDEN_APPLE);
    }
    
    /**
     * Check if an item is cooked beef (version-safe)
     */
    public static boolean isCookedBeef(ItemStack item) {
        if (item == null) return false;
        
        return item.getType() == Material.COOKED_BEEF;
    }
    
    /**
     * Set cooldown for a player (version-safe)
     */
    public static void setCooldown(Player player, Material material, int ticks) {
        if (supportsFeature(GooseSync.getInstance(), "cooldown_api")) {
            player.setCooldown(material, ticks);
        }
    }
    
    /**
     * Get cooldown for a player (version-safe)
     */
    public static int getCooldown(Player player, Material material) {
        if (supportsFeature(GooseSync.getInstance(), "cooldown_api")) {
            return player.getCooldown(material);
        }
        return 0;
    }

    public static int getPing(Player player) {
        try {
            Method getPing = player.getClass().getMethod("getPing");
            Object ping = getPing.invoke(player);
            if (ping instanceof Integer) {
                return Math.max(0, (Integer) ping);
            }
        } catch (ReflectiveOperationException ignored) {
            // Older servers expose ping through Player.Spigot.
        }

        try {
            Object spigot = player.getClass().getMethod("spigot").invoke(player);
            Method getPing = spigot.getClass().getMethod("getPing");
            Object ping = getPing.invoke(spigot);
            if (ping instanceof Integer) {
                return Math.max(0, (Integer) ping);
            }
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }

        return 0;
    }
} 
