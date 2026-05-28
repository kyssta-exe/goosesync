package me.kyssta.goosesync;

import me.kyssta.goosesync.command.GooseSyncCommand;
import me.kyssta.goosesync.config.ConfigManager;
import me.kyssta.goosesync.listener.*;
import me.kyssta.goosesync.manager.PlayerDataManager;
import me.kyssta.goosesync.task.PingUpdateTask;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class GooseSync extends JavaPlugin {
    private static GooseSync instance;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private String serverVersion;

    @Override
    public void onEnable() {
        instance = this;
        
        // Get server version for compatibility
        this.serverVersion = getServerVersion();
        getLogger().info("Detected server version: " + serverVersion);
        
        // Check if version is supported
        if (!isVersionSupported()) {
            getLogger().severe("This version of Minecraft is not supported! Please use version 1.16 or higher.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.playerDataManager = new PlayerDataManager();

        GooseSyncCommand gooseSyncCommand = new GooseSyncCommand(this);
        PluginCommand command = getCommand("gs");
        if (command != null) {
            command.setExecutor(gooseSyncCommand);
            command.setTabCompleter(gooseSyncCommand);
        }

        // Register listeners
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);
        getServer().getPluginManager().registerEvents(new ConsumptionListener(this), this);
        getServer().getPluginManager().registerEvents(new PearlListener(this), this);
        getServer().getPluginManager().registerEvents(new PotionListener(this), this);

        // Start ping update task
        new PingUpdateTask(this).runTaskTimer(this, 20L, 20L);

        getLogger().info("GooseSync has been enabled successfully!");
        getLogger().info("Compatible with Minecraft versions 1.16 - 1.21.2");
    }

    @Override
    public void onDisable() {
        getLogger().info("GooseSync has been disabled!");
    }

    /**
     * Get the server version string
     */
    private String getServerVersion() {
        try {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String[] parts = packageName.split("\\\\.");
            
            // Handle different server implementations
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("v") && part.length() > 1) {
                    // Found version part (e.g., "v1_16_R3")
                    return part.substring(1); // Remove 'v' prefix
                }
            }
            
            // Fallback: try to get version from Bukkit.getBukkitVersion()
            String bukkitVersion = Bukkit.getBukkitVersion();
            if (bukkitVersion != null && !bukkitVersion.isEmpty()) {
                // Extract version from "1.21.5-R0.1-SNAPSHOT" format
                String[] versionParts = bukkitVersion.split("-")[0].split("\\\\.");
                if (versionParts.length >= 2) {
                    return versionParts[0] + "_" + versionParts[1] + "_R0";
                }
            }
            
            // If all else fails, assume it's a supported version
            getLogger().warning("Could not detect server version, assuming compatibility");
            return "1_21_R0"; // Default to latest supported version
            
        } catch (Exception e) {
            getLogger().warning("Error detecting server version: " + e.getMessage());
            return "1_21_R0"; // Default to latest supported version
        }
    }

    /**
     * Check if the current server version is supported
     */
    private boolean isVersionSupported() {
        // First, check if we're running on Paper
        if (isPaper()) {
            // For Paper, we need to parse the version differently
            // Paper version format: {mc_major}_{mc_minor}_{patch}
            // e.g., 26_1_R0 for MC 1.21.2
            try {
                String[] parts = serverVersion.split("_");
                if (parts.length >= 2) {
                    int mcMajor = Integer.parseInt(parts[0]);
                    int mcMinor = Integer.parseInt(parts[1]);
                    
                    // Paper 26.x = MC 1.20.x
                    // Paper 27.x = MC 1.21.x
                    // Paper 28.x = MC 1.22.x (future)
                    // So: MC major version = Paper major - 6
                    int minecraftMajor = mcMajor - 6;
                    
                    // Support Minecraft 1.16+ (which is Paper 22+)
                    return minecraftMajor >= 16;
                }
            } catch (NumberFormatException e) {
                // If we can't parse, assume compatibility for Paper
                return true;
            }
        }

        // Not Paper, use standard version check (for Spigot, etc.)
        try {
            String[] parts = serverVersion.split("_");
            if (parts.length >= 2) {
                int majorVersion = Integer.parseInt(parts[0]);
                int minorVersion = Integer.parseInt(parts[1]);
                return minorVersion >= 16; // Support 1.16+
            }
        } catch (NumberFormatException e) {
            // If we can't parse, assume compatibility
        }
        return true;
    }

    /** 
     * Check if we're running on Paper 
     */ 
    private boolean isPaper() { 
        String name = Bukkit.getName(); 
        return name != null && name.contains("Paper"); 
    }

    /**
     * Get the current server version
     */
    public String getServerVersionString() {
        return serverVersion;
    }

    /**
     * Check if the server version is 1.17 or higher
     */
    public boolean isVersion117OrHigher() {
        try {
            String[] parts = serverVersion.split("_");
            if (parts.length >= 2) {
                int majorVersion = Integer.parseInt(parts[1]);
                return majorVersion >= 17;
            }
        } catch (NumberFormatException e) {
            // Ignore parsing errors, assume newer version
        }
        return true; // Assume newer version if we can't parse
    }

    /**
     * Check if the server version is 1.20 or higher
     */
    public boolean isVersion120OrHigher() {
        try {
            String[] parts = serverVersion.split("_");
            if (parts.length >= 2) {
                int majorVersion = Integer.parseInt(parts[1]);
                return majorVersion >= 20;
            }
        } catch (NumberFormatException e) {
            // Ignore parsing errors, assume newer version
        }
        return true; // Assume newer version if we can't parse
    }

    public static GooseSync getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}