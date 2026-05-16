package me.kyssta.goosesync.command;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GooseSyncCommand implements CommandExecutor, TabCompleter {
    private static final List<String> SUBCOMMANDS = Arrays.asList("help", "version", "status", "reload");
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
        sender.sendMessage(ChatColor.YELLOW + "/gs status" + ChatColor.GRAY + " - Show live config/status");
        if (sender.hasPermission("goosesync.reload")) {
            sender.sendMessage(ChatColor.YELLOW + "/gs reload" + ChatColor.GRAY + " - Reload configuration");
        }
        sender.sendMessage("");
    }

    private void showStatus(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "GooseSync status");
        sender.sendMessage(ChatColor.GRAY + "Enabled: " + ChatColor.YELLOW + plugin.getConfigManager().isEnabled());
        sender.sendMessage(ChatColor.GRAY + "Ping threshold: " + ChatColor.YELLOW + plugin.getConfigManager().getPingThreshold() + "ms");
        sender.sendMessage(ChatColor.GRAY + "Tracked players: " + ChatColor.YELLOW + plugin.getPlayerDataManager().getTrackedPlayerCount());
        sender.sendMessage(ChatColor.GRAY + "Knockback multiplier: " + ChatColor.YELLOW + plugin.getConfigManager().getKnockbackMultiplier());
        sender.sendMessage(ChatColor.GRAY + "Consumption: " + ChatColor.YELLOW + plugin.getConfigManager().isConsumptionEnabled());
        sender.sendMessage(ChatColor.GRAY + "Pearls: " + ChatColor.YELLOW + plugin.getConfigManager().isPearlEnabled());
        sender.sendMessage(ChatColor.GRAY + "Potions: " + ChatColor.YELLOW + plugin.getConfigManager().isPotionsEnabled());
        sender.sendMessage("");
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
