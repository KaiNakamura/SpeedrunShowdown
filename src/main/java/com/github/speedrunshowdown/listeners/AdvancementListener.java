package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {
    private SpeedrunShowdown plugin;

    public AdvancementListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAdvancementObtained(final PlayerAdvancementDoneEvent event) {
        // Unfortunately Spigot does not provide a message to cancel this event so we have to come up with our own tomfoolery.
        if (plugin.isRunning() && plugin.getConfig().getBoolean("hide-spectator-advancements-in-chat")) {
            event.getPlayer().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
            if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                event.getPlayer().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        event.getPlayer().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
                    }
                }, 1L);

            }
        }
    }

}
