package com.github.speedrunshowdown.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.speedrunshowdown.Constants;
import com.github.speedrunshowdown.SpeedrunShowdown;

public class ToolUseListener implements Listener {
    @EventHandler
    public void onToolUse(PlayerInteractEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

        // If plugin is running and should make tools efficient, make tools efficient
        if (
            plugin.isRunning() &&
            plugin.getConfig().getBoolean("efficient-tools")
        ) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();

            // If item is a tool, enchant with efficiency 3 and unbreaking 1
            if (isEfficientTool(item.getType())) {
                item.addEnchantment(Enchantment.DIG_SPEED, 3);
                item.addEnchantment(Enchantment.DURABILITY, 1);
            }
        }
    }

    public static boolean isEfficientTool(Material material) {
        for (Material tool : Constants.EFFICIENT_TOOLS) {
            if (material == tool) {
                return true;
            }
        }
        return false;
    }
}