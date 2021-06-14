package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method ="getWindowTitle" ,at = @At("RETURN"), cancellable = true)
    private void getWindowTitle(final CallbackInfoReturnable<String> cir)   {
        cir.setReturnValue(Unikko.MODID + " " + Unikko.VERSION + " (" + Unikko.REL_DATE + ") *");
    }
}
