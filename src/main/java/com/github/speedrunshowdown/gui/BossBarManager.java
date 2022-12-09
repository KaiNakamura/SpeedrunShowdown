package com.github.speedrunshowdown.gui;

import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;

import com.github.speedrunshowdown.SpeedrunShowdown;

public class BossBarManager {
    private SpeedrunShowdown plugin;
    private BossBar bossBar;

    public BossBarManager() {
        plugin = SpeedrunShowdown.getInstance();

        // Create a duplicate bar for people not in the end
        bossBar = plugin.getServer().createBossBar(
            "Ender Dragon",
            BarColor.PINK,
            BarStyle.SOLID
        );
    }

    public void removeAll() {
        plugin.getServer().getBossBars().forEachRemaining((bar) -> {
            plugin.getLogger().info(bar.getTitle());
            bar.setVisible(false);
            bar.removeAll();
            plugin.getServer().removeBossBar(bar.getKey());
        });
    }

    public void update() {
        if (plugin.isEnabled()) {
            EnderDragon dragon = plugin.getServer().getWorld("world_the_end").getEnderDragonBattle().getEnderDragon();

            // If there is a dragon, update the boss bar
            if (dragon != null) {
                bossBar.setProgress(dragon.getBossBar().getProgress());

                // If there is someone in the end, add boss bar for people not in the end
                if (areAnyPlayersInTheEnd()) {
                    // For all players not in the end, add the boss bar
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (player.getWorld().getEnvironment() != Environment.THE_END) {
                            bossBar.addPlayer(player);
                        }
                    }
                }
                // Otherwise, remove all boss bars
                else {
                    removeAll();
                }
            }
        }
    }

    private boolean areAnyPlayersInTheEnd() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getWorld().getEnvironment() == Environment.THE_END && player.getGameMode() != GameMode.SPECTATOR) {
                return true;
            }
        }
        return false;
    }
}
