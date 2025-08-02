package standard.mod.standard;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum Type implements StringRepresentable {
	XBOX_360("Xbox 360", "X-box 360"),
	PLAYSTATION_5("Playstation 5", "Paystation 5"),
	XBOX_ONE("Xbox One", "Xbox 1"),
	PLAYSTATION_4("Playstation 4", "Paystation 4"),
	STEAM("Steam", "Epic Games");

	public final String d;
	public final String d2;

	Type(String d, String d2) {
		this.d = d;
		this.d2 = d2;
	}

	public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase(Locale.ROOT);
	}
}
