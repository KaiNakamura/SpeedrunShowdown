package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown.getInstance().stop();
        return true;
    }
}
