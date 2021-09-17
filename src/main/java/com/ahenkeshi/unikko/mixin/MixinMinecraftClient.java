/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method ="getWindowTitle" ,at = @At("RETURN"), cancellable = true)
    private void getWindowTitle(final CallbackInfoReturnable<String> cir)   {
        if(Unikko.DEV_MODE) {
            cir.setReturnValue(Unikko.MODID + " " + Unikko.VERSION + " (" + Unikko.REL_DATE + ") *");
            // Decided that changing the window title is a stupid idea.
            // Still do it if devmode enabled because idk
        }
    }

    @Inject(method = "onResolutionChanged", at = @At("HEAD"), cancellable = true)
    private void onResolutionChanged(CallbackInfo ci)  {
        Unikko.logger.info("Resolution changed. Print this message just because.");
    }

}
