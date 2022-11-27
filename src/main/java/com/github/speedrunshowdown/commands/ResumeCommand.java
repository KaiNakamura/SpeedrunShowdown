package com.github.speedrunshowdown.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.speedrunshowdown.SpeedrunShowdown;

public class ResumeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin is running, give warning
        if (plugin.isRunning()) {
            sender.sendMessage(ChatColor.RED + "Game already running!");
        }
        // Else, resume game
        else {
            int minutes = plugin.getConfig().getInt("sudden-death-time");
            int seconds = 0;

            // If there is at least one argument, parse first argument as minutes
            if (args.length > 0) {
                try {
                    minutes = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Minutes must be formatted as a number");
                    return true;
                }
            }

            // If there is at least two arguments, parse second argument as seconds
            if (args.length > 1) {
                try {
                    seconds = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Seconds must be formatted as a number");
                    return true;
                }
            }

            plugin.resume(minutes, seconds);
        }

        return true;
    }
}
