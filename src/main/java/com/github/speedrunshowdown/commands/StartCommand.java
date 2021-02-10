package com.github.speedrunshowdown.commands;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    private SpeedrunShowdown plugin;

    public StartCommand(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.isRunning()) sender.sendMessage(ChatColor.RED + "Game already started!");
        sender.sendMessage(ChatColor.GREEN + "Countdown started!");
        // Schedule tasks to show titles to players in the future.
        startingTimerTitle(ChatColor.YELLOW + "Game starting soon...", false);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                startingTimerTitle(ChatColor.YELLOW + "Starting in 3...", false);
            }
        }, 60L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                startingTimerTitle(ChatColor.YELLOW + "Starting in 2...", false);
            }
        }, 80L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                startingTimerTitle(ChatColor.YELLOW + "Starting in 1...", false);
            }
        }, 100L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                startingTimerTitle(ChatColor.GREEN + "Game started!", true);
                plugin.start();
            }
        }, 120L);
        return true;
    }

    public void startingTimerTitle(String subtitle, boolean louderDing) {
        float pitch = 1f;
        if (louderDing) pitch = 2f;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, pitch);
            player.sendTitle(ChatColor.YELLOW + ChatColor.BOLD.toString() + "SPEEDRUN SHOWDOWN", subtitle, 0, 60, 10);
        }
    }

}
