package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamageListener implements Listener {
    private SpeedrunShowdown plugin;

    public BlockDamageListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        // If plugin is running and spawners are indestructable
        // and player breaking spawner, prevent breaking
        if (
            plugin.isRunning() &&
            plugin.getConfig().getBoolean("indestructable-spawners") &&
            event.getBlock().getType() == Material.SPAWNER
        ) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Cannot break spawners!");
        }
    }
}