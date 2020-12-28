package com.company.speedrunshowdown.listeners;

import com.company.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

public class BedUseListener implements Listener {
    private SpeedrunShowdown plugin;

    public BedUseListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBedUse(PlayerBedEnterEvent event) {
        // If plugin is running and should prevent bed explosions
        // and entering bed not possible in this dimension, cancel event
        if (
            plugin.isRunning() &&
            plugin.getConfig().getBoolean("prevent-bed-explosions") &&
            event.getBedEnterResult() == BedEnterResult.NOT_POSSIBLE_HERE
        ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(
                ChatColor.RED + "Cannot use beds in this dimension!"
            );
        }
    }
}