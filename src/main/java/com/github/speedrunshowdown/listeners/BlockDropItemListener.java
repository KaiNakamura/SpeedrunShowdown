package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDropItemListener implements Listener {
    private SpeedrunShowdown plugin;

    public BlockDropItemListener(SpeedrunShowdown plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event) {
		// If plugin is running and should randomize drops, randomize drops
        if (
			plugin.isRunning() &&
			plugin.getConfig().getBoolean("randomize-drops")
		) {
            // If player does not have silk touch, change item
            ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
            if (heldItem == null || heldItem.getItemMeta() == null || !heldItem.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                for (Item droppedItem : event.getItems()) {
                    plugin.getRandomItem(droppedItem);
                }
            }
        }
    }
}