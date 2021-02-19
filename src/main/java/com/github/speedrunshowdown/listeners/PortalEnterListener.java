package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PortalEnterListener implements Listener {
    @EventHandler
    public void onPortalEnter(PlayerPortalEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin is running, give player portal invincibility
        if (plugin.isRunning()) {
            plugin.getLogger().info(event.getPlayer().getName());
            event.getPlayer().addPotionEffect(new PotionEffect(
                PotionEffectType.DAMAGE_RESISTANCE,
                plugin.getConfig().getInt("portal-invincibility") * 20,
                255
            ));
        }
    }
}