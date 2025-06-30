package me.kyssta.goosesync.util;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        if (item == null) return false;
        
        String itemType = item.getType().toString();
        return itemType.contains("SPLASH_POTION");
    }
    
    /**
     * Check if an item is a golden apple (version-safe)
     */
    public static boolean isGoldenApple(ItemStack item) {
        if (item == null) return false;
        
        String itemType = item.getType().toString();
        return itemType.contains("GOLDEN_APPLE");
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
} 