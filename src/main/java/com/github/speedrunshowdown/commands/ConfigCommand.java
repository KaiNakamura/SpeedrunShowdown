package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;
import com.github.speedrunshowdown.SpeedrunShowdownConfig;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ConfigCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public ConfigCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // If sender is a player, open gui
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Create inventory gui
            Inventory gui = plugin.getServer().createInventory(player, 9, "Config");
            gui.setContents(SpeedrunShowdownConfig.getItems(plugin.getConfig()));

            // Open gui
            player.openInventory(gui);
        }
        // Else, give warning
        else {
            sender.sendMessage("Only players may open the config gui");
        }

        return true;
    }
}
