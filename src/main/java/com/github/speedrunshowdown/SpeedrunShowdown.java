package com.github.speedrunshowdown;

import java.util.Arrays;
import java.util.Iterator;

import com.github.speedrunshowdown.commands.GiveCompassCommand;
import com.github.speedrunshowdown.commands.StartCommand;
import com.github.speedrunshowdown.commands.StopCommand;
import com.github.speedrunshowdown.commands.SuddenDeathCommand;
import com.github.speedrunshowdown.commands.WinCommand;
import com.github.speedrunshowdown.listeners.BedUseListener;
import com.github.speedrunshowdown.listeners.BlockDamageListener;
import com.github.speedrunshowdown.listeners.CompassUseListener;
import com.github.speedrunshowdown.listeners.DragonKillListener;
import com.github.speedrunshowdown.listeners.PlayerDeathListener;
import com.github.speedrunshowdown.listeners.PlayerRespawnListener;
import com.github.speedrunshowdown.listeners.RespawnAnchorUseListener;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

public class SpeedrunShowdown extends JavaPlugin implements Runnable {
    private boolean running = false;
    private boolean suddenDeath = false;
    private int taskId;
    private int timer;

    private SpeedrunShowdownScoreboard speedrunShowdownScoreboard;

    @Override
    public void onEnable() {
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("stop").setExecutor(new StopCommand(this));
        getCommand("suddendeath").setExecutor(new SuddenDeathCommand(this));
        getCommand("givecompass").setExecutor(new GiveCompassCommand(this));
        getCommand("win").setExecutor(new WinCommand(this));
        getServer().getPluginManager().registerEvents(new CompassUseListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new BedUseListener(this), this);
        getServer().getPluginManager().registerEvents(new RespawnAnchorUseListener(this), this);
        getServer().getPluginManager().registerEvents(new DragonKillListener(this), this);

        saveDefaultConfig();

        speedrunShowdownScoreboard = new SpeedrunShowdownScoreboard(this);
    }

    @Override
    public void run() {
        // Check if any players in survival are in the end
        boolean playerInEnd = false;
        for (Player player : getServer().getOnlinePlayers()) {
            if (
                player.getGameMode() == GameMode.SURVIVAL &&
                player.getLocation().getWorld().getEnvironment() == Environment.THE_END
            ) {
                playerInEnd = true;
            }
        }

        // If it is not sudden death and no player is in the end, increment the timer
        if (!suddenDeath && !playerInEnd) {
            timer--;

            // If timer reaches swap time, begin sudden death
            if (timer <= 0) {
                suddenDeath();
            }
            // Else give warnings
            else {
                for (int warningTime : getConfig().getIntegerList("warning-times")) {
                    if (timer == warningTime) {
                        getServer().broadcastMessage(
                            (timer <= 10 ? ChatColor.RED : ChatColor.YELLOW) + "" +
                            timer + " seconds before sudden death!"
                        );

                        // Play sound to all players
                        for (Player player : getServer().getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                        }
                    }
                }
            }
        }

        speedrunShowdownScoreboard.update();
    }
    
    public void start() {
        // If already running, cancel start
        if (running) {
            getServer().broadcastMessage(ChatColor.YELLOW + "Game already running");
            return;
        }

        // Reset timer
        timer = getConfig().getInt("sudden-death-time");

        // Schedule repeating task
        taskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, this, 20, 20);

        // Set running
        running = true;

        // Set not sudden death
        suddenDeath = false;

        // Broadcast starting game
        getServer().broadcastMessage(ChatColor.GREEN + "Starting game!");

        // Set time to 0
        getServer().getWorld("world").setTime(0);

        // For every player
        for (Player player : getServer().getOnlinePlayers()) {
            // Give compass
            giveCompass(player);

            // Revoke all advancements
            Iterator<Advancement> advancements = getServer().advancementIterator();
            while (advancements.hasNext()) {
                AdvancementProgress advancementProgress = player.getAdvancementProgress(advancements.next());
                for (String criteria : advancementProgress.getAwardedCriteria()){
                    advancementProgress.revokeCriteria(criteria);
                }
            }

            // Give players full health and food
            player.setHealth(20);
            player.setFoodLevel(20);

            // Give resistance
            player.addPotionEffect(new PotionEffect(
                PotionEffectType.DAMAGE_RESISTANCE,
                getConfig().getInt("grace-period") * 20,
                255
            ));
        }
    }

    public void stop() {
        // If already not running, cancel stop
        if (!running) {
            getServer().broadcastMessage(ChatColor.YELLOW + "Game not running");
            return;
        }

        // Set not running
        running = false;

        // Broadcast stopping game
        getServer().broadcastMessage(ChatColor.RED + "Stopping game");

        // Cancel repeating task
        getServer().getScheduler().cancelTask(taskId);
    }

    public void suddenDeath() {
        // Set sudden death
        suddenDeath = true;

        // Set timer to 0
        timer = 0;

        // Broadcast sudden death
        getServer().broadcastMessage("");
        getServer().broadcastMessage(
            "" + ChatColor.WHITE + ChatColor.BOLD + "SUDDEN DEATH > " +
            ChatColor.RED + "Players can no longer respawn"
        );
        getServer().broadcastMessage("");

        // For all players
        for (Player player : getServer().getOnlinePlayers()) {
            // If player has a team, set game mode to survival
            if (speedrunShowdownScoreboard.getTeam(player) != null) {
                player.setGameMode(GameMode.SURVIVAL);
            }

            // Teleport to the end
            World end = getServer().getWorld("world_the_end");
            player.teleport(new Location(end, 0.5, end.getHighestBlockYAt(0, 0) + 1, 0.5, 0, 90));

            // Give resistance
            player.addPotionEffect(new PotionEffect(
                PotionEffectType.DAMAGE_RESISTANCE,
                100, // ticks
                255
            ));

            // Play sound
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);

            // Send title
            player.sendTitle(
                "" + ChatColor.RED + ChatColor.BOLD + "SUDDEN DEATH",
                "Players can no longer respawn",
                10,
                70,
                20
            );
        }
    }

    public void giveCompass(Player player) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        itemMeta.setDisplayName(ChatColor.WHITE + "Tracking Compass");
        itemMeta.setLore(Arrays.asList(
            ChatColor.GRAY + "Right click to point to nearest enemy",
            ChatColor.GRAY + "Left click to point to nearest teammate"
        ));
        compass.setItemMeta(itemMeta);
        player.getInventory().addItem(compass);
    }

    public void win(Team team, String subtitle) {
        for (Player player : getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);

            // If team is not null, send title of winning team
            if (team != null) {
                player.sendTitle(
                    team.getColor() + team.getName().toUpperCase() + " WINS!",
                    subtitle,
                    20,
                    140,
                    40
                );
            }
        }

        // Make fireworks!
        World world = getServer().getWorld("world_the_end");
        int spawnRange = 80;
        Color color = (team != null) ? chatColorToColor(team.getColor()) : Color.WHITE;

        for (int i = 0; i < 50; i++) {
            Location location = new Location(
                world,
                (Math.random() - 0.5) * spawnRange,
                70,
                (Math.random() - 0.5) * spawnRange
            );
            Firework firework = (Firework) world.spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(((int) (Math.random() * 3)) + 1); // 1, 2, or 3
            fireworkMeta.addEffect(
                FireworkEffect.builder().with(
                    FireworkEffect.Type.BALL_LARGE
                ).withColor(
                    color
                ).flicker(
                    true
                ).build()
            );
            firework.setFireworkMeta(fireworkMeta);
        }
    }

    public void setTime(int seconds) {
        timer = seconds;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isSuddenDeath() {
        return suddenDeath;
    }

    public int getTimer() {
        return timer;
    }

    public SpeedrunShowdownScoreboard getSpeedrunShowdownScoreboard() {
        return speedrunShowdownScoreboard;
    }

    public static Color chatColorToColor(ChatColor chatColor) {
        switch (chatColor) {
            case BLACK:
                return Color.fromRGB(0, 0, 0);
            case DARK_BLUE:
                return Color.fromRGB(0, 0, 170);
            case DARK_GREEN:
                return Color.fromRGB(0, 170, 0);
            case DARK_AQUA:
                return Color.fromRGB(0, 170, 170);
            case DARK_RED:
                return Color.fromRGB(170, 0, 0);
            case DARK_PURPLE:
                return Color.fromRGB(170, 0, 170);
            case GOLD:
                return Color.fromRGB(255, 170, 0);
            case GRAY:
                return Color.fromRGB(170, 170, 170);
            case DARK_GRAY:
                return Color.fromRGB(85, 85, 85);
            case BLUE:
                return Color.fromRGB(85, 85, 255);
            case GREEN:
                return Color.fromRGB(85, 255, 85);
            case AQUA:
                return Color.fromRGB(85, 255, 255);
            case RED:
                return Color.fromRGB(255, 85, 85);
            case LIGHT_PURPLE:
                return Color.fromRGB(255, 85, 255);
            case YELLOW:
                return Color.fromRGB(255, 255, 85);
            case WHITE:
                return Color.fromRGB(255, 255, 255);
            default:
                return null;
        }
    }
}