package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public StopCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.stop();
        return true;
    }
}
