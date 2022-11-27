package com.github.speedrunshowdown.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.github.speedrunshowdown.SpeedrunShowdown;

public class PlayerLoginListener implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin is running
        if (plugin.isRunning()) {
            plugin.getWorldBorderManager().updatePlayerWorldBorder(event.getPlayer());
        }
    }
}