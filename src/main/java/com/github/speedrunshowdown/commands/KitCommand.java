package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public KitCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // If no arguments given, try to give the sender a kit
        if (args.length == 0) {
            // If sender is a player, give player kit
            if (sender instanceof Player) {
                plugin.kit((Player) sender);
            }
            // Else, give warning
            else {
                sender.sendMessage(plugin.getCommand("givecompass").getUsage());
            }
        }
        // Else if given @a, give kit to everyone
        else if (args[0].equalsIgnoreCase("@a")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                plugin.kit(player);
            }
        }
        // Else, try to give kit to player name
        else {
            Player player = plugin.getServer().getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Player not found with name: " + args[0]);
            }
            else {
                plugin.kit(player);
            }
        }

        return true;
    }
}