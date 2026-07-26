package me.kyssta.goosesync.command;

import me.kyssta.goosesync.GooseSync;
import me.kyssta.goosesync.config.ConfigManager;
import me.kyssta.goosesync.model.PlayerData;
import me.kyssta.goosesync.util.VersionUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GooseSyncCommand implements CommandExecutor, TabCompleter {
    private static final List<String> SUBCOMMANDS = Arrays.asList("help", "version", "status", "ping", "toggle", "reload");
    private final GooseSync plugin;

    public GooseSyncCommand(GooseSync plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            showPluginInfo(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload":
                if (sender.hasPermission("goosesync.reload")) {
                    plugin.reloadConfig();
                    plugin.getConfigManager().loadConfig();
                    sender.sendMessage(ChatColor.GREEN + "GooseSync configuration reloaded.");
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
                break;
            case "version":
                showPluginInfo(sender);
                break;
            case "status":
                if (sender.hasPermission("goosesync.status")) {
                    showStatus(sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
                break;
            case "ping":
                showPing(sender);
                break;
            case "toggle":
                toggleCompensation(sender);
                break;
            case "help":
                showHelp(sender);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /gs help.");
                break;
        }

        return true;
    }

    private void showPluginInfo(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "GooseSync " + ChatColor.YELLOW + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GRAY + "Latency compensation for high-ping players.");
        sender.sendMessage(ChatColor.GRAY + "Server: " + ChatColor.YELLOW + plugin.getServerVersionString());
        sender.sendMessage(ChatColor.GRAY + "Use " + ChatColor.YELLOW + "/gs help" + ChatColor.GRAY + " for commands.");
        sender.sendMessage("");
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "GooseSync commands");
        sender.sendMessage(ChatColor.YELLOW + "/gs" + ChatColor.GRAY + " - Show plugin info");
        sender.sendMessage(ChatColor.YELLOW + "/gs help" + ChatColor.GRAY + " - Show command list");
        sender.sendMessage(ChatColor.YELLOW + "/gs version" + ChatColor.GRAY + " - Show version info");
        sender.sendMessage(ChatColor.YELLOW + "/gs ping" + ChatColor.GRAY + " - Show your ping and compensation status");
        sender.sendMessage(ChatColor.YELLOW + "/gs toggle" + ChatColor.GRAY + " - Toggle your own compensation");
        sender.sendMessage(ChatColor.YELLOW + "/gs status" + ChatColor.GRAY + " - Show live config/status");
        if (sender.hasPermission("goosesync.reload")) {
            sender.sendMessage(ChatColor.YELLOW + "/gs reload" + ChatColor.GRAY + " - Reload configuration");
        }
        sender.sendMessage("");
    }

    private void showStatus(CommandSender sender) {
        ConfigManager config = plugin.getConfigManager();
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "GooseSync status");
        sender.sendMessage(ChatColor.GRAY + "Enabled: " + ChatColor.YELLOW + config.isEnabled());
        sender.sendMessage(ChatColor.GRAY + "Ping threshold: " + ChatColor.YELLOW + config.getPingThreshold() + "ms");
        sender.sendMessage(ChatColor.GRAY + "Ping update interval: " + ChatColor.YELLOW + config.getPingUpdateInterval() + " ticks");
        sender.sendMessage(ChatColor.GRAY + "Tracked players: " + ChatColor.YELLOW + plugin.getPlayerDataManager().getTrackedPlayerCount());
        sender.sendMessage(ChatColor.GRAY + "Knockback: " + ChatColor.YELLOW + config.isKnockbackEnabled()
                + ChatColor.GRAY + " (x" + config.getKnockbackMultiplier() + ")");
        sender.sendMessage(ChatColor.GRAY + "Consumption: " + ChatColor.YELLOW + config.isConsumptionEnabled());
        sender.sendMessage(ChatColor.GRAY + "Pearls: " + ChatColor.YELLOW + config.isPearlEnabled());
        sender.sendMessage(ChatColor.GRAY + "Potions: " + ChatColor.YELLOW + config.isPotionsEnabled());
        sender.sendMessage("");
    }

    private void showPing(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        PlayerData data = plugin.getPlayerDataManager().getOrCreatePlayerData(player);
        int threshold = plugin.getConfigManager().getPingThreshold();
        boolean active = data.shouldCompensate(threshold);

        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "Your GooseSync status");
        sender.sendMessage(ChatColor.GRAY + "Ping: " + ChatColor.YELLOW + data.getPing() + "ms");
        sender.sendMessage(ChatColor.GRAY + "Threshold: " + ChatColor.YELLOW + threshold + "ms");
        sender.sendMessage(ChatColor.GRAY + "Compensation: " + (data.isCompensationEnabled() ? ChatColor.GREEN + "on" : ChatColor.RED + "off"));
        sender.sendMessage(ChatColor.GRAY + "Currently active: " + (active ? ChatColor.GREEN + "yes" : ChatColor.YELLOW + "no"));
        sender.sendMessage("");
    }

    private void toggleCompensation(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        PlayerData data = plugin.getPlayerDataManager().getOrCreatePlayerData(player);
        data.setCompensationEnabled(!data.isCompensationEnabled());

        sender.sendMessage(ChatColor.GREEN + "GooseSync compensation " + (data.isCompensationEnabled() ? "enabled" : "disabled") + ".");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return new ArrayList<>();
        }

        String prefix = args[0].toLowerCase();
        List<String> matches = new ArrayList<>();
        for (String subCommand : SUBCOMMANDS) {
            if (subCommand.startsWith(prefix)
                    && (!"reload".equals(subCommand) || sender.hasPermission("goosesync.reload"))
                    && (!"status".equals(subCommand) || sender.hasPermission("goosesync.status"))) {
                matches.add(subCommand);
            }
        }
        return matches;
    }
}
