package com.github.speedrunshowdown.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import com.github.speedrunshowdown.SpeedrunShowdown;

public class PortalEnterListener implements Listener {
    @EventHandler
    public void onPortalEnter(PlayerPortalEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();
        Player player = event.getPlayer();

        // If plugin is running and player is not a spectator
        if (plugin.isRunning() && player.getGameMode() != GameMode.SPECTATOR) {
            // Give player portal invincibility
            event.getPlayer().addPotionEffect(new PotionEffect(
                PotionEffectType.DAMAGE_RESISTANCE,
                plugin.getConfig().getInt("portal-invincibility") * 20,
                255
            ));

            // If plugin should give portal alerts, give portal alerts
            if (plugin.getConfig().getBoolean("portal-alerts")) {
                Location from = event.getFrom();
                Location to = event.getTo();
                plugin.getServer().broadcastMessage(
                    "" + getPlayerColor(player) + ChatColor.BOLD + player.getName() +
                    ChatColor.YELLOW + " portaled from " +
                    getLocationString(from) + ChatColor.YELLOW + " to " +
                    getLocationString(to)
                );

                // Play sound to all players
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                }
            }
        }
    }

    private static ChatColor getPlayerColor(Player player) {
        Team team = SpeedrunShowdown.getInstance().getScoreboardManager().getTeam(player);
        if (team == null) {
            return ChatColor.RESET;
        } else {
            return team.getColor();
        }
    }

    private static String getLocationString(Location location) {
        return (
            getWorldColor(location.getWorld()) + "(" +
            (int) location.getX() + ", " +
            (int) location.getZ() + ") in the " +
            getWorldName(location.getWorld())
        );
    }

    private static String getWorldName(World world) {
        switch (world.getName()) {
            case "world":
                return "Overworld";
            case "world_nether":
                return "Nether";
            case "world_the_end":
                return "End";
            default:
                return world.getName();
        }
    }

    private static ChatColor getWorldColor(World world) {
        switch (world.getName()) {
            case "world":
                return ChatColor.GREEN;
            case "world_nether":
                return ChatColor.RED;
            case "world_the_end":
                return ChatColor.DARK_PURPLE;
            default:
                return ChatColor.WHITE;
        }
    }
}