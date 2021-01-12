package com.company.speedrunshowdown.commands;

import com.company.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SuddenDeathCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public SuddenDeathCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // If plugin not running, give warning
        if (!plugin.isRunning()) {
            sender.sendMessage(ChatColor.YELLOW + "Game not running");
        }
        else {
            // If no arguments, start sudden death immediately
            if (args.length == 0) {
                plugin.suddenDeath();
            }
            else {
                // Try to parse int
                try {
                    // Set timer
                    int seconds = Integer.parseInt(args[0]);
                    plugin.setTime(seconds);
                    plugin.getServer().broadcastMessage(
                        ChatColor.YELLOW + "Sudden death in " +
                        ChatColor.GREEN + seconds +
                        ChatColor.YELLOW + " seconds"
                    );
                }
                // If invalid, give warning
                catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getCommand("settime").getUsage());
                }
            }
        }

        return true;
    }
}
