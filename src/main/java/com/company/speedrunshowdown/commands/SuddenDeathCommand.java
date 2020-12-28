package com.company.speedrunshowdown.commands;

import com.company.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SuddenDeathCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public SuddenDeathCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.suddenDeath();
        return true;
    }
}
