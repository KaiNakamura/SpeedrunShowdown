package com.github.speedrunshowdown.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.speedrunshowdown.SpeedrunShowdown;

public class RemoveAllBossBarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpeedrunShowdown.getInstance().getBossBarManager().removeAll();
        return true;
    }
}
