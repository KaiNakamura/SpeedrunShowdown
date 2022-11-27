package com.github.speedrunshowdown.listeners;

import java.util.ArrayList;

import com.github.speedrunshowdown.Constants;
import com.github.speedrunshowdown.SpeedrunShowdown;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin running
        if (plugin.isRunning()) {
            // If is sudden death
            if (plugin.isSuddenDeath()) {
                // Make player a spectator
                event.getEntity().setGameMode(GameMode.SPECTATOR);

                // Mark player death as a final kill
                String deathMessage = event.getDeathMessage();

                String[] words = deathMessage.split(" ");
                StringBuilder stringBuilder = new StringBuilder();
                for (String word : words) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (word.equals(player.getName())) {
                            Team team = plugin.getScoreboardManager().getTeam(player);
                            if (team != null) {
                                word = team.getColor() + word + ChatColor.RESET;
                            }
                        }
                    }

                    stringBuilder.append(word + " ");
                }

                deathMessage = stringBuilder.toString();

                event.setDeathMessage("");
                plugin.getServer().broadcastMessage(deathMessage + ChatColor.AQUA + ChatColor.BOLD + "FINAL KILL!");

                // Play death sound to all players
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                }

                // Check which teams are living
                ArrayList<Team> livingTeams = new ArrayList<>();
                for (Team team : plugin.getScoreboardManager().getScoreboard().getTeams()) {
                    // If team has no entries, skip
                    if (team.getSize() != 0) {
                        boolean teamAlive = false;
                        // If team contains a player in survival, mark team as alive
                        for (String entry : team.getEntries()) {
                            Player player = plugin.getServer().getPlayer(entry);
                            if (player != null && player.getGameMode() == GameMode.SURVIVAL) {
                                teamAlive = true;
                            }
                        }
                        
                        // If team not alive and contains dying player, announce team elimination
                        if (
                            !teamAlive &&
                            team.getName().equals(
                                plugin.getScoreboardManager().getTeam(event.getEntity()).getName()
                            )
                        ) {
                            // Broadcast team elimination
                            plugin.getServer().broadcastMessage("");
                            plugin.getServer().broadcastMessage(
                                "" + ChatColor.WHITE + ChatColor.BOLD + "TEAM ELIMINATED > " +
                                team.getColor() + " Team " + WordUtils.capitalize(team.getName()) +
                                ChatColor.RED + " has been eliminated!"
                            );
                            plugin.getServer().broadcastMessage("");
                        }
                        // Else if team living, add team to living teams
                        else if (teamAlive) {
                            livingTeams.add(team);
                        }
                    }
                }

                // If players need to kill the dragon to win and there's no teams left, respawn players
                if (
                    plugin.getConfig().getBoolean("must-kill-dragon-to-win") &&
                    livingTeams.size() == 0
                ){
                    plugin.suddenDeath();
                }
                // Else if players don't need to kill the dragon and there's one team left, make team win
                else if (
                    !plugin.getConfig().getBoolean("must-kill-dragon-to-win") &&
                    livingTeams.size() == 1
                ) {
                    plugin.win(
                        livingTeams.get(0),
                        deathMessage
                    );
                }
            }
            // Else not sudden death
            else {
                Player player = event.getEntity();

                if (plugin.getConfig().getBoolean("keep-armor")) {
                    // Get armor from inventory
                    final ItemStack[] armor = player.getInventory().getArmorContents();

                    // Schedule task to give armor back on respawn
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.getInventory().setArmorContents(armor);
                    });
                    
                    // Remove armor so items don't drop
                    for (ItemStack item : armor) {
                        event.getDrops().remove(item);
                    }
                }
                if (plugin.getConfig().getBoolean("keep-tools")) {
                    // Make list of tools
                    final ArrayList<ItemStack> tools = new ArrayList<>();

                    // Add each item that is a tool to the list
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item != null && isPersistentTool(item.getType())) {
                            tools.add(item);
                        }
                    }

                    // Schedule task to give tools back on respawn
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        for (ItemStack tool : tools) {
                            player.getInventory().addItem(tool);
                        }
                    });

                    // Remove tools so items don't drop
                    for (ItemStack tool : tools) {
                        event.getDrops().remove(tool);
                    }
                }
            }
        }
    }

    public static boolean isPersistentTool(Material material) {
        for (Material tool : Constants.PERSISTENT_TOOLS) {
            if (material == tool) {
                return true;
            }
        }
        return false;
    }
}