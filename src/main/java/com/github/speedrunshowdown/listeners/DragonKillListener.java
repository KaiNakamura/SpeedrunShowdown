package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class DragonKillListener implements Listener {
    @EventHandler
    public void onDragonKill(PlayerAdvancementDoneEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin is running, check advancement criteria
        if (plugin.isRunning()){
            // For all advancement criteria
            for (String criteria : event.getAdvancement().getCriteria()) {
                // If crieria is killed dragon, declare winning team
                if (criteria.equals("killed_dragon")) {
                    plugin.win(
                        plugin.getScoreboardManager().getTeam(event.getPlayer()),
                        event.getPlayer().getName() + " killed the dragon!"
                    );
                }
            }
        }
    }
}