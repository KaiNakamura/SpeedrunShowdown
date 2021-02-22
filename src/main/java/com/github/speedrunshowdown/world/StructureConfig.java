package com.github.speedrunshowdown.world;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import com.github.speedrunshowdown.SpeedrunShowdown;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_16_R1.StructureGenerator;
import net.minecraft.server.v1_16_R1.StructureSettingsFeature;

public enum StructureConfig {
	VILLAGE(StructureGenerator.VILLAGE, "village", 32, 8, 10387312),
	DESERT_PYRAMID(StructureGenerator.DESERT_PYRAMID, "desert-pyramid", 32, 8, 14357617),
	IGLOO(StructureGenerator.IGLOO, "igloo", 32, 8, 14357618),
	JUNGLE_PYRAMID(StructureGenerator.JUNGLE_PYRAMID, "jungle-pyramid", 32, 8, 14357619),
	SWAMP_HUT(StructureGenerator.SWAMP_HUT, "swamp-hut", 32, 8, 14357620),
	PILLAGER_OUTPOST(StructureGenerator.PILLAGER_OUTPOST, "pillager-outpost", 32, 8, 165745296),
	MONUMENT(StructureGenerator.MONUMENT, "monument", 32, 5, 10387313),
	ENDCITY(StructureGenerator.ENDCITY, "endcity", 20, 11, 10387313),
	MANSION(StructureGenerator.MANSION, "mansion", 80, 20, 10387319),
	RUINED_PORTAL(StructureGenerator.RUINED_PORTAL, "ruined-portal", 40, 10, 34222645),
	SHIPWRECK(StructureGenerator.SHIPWRECK, "shipwreck", 24, 4, 165745295),
	OCEAN_RUIN(StructureGenerator.OCEAN_RUIN, "ocean-ruin", 20, 8, 14357621),
	BASTION_REMNANT(StructureGenerator.BASTION_REMNANT, "bastion-remnant", 27, 4, 30084232),
	FORTRESS(StructureGenerator.FORTRESS, "fortress", 27, 4, 30084232),
	NETHER_FOSSIL(StructureGenerator.NETHER_FOSSIL, "nether-fossil", 2, 1, 14357921);

	private static FileConfiguration config = null;
	private static File file = null;
	private final StructureGenerator<?> structureGenerator;
	private final StructureSettingsFeature structureSettingsFeature;

	StructureConfig(StructureGenerator<?> structureGenerator, String path, int defaultSpacing, int defaultSeparation, int defaultSalt) {
        // Save default config, fails silently if config already exists
		saveDefaultConfig();

		// Get configuration section
		ConfigurationSection configSection = getConfig().getConfigurationSection(path);
		
		this.structureGenerator = structureGenerator;
		this.structureSettingsFeature = new StructureSettingsFeature(
			configSection.getInt("spacing", defaultSpacing),
			configSection.getInt("separation", defaultSeparation),
			configSection.getInt("salt", defaultSalt)
		);
	}

	public StructureGenerator<?> getStructureGenerator() {
		return structureGenerator;
	}

	public StructureSettingsFeature getStructureSettingsFeature() {
		return structureSettingsFeature;
	}

	public static void loadConfig() {
		// Get plugin
		Plugin plugin = SpeedrunShowdown.getInstance();

		// Create target file to write to
		if (file == null) {
			file = new File(plugin.getDataFolder(), "structures.yml");
		}
		Bukkit.getLogger().info(file.toString());

		// Create config
		config = YamlConfiguration.loadConfiguration(file);

		// Create input stream pointing to defaults
		InputStream inputStream = plugin.getResource("structures.yml");

		// If input stream isn't null, set config defaults from input stream
		if (inputStream != null) {
			// Get default config from input stream
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
				new InputStreamReader(inputStream)
			);
			// Set config defaults
			config.setDefaults(defaultConfig);
		}
	}

	public static FileConfiguration getConfig() {
		// If config null, load config
		if (config == null) {
			loadConfig();
		}

		return config;
	}

	public static void saveConfig() {
		// If config or file is null, return
		if (config == null || file == null) {
			return;
		}

		// Try to save config
		try {
			getConfig().save(file);
		}
		catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + config, e);
		}
	}

	public static void saveDefaultConfig() {
		Plugin plugin = SpeedrunShowdown.getInstance();

		// If file null, create new file
		if (file == null) {
			file = new File(plugin.getDataFolder(), "structures.yml");
		}

		// If the file doesn't exist, save file
		if (!file.exists()) {
			plugin.saveResource("structures.yml", false);
		}
	}
}
