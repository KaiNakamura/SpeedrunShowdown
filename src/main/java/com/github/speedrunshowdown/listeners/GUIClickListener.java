package com.github.speedrunshowdown.listeners;

import com.github.speedrunshowdown.gui.GUI;
import com.github.speedrunshowdown.gui.GUIItem;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        // Search for a gui that matches the name of the inventory clicked
        GUI gui = GUI.getGui(event.getView().getTitle());

        // If gui found, process input
        if (gui != null) {
            // Search for an item at the clicked slot
            GUIItem item = gui.getItem(event.getSlot());

            // If item found, run on click
            if (item != null) {
                item.onClick(event);
            }

            // Cancel event, prevents moving items around
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
}