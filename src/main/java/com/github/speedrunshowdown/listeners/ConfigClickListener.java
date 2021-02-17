package com.github.speedrunshowdown.listeners;

import java.io.File;

import com.github.speedrunshowdown.SpeedrunShowdown;
import com.github.speedrunshowdown.gui.ConfigOption;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ConfigClickListener implements Listener {
    private SpeedrunShowdown plugin;

    public ConfigClickListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onConfigClick(InventoryClickEvent event) {
        // If config menu is open and item is clicked, process input
        if (
            event.getView().getTitle().toLowerCase().contains("speedrun showdown") &&
            event.getCurrentItem() != null
        ) {
            // Get player
            Player player = (Player) event.getWhoClicked();

            // Get clicked item by material
            ConfigOption configOption = ConfigOption.getConfigOptionByMaterial(
                event.getCurrentItem().getType()
            );

            // If selected reset, reset to defaults
            if (configOption == ConfigOption.RESET) {
                new File(plugin.getDataFolder(), "config.yml").delete();
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
            }
            // Else, change selected value
            else {
                String path = configOption.getPath();
                Object data = plugin.getConfig().get(path);

                // If data is a boolean, toggle value
                if (data instanceof Boolean) {
                    plugin.getConfig().set(path, !((boolean) data));
                }
                // Else if data is an integer, increase or decrease value
                else if (data instanceof Integer) {
                    ClickType clickType = event.getClick();
                    switch (clickType) {
                        case LEFT:
                            plugin.getConfig().set(path, (int) data + 1);
                            break;
                        case RIGHT:
                            plugin.getConfig().set(path, (int) data - 1);
                            break;
                        case SHIFT_LEFT:
                            plugin.getConfig().set(path, (int) data * 2);
                            break;
                        case SHIFT_RIGHT:
                            plugin.getConfig().set(path, (int) data / 2);
                            break;
                        default:
                            break;
                    }
                }

                // Save changes
                plugin.saveConfig();
            }

            // Update gui
            Inventory gui = event.getClickedInventory();
            gui.setContents(ConfigOption.getItems(plugin.getConfig(), gui.getSize()));

            // Cancel event, prevents moving items around
            event.setCancelled(true);

            // Play sound
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }
    }
}