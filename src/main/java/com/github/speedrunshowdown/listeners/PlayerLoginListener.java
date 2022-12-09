package com.github.speedrunshowdown.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.github.speedrunshowdown.SpeedrunShowdown;

public class PlayerLoginListener implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        SpeedrunShowdown.getInstance().getWorldBorderManager().updatePlayerWorldBorder(event.getPlayer());
    }
}