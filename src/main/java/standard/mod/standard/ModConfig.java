package standard.mod.standard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
	public Type type = Type.JAVA;
	public static final Codec<ModConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Type.CODEC.fieldOf("sound_platform").forGetter(a -> a.type)
	).apply(instance, f -> {
		ModConfig modConfig = new ModConfig();
		modConfig.type = f;
		return modConfig;
	}));

	public static ModConfig load() throws IOException {
		Path resolve = FabricLoader.getInstance().getConfigDir().resolve("jimmy-smiths.json");
		if (!resolve.toFile().exists()) {
			resolve.getParent().toFile().mkdirs();
			ModConfig modConfig = new ModConfig();
			modConfig.save();
			return modConfig;
		} else {
			String s = Files.readString(resolve, StandardCharsets.UTF_8);
			JsonElement json;
			try {
				json = new Gson().fromJson(s, JsonElement.class);
			} catch (JsonSyntaxException e) {
				DutchWindmills.LOGGER.warn("Failed to parse config; resetting to defaults", e);
				ModConfig modConfig = new ModConfig();
				modConfig.save();
				return modConfig;
			}

			return CODEC.parse(JsonOps.INSTANCE, json).resultOrPartial().orElseGet(() -> migrateOrReset(json));
		}
	}

	private static ModConfig migrateOrReset(JsonElement json) {
		ModConfig modConfig = new ModConfig();

		if (json != null && json.isJsonObject()) {
			JsonElement soundPlatform = json.getAsJsonObject().get("sound_platform");
			if (soundPlatform != null && soundPlatform.isJsonPrimitive()) {
				String platform = soundPlatform.getAsString();
				if ("playstation_3".equalsIgnoreCase(platform) || "ps3".equalsIgnoreCase(platform)) {
					modConfig.type = Type.LEGACY;
				} else {
					DutchWindmills.LOGGER.warn("Unknown sound platform '{}' in config; resetting to defaults", platform);
				}
			}
		}

		try {
			modConfig.save();
		} catch (IOException e) {
			DutchWindmills.LOGGER.warn("Failed to save migrated config", e);
		}

		return modConfig;
	}

	public void save() throws IOException {
		JsonElement jsonElement = CODEC.encodeStart(JsonOps.INSTANCE, this).resultOrPartial().orElseThrow();
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
		Files.writeString(FabricLoader.getInstance().getConfigDir().resolve("jimmy-smiths.json"), json);
	}
}
