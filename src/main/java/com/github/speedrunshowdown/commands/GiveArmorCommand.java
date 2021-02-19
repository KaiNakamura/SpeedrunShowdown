package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveArmorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If no arguments given, try to give the sender armor
        if (args.length == 0) {
            // If sender is a player, give player armor
            if (sender instanceof Player) {
                plugin.giveArmor((Player) sender);
            }
            // Else, give warning
            else {
                sender.sendMessage(plugin.getCommand("kit").getUsage());
            }
        }
        // Else if given @a, give armor to everyone
        else if (args[0].equalsIgnoreCase("@a")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                plugin.giveArmor(player);
            }
        }
        // Else, try to give armor to player name
        else {
            Player player = plugin.getServer().getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Player not found with name: " + args[0]);
            }
            else {
                plugin.giveArmor(player);
            }
        }

        return true;
    }
}