package standard.mod.standard.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import standard.mod.standard.DutchWindmills;
import standard.mod.standard.Type;

import java.io.IOException;
import java.util.Locale;

public class ConfigScreen extends Screen {

	private final Screen parent;

	public ConfigScreen(Screen parent) {
		super(Component.literal("Dutch config screen"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();
		boolean s;
		if (Minecraft.getInstance().getGameProfile() != null) {
			String name1 = Minecraft.getInstance().getGameProfile().name();
			s = name1 != null && (name1.toLowerCase(Locale.ROOT).equals("nicgamertv") || name1.toLowerCase(Locale.ROOT).equals("jab125"));
		} else {
			s = false;
		}
		s = s? Math.random() < 0.003 : Math.random() < 0.08;
		s = Math.random() < 0.01;
		boolean finalS = s;
		CycleButton.Builder<Type> type = new CycleButton.Builder<>(a -> Component.literal(finalS ? a.d2 : a.d), () -> DutchWindmills.config.type);
		type.withValues(Type.values());
		CycleButton<Type> jeff = type.create(this.width / 2 - 100, 20, 200, 20, Component.literal(s ? "taip (ing)" : "TYPE"), (a, b) -> {
			DutchWindmills.config.type = b;
		});
		addRenderableWidget(jeff);

		addRenderableWidget(Button.builder(Component.literal(s ? "Shave" : "Save"), a -> onClose()).bounds(this.width / 2 - 100, this.height - 25, 200, 20).tooltip(Tooltip.create(Component.literal("Save the config"))).build());
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
		super.extractRenderState(guiGraphics, i, j, f);
		guiGraphics.centeredText(font, "The config screen", (int) (this.width / 2), (int) (5), 0xffffffff);
	}

	@Override
	public void onClose() {
		try {
			DutchWindmills.config.save();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		minecraft.setScreen(parent);
	}
}
