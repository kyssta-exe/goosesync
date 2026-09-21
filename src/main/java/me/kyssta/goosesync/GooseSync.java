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
            getLogger().severe("This version of Minecraft is not supported (" + serverVersion + ")! Please use Minecraft 1.16 - 1.21.x or 26.x.");
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
        long pingInterval = configManager.getPingUpdateInterval();
        new PingUpdateTask(this).runTaskTimer(this, pingInterval, pingInterval);

        getLogger().info("GooseSync has been enabled successfully!");
        getLogger().info("Compatible with Minecraft versions 1.16 - 26.3");
    }

    @Override
    public void onDisable() {
        getLogger().info("GooseSync has been disabled!");
    }

    /**
     * Get the server's game version, e.g. "26.3" or "1.21.2"
     */
    private String getServerVersion() {
        // Primary: Bukkit.getBukkitVersion() reports the game version,
        // e.g. "26.3-R0.1-SNAPSHOT"
        try {
            String bukkitVersion = Bukkit.getBukkitVersion();
            if (bukkitVersion != null && !bukkitVersion.isEmpty()) {
                String gameVersion = bukkitVersion.split("-")[0].trim();
                if (!gameVersion.isEmpty()) {
                    return gameVersion;
                }
            }
        } catch (Exception e) {
            getLogger().warning("Could not read Bukkit version: " + e.getMessage());
        }

        // Fallback: legacy versioned CraftBukkit packages, e.g. v1_21_R3
        try {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            for (String part : packageName.split("\\.")) {
                if (part.startsWith("v") && part.length() > 1 && Character.isDigit(part.charAt(1))) {
                    return part.substring(1).replace('_', '.');
                }
            }
        } catch (Exception e) {
            getLogger().warning("Error detecting server version: " + e.getMessage());
        }

        // If all else fails, assume it's a supported version
        getLogger().warning("Could not detect server version, assuming compatibility");
        return "26.3"; // Default to latest supported version
    }

    /**
     * Parse a game version string into {major, minor}, e.g. "26.3" -> {26, 3},
     * "1.21.2" -> {1, 21}, "26_3-R0.1" -> {26, 3}. Returns null if unparseable.
     */
    private int[] parseGameVersion(String version) {
        if (version == null || version.isEmpty()) {
            return null;
        }
        try {
            String[] parts = version.split("[-_]");
            String[] numbers = parts[0].split("\\.");
            if (numbers.length < 2) {
                return null;
            }
            return new int[] { Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]) };
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Check if the current server version is supported
     */
    private boolean isVersionSupported() {
        int[] version = parseGameVersion(serverVersion);
        if (version == null) {
            // If we can't parse, assume compatibility
            return true;
        }
        int major = version[0];
        int minor = version[1];
        // Old scheme: 1.16 - 1.21.x; new scheme: 26.x and later
        if (major == 1) {
            return minor >= 16;
        }
        return major >= 26;
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
        return isVersionAtLeast(1, 17);
    }

    /**
     * Check if the server version is 1.20 or higher
     */
    public boolean isVersion120OrHigher() {
        return isVersionAtLeast(1, 20);
    }

    /**
     * Compare the detected game version against a minimum major/minor pair.
     * Works for both schemes: 1.x (1.16 - 1.21.x) and 26.x+.
     */
    private boolean isVersionAtLeast(int reqMajor, int reqMinor) {
        int[] version = parseGameVersion(serverVersion);
        if (version == null) {
            return true; // Assume newer version if we can't parse
        }
        if (version[0] == reqMajor) {
            return version[1] >= reqMinor;
        }
        return version[0] > reqMajor;
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
