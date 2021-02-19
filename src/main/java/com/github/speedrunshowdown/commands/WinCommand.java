package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class WinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If no arguments given, give warning
        if (args.length == 0) {
            sender.sendMessage(plugin.getCommand("win").getUsage());
        }
        // Else if plugin not running, give warning
        else if (!plugin.isRunning()) {
            sender.sendMessage(ChatColor.YELLOW + "Game not running");
        }
        // Else, check if argument is a team or player
        else {
            Team team = plugin.getSpeedrunShowdownScoreboard().getScoreboard().getTeam(args[0]);
            Player player = plugin.getServer().getPlayer(args[0]);

            if (team != null) {
                plugin.win(team, "");
            }
            else if (player != null) {
                // Look for team
                team = plugin.getSpeedrunShowdownScoreboard().getTeam(player);

                // If no team found, give warning
                if (team == null) {
                    sender.sendMessage(ChatColor.YELLOW + "No team found for " + player.getName());
                }
                else {
                    plugin.win(team, "");
                }
            }
            else {
                sender.sendMessage(plugin.getCommand("win").getUsage());
            }
        }

        return true;
    }
}
