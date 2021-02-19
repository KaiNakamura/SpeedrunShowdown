package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.gui.ConfigOption;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // If sender is a player, open gui
        if (sender instanceof Player) {
            ConfigOption.getGui().show((Player) sender);
        }
        // Else, give warning
        else {
            sender.sendMessage("Only players may open the config gui");
        }

        return true;
    }
}
