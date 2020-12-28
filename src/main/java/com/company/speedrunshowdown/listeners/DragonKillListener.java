package com.company.speedrunshowdown.listeners;

import com.company.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class DragonKillListener implements Listener {
    private SpeedrunShowdown plugin;

    public DragonKillListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDragonKill(PlayerAdvancementDoneEvent event) {
        if (plugin.isRunning()){
            for (String criteria : event.getAdvancement().getCriteria()) {
                if (criteria.equals("killed_dragon")) {
                    plugin.win(
                        plugin.getSpeedrunShowdownScoreboard().getTeam(event.getPlayer()),
                        event.getPlayer().getName() + " killed the dragon!"
                    );
                }
            }
        }
    }
}