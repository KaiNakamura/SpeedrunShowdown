package com.github.speedrunshowdown.listeners;

import java.io.File;

import com.github.speedrunshowdown.SpeedrunShowdown;
import com.github.speedrunshowdown.SpeedrunShowdownConfig;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfigClickListener implements Listener {
    private SpeedrunShowdown plugin;

    public ConfigClickListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onConfigClick(InventoryClickEvent event) {
        // If config menu is open and item is clicked, process input
        if (
            event.getView().getTitle().toLowerCase().contains("config") &&
            event.getCurrentItem() != null
        ) {
            // Get clicked item by material
            SpeedrunShowdownConfig config = SpeedrunShowdownConfig.getConfigByMaterial(event.getCurrentItem().getType());

            // If selected reset, reset to defaults
            if (config.getPath() == "RESET") {
                new File(plugin.getDataFolder(), "config.yml").delete();
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
            }
            // Else, toggle selected value
            else {
                plugin.getConfig().set(config.getPath(), !plugin.getConfig().getBoolean(config.getPath()));
                plugin.saveConfig();
            }

            // Update gui
            event.getClickedInventory().setContents(SpeedrunShowdownConfig.getItems(plugin.getConfig()));

            // Cancel event, prevents moving items around
            event.setCancelled(true);

            // Play sound
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }
    }
}