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
        // If plugin is running and should hide spectator advancements,
        // turn announce advancements gamerule off and schedule it to return to its original value
        if (plugin.isRunning() && plugin.getConfig().getBoolean("hide-spectator-advancements")) {
            final boolean announceAdvancements = event.getPlayer().getWorld().getGameRuleValue(GameRule.ANNOUNCE_ADVANCEMENTS);
            if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                event.getPlayer().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        event.getPlayer().getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, announceAdvancements);
                    }
                }, 1L);
            }
        }
    }

}
