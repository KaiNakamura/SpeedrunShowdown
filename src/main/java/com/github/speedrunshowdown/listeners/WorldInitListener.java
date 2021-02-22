package com.github.speedrunshowdown.listeners;

import java.lang.reflect.Field;
import java.util.Map;

import com.github.speedrunshowdown.world.StructureConfig;

import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import net.minecraft.server.v1_16_R1.*;

public class WorldInitListener implements Listener {
	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		// If world not an instance of craft world, return
		if (!(event.getWorld() instanceof CraftWorld)) {
			return;
		}

		// Create craft world
		final CraftWorld world = (CraftWorld) event.getWorld();

		try {
			// Get chunk provider
			ChunkProviderServer chunkProvider = world.getHandle().getChunkProvider();

			// Get player chunk map
			PlayerChunkMap playerChunkMap = chunkProvider.playerChunkMap;

			// Get chunck generator from player chunk map
			Field chunkGeneratorField = PlayerChunkMap.class.getDeclaredField("chunkGenerator");
			chunkGeneratorField.setAccessible(true);
			Object chunkGeneratorObject = chunkGeneratorField.get(playerChunkMap);

			// If chunk generator object not an instance of chunk generator, return
			if (!(chunkGeneratorObject instanceof ChunkGenerator)) {
				return;
			}

			// Create chunk generator
			ChunkGenerator chunkGenerator = (ChunkGenerator) chunkGeneratorObject;

			// Create structure settings
			Map<StructureGenerator<?>, StructureSettingsFeature> structureSettings = chunkGenerator.getSettings().a();

			// For all structure configs
			for (StructureConfig structureConfig : StructureConfig.values()) {
				// Get structure generator
				StructureGenerator<?> structureGenerator = structureConfig.getStructureGenerator();

				// If structure settins contains structure generator, put structure settings feature
				if (structureSettings.containsKey(structureGenerator)) {
					structureSettings.put(structureGenerator, structureConfig.getStructureSettingsFeature());
				}
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
