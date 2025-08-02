package standard.mod.standard.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import standard.mod.standard.DutchWindmills;

@Mixin(ToastManager.class)
public class ToastManagerMixin {
	@WrapOperation(method =
			"method_61992(Lnet/minecraft/client/gui/components/toasts/Toast;)Z"
			,at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/Toast;getSoundEvent()Lnet/minecraft/sounds/SoundEvent;"))
	SoundEvent update(Toast instance, Operation<SoundEvent> original) {
		if (instance instanceof AdvancementToast toast) {
			return (toast.getSoundEvent() != null ? DutchWindmills.T2 : DutchWindmills.T).get(DutchWindmills.config.type);
		}
		return original.call(instance);
	}
}
