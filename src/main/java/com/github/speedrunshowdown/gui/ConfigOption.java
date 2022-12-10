package com.github.speedrunshowdown.gui;

import java.io.File;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
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
	PORTAL_ALERTS(
		"portal-alerts",
		Material.OBSIDIAN
	),
	WORLD_BORDER(
		"world-border",
		Material.GRASS_BLOCK
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
	HIDE_SPECTATOR_ADVANCEMENTS(
		"hide-spectator-advancements",
		Material.ENDER_EYE
	),
	HIDE_PLAYER_ADVANCEMENTS(
		"hide-player-advancements",
		Material.CRAFTING_TABLE
	),
	GIVE_COMPASS(
		"give-compass",
		Material.COMPASS
	),
	KEEP_ARMOR(
		"keep-armor",
		Material.DIAMOND_CHESTPLATE
	),
	KEEP_TOOLS(
		"keep-tools",
		Material.DIAMOND_PICKAXE
	),
	LOWER_DRAGON_HEALTH_IN_SUDDEN_DEATH(
		"lower-dragon-health-in-sudden-death",
		Material.DRAGON_HEAD
	),
	DESTROY_END_CRYSTALS_IN_SUDDEN_DEATH(
		"destroy-end-crystals-in-sudden-death",
		Material.END_CRYSTAL
	),
	MUST_KILL_DRAGON_TO_WIN(
		"must-kill-dragon-to-win",
		Material.DRAGON_EGG
	),
	GIVE_ARMOR(
		"give-armor",
		Material.LEATHER_CHESTPLATE
	),
	EFFICIENT_TOOLS(
		"efficient-tools",
		Material.BEACON
	),
	SMELT_ORES(
		"smelt-ores",
		Material.IRON_ORE
	),
	COOK_FOOD(
		"cook-food",
		Material.COOKED_BEEF
	),
	RANDOMIZE_DROPS(
		"randomize-drops",
		Material.DISPENSER
	),
	PERMANENT_POTIONS(
		"permanent-potions",
		Material.POTION
	),
	RESET(
		"RESET",
		"Reset to Defaults",
		Material.BARRIER
	);

	private final String path, name, suffix;
	private final Material material;

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
	
	public GUIItem getItem() {
        SpeedrunShowdown plugin = SpeedrunShowdown.getInstance();

		// Create new item stack with config option material
		ItemStack itemStack = new ItemStack(material);

		// Get item meta of the item stack and set display to the name of the config option
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + name);
		// Create item lore list
		ArrayList<String> itemLore = new ArrayList<>();

		// Get path and data at path location
		Object data = plugin.getConfig().get(path, null);

		// If data is not null, add item lore and on click event
		if (data != null) {
			// If data is a boolean, add status and boolean instructions
			if (data instanceof Boolean) {
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.GRAY + "Enabled: " +
					((boolean) data ? ChatColor.GREEN : ChatColor.RED) + data.toString()
				);
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Click to toggle"
				);
			}
			// Else if data is an integer, add status and integer instructions
			else if (data instanceof Integer) {
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.YELLOW + data.toString() + 
					ChatColor.RESET + " " + ChatColor.GRAY + suffix
				);
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Left/Right click to +/-"
				);
				itemLore.add(
					ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Shift click to double/half"
				);
			}

		}

		// Add lore to item meta
		itemMeta.setLore(itemLore);

		// Add item meta to item stack
		itemStack.setItemMeta(itemMeta);

		// Create on click event
		Consumer<InventoryClickEvent> onClick = event -> {
			// If selected reset, reset to defaults
			if (this == RESET) {
				new File(plugin.getDataFolder(), "config.yml").delete();
				plugin.saveDefaultConfig();
				plugin.reloadConfig();
			}
			// Else if data is not null, change data value
			else if (data != null) {
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

			// Get who clicked
			Player player = (Player) event.getWhoClicked();

			// Play sound
			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

			// Update gui
			getGui().show(player);
		};

		// Return new gui item with the item stack and event
		return new GUIItem(itemStack, onClick);
	}

	public static GUIItem[] getItems() {
		GUIItem[] items = new GUIItem[values().length];
		for (int i = 0; i < items.length; i++) {
			items[i] = values()[i].getItem();
		}
		return items;
	}

	public static GUI getGui() {
		return new GUI("Config", getItems());
	}
}
