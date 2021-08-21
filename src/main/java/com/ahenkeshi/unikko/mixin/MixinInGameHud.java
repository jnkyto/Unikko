/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.utils.SoftConfigUtils;
import com.ahenkeshi.unikko.utils.FacingTowards;
import com.ahenkeshi.unikko.utils.RainbowColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Shadow @Final
    private MinecraftClient client;
    private final DecimalFormat df = new DecimalFormat("0.0");
    String yawStr;

    @Shadow public abstract TextRenderer getTextRenderer();

    @Inject(method="render", at=@At("RETURN"))
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci)  {
        // Text devd = new TranslatableText("unikko.devd");
        if(this.client.player != null) {
            String fps = ("fps: " + Integer.parseInt(client.fpsDebugString.split(" ")[0].split("/")[0]));
            int screenHeight = client.getWindow().getScaledHeight();
            // int screenWidth = mc.getWindow().getScaledWidth(); not needed atm
            double xpos = client.player.getX();
            double ypos = client.player.getY();
            double zpos = client.player.getZ();
            String yaw = client.player.getHorizontalFacing().asString();
            yawStr = FacingTowards.get(yaw);
            if(!this.client.options.debugEnabled && (boolean) SoftConfigUtils.get("hudRender")) {
                TextRenderer textRenderer = this.getTextRenderer();
                textRenderer.drawWithShadow(matrices, Unikko.MODID + " " + Unikko.VERSION,
                        (int) SoftConfigUtils.get("watermarkX"), (int) SoftConfigUtils.get("watermarkY"),
                        RainbowColor.gen(0));
                        // ^ render watermark
                textRenderer.drawWithShadow(matrices, Unikko.REL_DATE, (int) SoftConfigUtils.get("reldateX"),
                        (int) SoftConfigUtils.get("reldateY"), RainbowColor.gen(300));
                        // ^ render release date
                textRenderer.drawWithShadow(matrices, (yawStr + " " + df.format(xpos) + " " + df.format(ypos) + " " +
                        df.format(zpos)), (int) SoftConfigUtils.get("yawX"), screenHeight - 26, 16777215);
                        // ^ render yaw and coords (i gave up on yawY. i promise i'll figure it out)
                textRenderer.drawWithShadow(matrices, fps, (int) SoftConfigUtils.get("fpsX"),
                        (int) SoftConfigUtils.get("fpsY"), 16777215);
                        // ^ render fps
            }
        }
    }
}
