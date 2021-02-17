package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.Material;
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
		// If plugin is running
        if (plugin.isRunning()) {
            // Get held item
            ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
            // If player does not have silk touch, change drops
            if (
                heldItem == null ||
                heldItem.getItemMeta() == null ||
                !heldItem.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)
            ) {
                // For all dropped items
                for (Item droppedItem : event.getItems()) {
                    // If should randomize drops, randomize drops
                    if (plugin.getConfig().getBoolean("randomize-drops")) {
                        plugin.randomizeItem(droppedItem);
                    }
                    // Else if should smelt ores, smelt ores
                    else if (plugin.getConfig().getBoolean("smelt-ores")) {
                        ItemStack itemStack = droppedItem.getItemStack();

                        // Find ingot type if it exists
                        Material ingot;
                        switch (itemStack.getType()) {
                            case IRON_ORE:
                                ingot = Material.IRON_INGOT;
                                break;
                            case GOLD_ORE:
                                ingot = Material.GOLD_INGOT;
                                break;
                            default:
                                ingot = null;
                                break;
                        }

                        // If ingot does exist, change dropped item to ingot
                        if (ingot != null) {
                            droppedItem.getWorld().dropItem(
                                droppedItem.getLocation(),
                                new ItemStack(ingot, itemStack.getAmount())
                            );
                            droppedItem.remove();
                        }
                    }
                }
            }
        }
    }
}