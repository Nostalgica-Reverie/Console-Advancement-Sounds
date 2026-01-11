package standard.mod.standard;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DutchWindmills implements ModInitializer {
	public static final String MOD_ID = "console_advancement_sounds";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ModConfig config;
	public static final Map<Type, SoundEvent> T = new HashMap<>();
	public static final Map<Type, SoundEvent> T2 = new HashMap<>();
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		for (Type value : Type.values()) {
			T.put(value, SoundEvent.createVariableRangeEvent(ResourceLocation.parse("console_advancement_sounds:advancement-sound." + value.getSerializedName())));

			T2.put(value, SoundEvent.createVariableRangeEvent(ResourceLocation.parse("console_advancement_sounds:advancement-sound-rare." + value.getSerializedName())));
		}
		LOGGER.info("Hello NeoForge world!");
		try {
			config = ModConfig.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	}