package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerRespawnListener implements Listener {
    private SpeedrunShowdown plugin;

    public PlayerRespawnListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plugin.isRunning()) {
            final Player player = event.getPlayer();

            // If sudden death, give sudden death kit and armor
            if (plugin.isSuddenDeath()) {
                plugin.giveSuddenDeathKit(player);
                plugin.giveArmor(player);
            }
            // Else if plugin should give armor, give armor
            else if (plugin.getConfig().getBoolean("give-armor")) {
                plugin.giveArmor(player);
            }

            // If plugin should give compass, give compass
            if (plugin.getConfig().getBoolean("give-compass")) {
                plugin.giveCompass(player);
            }

            // Give resistance
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(
                plugin,
                new Runnable() {
                    @Override
                    public void run() {
                        player.getPlayer().addPotionEffect(new PotionEffect(
                            PotionEffectType.DAMAGE_RESISTANCE,
                            plugin.getConfig().getInt("respawn-invincibility") * 20,
                            255
                        ));
                    }
                },
                20
            );

            // If is sudden death, change respawn location to end
            if (plugin.isSuddenDeath()) {
                World end = plugin.getServer().getWorld("world_the_end");
                event.setRespawnLocation(
                    new Location(end, 0.5, end.getHighestBlockYAt(0, 0) + 1, 0.5, 0, 90)
                );
            }
        }
    }
}