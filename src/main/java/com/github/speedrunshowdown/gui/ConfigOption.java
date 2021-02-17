package com.github.speedrunshowdown.gui;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ConfigOption {
	SUDDEN_DEATH_TIME(
		"sudden-death-time",
		Material.END_STONE,
		"minutes"
	),
	COUNTDOWN_TIME(
		"countdown-time",
		Material.CLOCK,
		"seconds"
	),
	GRACE_PERIOD(
		"grace-period",
		Material.SHIELD,
		"seconds"
	),
	RESPAWN_INVINCIBILITY(
		"respawn-invincibility",
		Material.TOTEM_OF_UNDYING,
		"seconds"
	),
	PORTAL_INVINCIBILITY(
		"portal-invincibility",
		Material.END_PORTAL_FRAME,
		"seconds"
	),
	PREVENT_BED_EXPLOSTIONS(
		"prevent-bed-explosions",
		Material.RED_BED
	),
	PREVENT_RESPAWN_ANCHOR_EXPLOSIONS(
		"prevent-respawn-anchor-explosions",
		Material.RESPAWN_ANCHOR
	),
	INDESTRUCTABLE_SPAWNERS(
		"indestructable-spawners",
		Material.SPAWNER
	),
	MUST_KILL_DRAGON_TO_WIN(
		"must-kill-dragon-to-win",
		Material.DRAGON_HEAD
	),
	HIDE_SPECTATOR_ADVANCEMENTS(
		"hide-spectator-advancements",
		Material.ENDER_EYE
	),
	GIVE_ARMOR(
		"give-armor",
		Material.LEATHER_CHESTPLATE
	),
	RESET(
		"RESET",
		"Reset to Defaults",
		Material.BARRIER
	);

	private String path, name, suffix;
	private Material material;

	ConfigOption(String path, String name, Material material, String suffix) {
		this.path = path;
		this.name = name;
		this.material = material;
		this.suffix = suffix;
	}

	ConfigOption(String path, String name, Material material) {
		this(path, name, material, "");
	}

	ConfigOption(String path, Material material, String suffix) {
		this(path, WordUtils.capitalize(path.replace('-', ' ')), material, suffix);
	}

	ConfigOption(String path, Material material) {
		this(path, material, "");
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

	public static ConfigOption getConfigOptionByPath(String path) {
		for (ConfigOption value : ConfigOption.values()) {
			if (value.getPath().equals(path)) {
				return value;
			}
		}
		return null;
	}

	public static ConfigOption getConfigOptionByMaterial(Material material) {
		for (ConfigOption value : ConfigOption.values()) {
			if (value.getMaterial() == material) {
				return value;
			}
		}
		return null;
	}

	public static ItemStack getItem(ConfigOption configOption, FileConfiguration config) {
		ItemStack item = new ItemStack(configOption.getMaterial());
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + configOption.getName());
		ArrayList<String> itemLore = new ArrayList<>();

		Object data = config.get(configOption.getPath(), null);
		if (data != null) {
			if (data instanceof Boolean) {
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.GRAY + "Enabled: " +
					((boolean) data ? ChatColor.GREEN : ChatColor.RED) + data.toString()
				);
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Click to toggle"
				);
			}
			else if (data instanceof Integer) {
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.YELLOW + data.toString() + 
					ChatColor.RESET + " " + ChatColor.GRAY + configOption.suffix
				);
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Left/Right click to +/-"
				);
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Shift click to double/half"
				);
			}
		}

		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack[] getItems(FileConfiguration config) {
		ItemStack[] items = new ItemStack[ConfigOption.values().length];
		for (int i = 0; i < items.length; i++) {
			items[i] = ConfigOption.getItem(ConfigOption.values()[i], config);
		}
		return items;
	}
}
