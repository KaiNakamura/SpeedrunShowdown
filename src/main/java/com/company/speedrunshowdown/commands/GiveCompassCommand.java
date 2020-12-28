package com.company.speedrunshowdown.commands;

import com.company.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCompassCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public GiveCompassCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // If no arguments given, try to give the sender a compass
        if (args.length == 0) {
            // If sender is a player, give player compass
            if (sender instanceof Player) {
                plugin.giveCompass((Player) sender);
            }
            // Else, give warning
            else {
                sender.sendMessage(plugin.getCommand("givecompass").getUsage());
            }
        }
        // Else if given @a, give compass to everyone except the runner
        else if (args[0].equalsIgnoreCase("@a")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                plugin.giveCompass(player);
            }
        }
        // Else, try to give compass to player name
        else {
            Player player = plugin.getServer().getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Player not found with name: " + args[0]);
            }
            else {
                plugin.giveCompass(player);
            }
        }

        return true;
    }
}