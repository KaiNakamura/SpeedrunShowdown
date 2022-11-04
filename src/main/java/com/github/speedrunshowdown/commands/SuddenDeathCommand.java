package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SuddenDeathCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

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
                    int minutes = Integer.parseInt(args[0]);
                    plugin.setTime(minutes * 60);
                    plugin.getServer().broadcastMessage(
                        ChatColor.YELLOW + "Sudden death in " +
                        ChatColor.GREEN + minutes +
                        ChatColor.YELLOW + " minutes"
                    );
                }
                // If invalid, give warning
                catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getCommand("suddendeath").getUsage());
                }
            }
        }

        return true;
    }
}
