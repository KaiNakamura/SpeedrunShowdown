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
                int minutes = 0;
                int seconds = 0;

                // If there is at least one argument, parse first argument as minutes
                if (args.length > 0) {
                    try {
                        minutes = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Minutes must be formatted as a number");
                        sender.sendMessage(plugin.getCommand("suddendeath").getUsage());
                        return true;
                    }
                }

                // If there is at least two arguments, parse second argument as seconds
                if (args.length > 1) {
                    try {
                        seconds = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Seconds must be formatted as a number");
                        sender.sendMessage(plugin.getCommand("suddendeath").getUsage());
                        return true;
                    }
                }

                plugin.setTime(60 * minutes + seconds);
                plugin.getServer().broadcastMessage(
                    ChatColor.YELLOW + "Sudden death in " +
                    ChatColor.GREEN + minutes +
                    ChatColor.YELLOW + " minutes"
                );
            }
        }

        return true;
    }
}
