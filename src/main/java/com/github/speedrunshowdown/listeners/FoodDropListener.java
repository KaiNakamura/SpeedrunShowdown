package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.Constants;
import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class FoodDropListener implements Listener {
    @EventHandler
    public void onAnimalDeath(EntityDeathEvent event) {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

		// If plugin is running and should cook food, cook food
        if (
            plugin.isRunning() &&
            plugin.getConfig().getBoolean("cook-food")
        ) {
            // For all drops
            for (ItemStack drop : event.getDrops()) {
                // For all recipes
                for (Material input : Constants.FOOD_RECIPES.keySet()) {
                    // If drop matches input, replace drop with result
                    if (drop.getType() == input) {
                        drop.setType(Constants.FOOD_RECIPES.get(input));
                    }
                }
            }
        }
    }
}