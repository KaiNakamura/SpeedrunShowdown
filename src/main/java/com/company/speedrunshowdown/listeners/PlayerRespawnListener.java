package com.company.speedrunshowdown.listeners;

import com.company.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    private SpeedrunShowdown plugin;

    public PlayerRespawnListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plugin.isRunning()) {
            Player player = event.getPlayer();

            plugin.giveCompass(player);

            // If is sudden death, change respawn location
            if (plugin.isSuddenDeath()) {
                event.setRespawnLocation(
                    new Location(plugin.getServer().getWorld("world_the_end"), 0.5, 70, 0.5)
                );
            }
        }
    }
}