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

    public ConfigManager(GooseSync plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        
        this.enabled = config.getBoolean("enabled", true);
        this.pingThreshold = config.getInt("ping-threshold", 100);
        this.knockbackMultiplier = config.getDouble("knockback-multiplier", 0.8);
        
        this.consumptionEnabled = config.getBoolean("consumption.enabled", true);
        this.consumptionDelayReduction = config.getDouble("consumption.delay-reduction", 0.2);
        
        this.pearlEnabled = config.getBoolean("pearl.enabled", true);
        this.pearlCooldownReduction = config.getDouble("pearl.cooldown-reduction", 0.2);
        
        this.potionsEnabled = config.getBoolean("potions.enabled", true);
        this.potionThrowDelayReduction = config.getDouble("potions.throw-delay-reduction", 0.2);
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
}