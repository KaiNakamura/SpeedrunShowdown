package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RespawnAnchorUseListener implements Listener {
    @EventHandler
    public void onRespawnAnchorUse(PlayerInteractEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin is running and plugin should prevent respawn anchor explosions
        // and respawn anchor right clicked in the wrong dimension, cancel event
        if (
            plugin.isRunning() &&
            plugin.getConfig().getBoolean("prevent-respawn-anchor-explosions") &&
            event.getClickedBlock() != null &&
            event.getClickedBlock().getType() == Material.RESPAWN_ANCHOR &&
            event.getAction() == Action.RIGHT_CLICK_BLOCK &&
            event.getPlayer().getWorld().getEnvironment() != Environment.NETHER
        ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Cannot use respawn anchors in this dimension!");
        }
    }
}