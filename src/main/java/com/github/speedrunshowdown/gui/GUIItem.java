package com.github.speedrunshowdown.gui;

import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIItem {
	private ItemStack itemStack;
	private Consumer<InventoryClickEvent> onClick;

	public GUIItem(ItemStack itemStack, Consumer<InventoryClickEvent> onClick) {
		this.itemStack = itemStack;
		this.onClick = onClick;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void onClick(InventoryClickEvent event) {
		onClick.accept(event);
	}
}
