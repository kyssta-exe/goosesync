package me.kyssta.goosesync.command;

import me.kyssta.goosesync.GooseSync;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GooseSyncCommand implements CommandExecutor {
    private final GooseSync plugin;

    public GooseSyncCommand(GooseSync plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // Show plugin information
            showPluginInfo(sender);
            return true;
        }

        // Handle subcommands if needed in the future
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "reload":
                if (sender.hasPermission("goosesync.reload")) {
                    plugin.getConfigManager().loadConfig();
                    sender.sendMessage(ChatColor.GREEN + "GooseSync configuration reloaded!");
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                }
                break;
            case "version":
                showPluginInfo(sender);
                break;
            case "help":
                showHelp(sender);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /gs help for available commands.");
                break;
        }

        return true;
    }

    private void showPluginInfo(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "╔══════════════════════════════════════════╗");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.YELLOW + "              " + ChatColor.AQUA + "GooseSync" + ChatColor.YELLOW + "              " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "╠══════════════════════════════════════════╣");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.WHITE + "  A Plugin to fix all the latency issues!  " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.GRAY + "  Version: " + ChatColor.YELLOW + plugin.getDescription().getVersion() + ChatColor.GRAY + "                    " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.GRAY + "  Made by " + ChatColor.AQUA + "Kyssta" + ChatColor.GRAY + "!                        " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.GRAY + "  " + ChatColor.AQUA + "Kyssta Network" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "kyssta.xyz" + ChatColor.GRAY + "        " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.GRAY + "  Server Version: " + ChatColor.YELLOW + plugin.getServerVersionString() + ChatColor.GRAY + "           " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "╚══════════════════════════════════════════╝");
        sender.sendMessage("");
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD + "╔══════════════════════════════════════════╗");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.YELLOW + "              " + ChatColor.AQUA + "GooseSync Help" + ChatColor.YELLOW + "              " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "╠══════════════════════════════════════════╣");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.WHITE + "  Available Commands:                    " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.YELLOW + "  /gs" + ChatColor.GRAY + " or " + ChatColor.YELLOW + "/goosesync" + ChatColor.GRAY + " - Show info    " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.YELLOW + "  /gs help" + ChatColor.GRAY + " - Show this help menu    " + ChatColor.GOLD + "║");
        sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.YELLOW + "  /gs version" + ChatColor.GRAY + " - Show plugin version    " + ChatColor.GOLD + "║");
        if (sender.hasPermission("goosesync.reload")) {
            sender.sendMessage(ChatColor.GOLD + "║" + ChatColor.YELLOW + "  /gs reload" + ChatColor.GRAY + " - Reload configuration   " + ChatColor.GOLD + "║");
        }
        sender.sendMessage(ChatColor.GOLD + "╚══════════════════════════════════════════╝");
        sender.sendMessage("");
    }
} 