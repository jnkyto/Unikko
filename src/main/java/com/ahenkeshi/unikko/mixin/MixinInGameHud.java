/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.utils.CommonUtils;
import com.ahenkeshi.unikko.utils.TickRateUtils;
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

import java.awt.*;
import java.text.DecimalFormat;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Shadow @Final private MinecraftClient client;
    @Shadow public abstract TextRenderer getTextRenderer();

    private final DecimalFormat df = new DecimalFormat("0.0");

    @Inject(method="render", at=@At("RETURN"))
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci)  {
        if(this.client.player != null) {
            String fps = ("fps: " + Integer.parseInt(client.fpsDebugString.split(" ")[0].split("/")[0]));
            int screenHeight = client.getWindow().getScaledHeight();
            double xpos = client.player.getX();
            double ypos = client.player.getY();
            double zpos = client.player.getZ();
            String yaw = client.player.getHorizontalFacing().asString();
            String yawStr = CommonUtils.facing(yaw);
            if(!this.client.options.debugEnabled && (Boolean) Unikko.softConfig.hudRender.value()) {
                TextRenderer textRenderer = this.getTextRenderer();
                textRenderer.drawWithShadow(matrices, Unikko.MODID + " " + Unikko.VERSION + Unikko.DEV,
                        (Integer) Unikko.softConfig.watermarkX.value(), (Integer) Unikko.softConfig.watermarkY.value(),
                        CommonUtils.rainbow(0));
                        // ^ render watermark
                textRenderer.drawWithShadow(matrices, Unikko.REL_DATE, (Integer) Unikko.softConfig.reldateX.value(),
                        (Integer) Unikko.softConfig.reldateY.value(), CommonUtils.rainbow(300));
                        // ^ render release date
                textRenderer.drawWithShadow(matrices, (yawStr + " " + df.format(xpos) + " " + df.format(ypos) + " " +
                        df.format(zpos)), (Integer) Unikko.softConfig.yawX.value(), screenHeight - 26, 16777215);
                        // ^ render yaw and coords (i gave up on yawY. i promise i'll figure it out)
                textRenderer.drawWithShadow(matrices, fps, (Integer) Unikko.softConfig.fpsX.value(),
                        (Integer) Unikko.softConfig.fpsY.value(), 16777215);
                        // ^ render fps
                textRenderer.drawWithShadow(matrices, ("tps: " + TickRateUtils.getTickrate()),
                        (Integer) Unikko.softConfig.tpsX.value(), (Integer) Unikko.softConfig.tpsY.value(), 16777215);
                        // ^ render tps
                textRenderer.drawWithShadow(matrices, (String) TickRateUtils.getSinceLastTick(true),
                        (Integer) Unikko.softConfig.lagX.value(), (Integer) Unikko.softConfig.lagY.value(), Color.red.getRGB());
                        // ^ render lag alert
            }
        }
    }
}
