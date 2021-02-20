package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;
import com.github.speedrunshowdown.gui.ConfigOption;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If no arguments given, try to open config to sender
        if (args.length == 0) {
            // If sender is a player, open config
            if (sender instanceof Player) {
                openConfig((Player) sender);
            }
            // Else, give warning
            else {
                sender.sendMessage(plugin.getCommand("config").getUsage());
            }
        }
        // Else if given @a, open config to everyone
        else if (args[0].equalsIgnoreCase("@a")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                openConfig(player);
            }
        }
        // Else, try to open config to player name
        else {
            Player player = plugin.getServer().getPlayer(args[0]);

            // If player not found, give warning
            if (player == null) {
                sender.sendMessage("Player not found with name: " + args[0]);
            }
            // Else, open config to player
            else {
                openConfig(player);
            }
        }

        return true;
    }

    private void openConfig(Player player) {
        ConfigOption.getGui().show(player);
    }
}
