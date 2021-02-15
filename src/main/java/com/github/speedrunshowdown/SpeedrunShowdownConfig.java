package com.github.speedrunshowdown;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum SpeedrunShowdownConfig {
	PREVENT_BED_EXPLOSTIONS(
		"prevent-bed-explosions",
		"Prevent Bed Explosions",
		Material.RED_BED
	),
	PREVENT_RESPAWN_ANCHOR_EXPLOSIONS(
		"prevent-respawn-anchor-explosions",
		"Prevent Respawn Anchor Explosions",
		Material.RESPAWN_ANCHOR
	),
	INDESTRUCTABLE_SPAWNERS(
		"indestructable-spawners",
		"Indestructable Spawners",
		Material.SPAWNER
	),
	MUST_KILL_DRAGON_TO_WIN(
		"must-kill-dragon-to-win",
		"Must Kill Dragon To Win",
		Material.DRAGON_HEAD
	),
	HIDE_SPECTATOR_ADVANCEMENTS(
		"hide-spectator-advancements",
		"Hide Spectator Advancements",
		Material.ENDER_EYE
	),
	GIVE_ARMOR(
		"give-armor",
		"Give Armor",
		Material.LEATHER_CHESTPLATE
	),
	RESET(
		"RESET",
		"Reset to Defaults",
		Material.BARRIER
	);

	private String path, name;
	private Material material;

	SpeedrunShowdownConfig(String path, String name, Material material) {
		this.path = path;
		this.name = name;
		this.material = material;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public Material getMaterial() {
		return material;
	}

	public static SpeedrunShowdownConfig getConfigByPath(String path) {
		for (SpeedrunShowdownConfig value : SpeedrunShowdownConfig.values()) {
			if (value.getPath().equals(path)) {
				return value;
			}
		}
		return null;
	}

	public static SpeedrunShowdownConfig getConfigByName(String name) {
		for (SpeedrunShowdownConfig value : SpeedrunShowdownConfig.values()) {
			if (value.getName().equals(name)) {
				return value;
			}
		}
		return null;
	}

	public static SpeedrunShowdownConfig getConfigByMaterial(Material material) {
		for (SpeedrunShowdownConfig value : SpeedrunShowdownConfig.values()) {
			if (value.getMaterial() == material) {
				return value;
			}
		}
		return null;
	}

	public static ItemStack getItem(SpeedrunShowdownConfig value, FileConfiguration config) {
		ItemStack item = new ItemStack(value.getMaterial());
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + value.getName());
		ArrayList<String> itemLore = new ArrayList<>();

		Object data = config.get(value.getPath(), null);
		if (data != null) {
			if (data instanceof Boolean) {
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.GRAY + "Enabled: " +
					((boolean) data ? ChatColor.GREEN : ChatColor.RED) + data.toString()
				);
			}
		}

		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack[] getItems(FileConfiguration config) {
		ItemStack[] items = new ItemStack[SpeedrunShowdownConfig.values().length];
		for (int i = 0; i < items.length; i++) {
			items[i] = SpeedrunShowdownConfig.getItem(SpeedrunShowdownConfig.values()[i], config);
		}
		return items;
	}
}
