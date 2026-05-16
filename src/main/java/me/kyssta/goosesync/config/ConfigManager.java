package me.kyssta.goosesync.config;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final GooseSync plugin;
    private boolean enabled;
    private int pingThreshold;
    private double knockbackMultiplier;
    private boolean consumptionEnabled;
    private double consumptionDelayReduction;
    private boolean pearlEnabled;
    private double pearlCooldownReduction;
    private boolean potionsEnabled;
    private double potionThrowDelayReduction;
    private boolean debugEnabled;

    public ConfigManager(GooseSync plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        
        this.enabled = config.getBoolean("enabled", true);
        this.pingThreshold = Math.max(0, config.getInt("ping-threshold", 100));
        this.knockbackMultiplier = clamp(config.getDouble("knockback-multiplier", 0.8), 0.0, 2.0);
        
        this.consumptionEnabled = config.getBoolean("consumption.enabled", true);
        this.consumptionDelayReduction = clamp(config.getDouble("consumption.delay-reduction", 0.2), 0.0, 0.9);
        
        this.pearlEnabled = config.getBoolean("pearl.enabled", true);
        this.pearlCooldownReduction = clamp(config.getDouble("pearl.cooldown-reduction", 0.2), 0.0, 0.9);
        
        this.potionsEnabled = config.getBoolean("potions.enabled", true);
        this.potionThrowDelayReduction = clamp(config.getDouble("potions.throw-delay-reduction", 0.2), 0.0, 0.9);
        this.debugEnabled = config.getBoolean("debug", false);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getPingThreshold() {
        return pingThreshold;
    }

    public double getKnockbackMultiplier() {
        return knockbackMultiplier;
    }

    public boolean isConsumptionEnabled() {
        return consumptionEnabled;
    }

    public double getConsumptionDelayReduction() {
        return consumptionDelayReduction;
    }

    public boolean isPearlEnabled() {
        return pearlEnabled;
    }

    public double getPearlCooldownReduction() {
        return pearlCooldownReduction;
    }

    public boolean isPotionsEnabled() {
        return potionsEnabled;
    }

    public double getPotionThrowDelayReduction() {
        return potionThrowDelayReduction;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }
}
