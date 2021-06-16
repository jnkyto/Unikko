package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.utils.BoolUtils;
import com.ahenkeshi.unikko.utils.FacingTowards;
import com.ahenkeshi.unikko.utils.RainbowColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    MinecraftClient mc = MinecraftClient.getInstance();
    private final DecimalFormat df = new DecimalFormat("0.0");
    String yawStr;

    @Shadow public abstract TextRenderer getFontRenderer();

    @Inject(method="render", at=@At("RETURN"))
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci)  {
        Text devd = new TranslatableText("unikko.devd");
        if(this.mc.player != null) {
            String fps = ("fps: " + Integer.parseInt(mc.fpsDebugString.split(" ")[0].split("/")[0]));
            int screenHeight = mc.getWindow().getScaledHeight();
            int screenWidth = mc.getWindow().getScaledWidth();
            double xpos = mc.player.getX();
            double ypos = mc.player.getY();
            double zpos = mc.player.getZ();
            String yaw = mc.player.getHorizontalFacing().asString();
            yawStr = FacingTowards.get(yaw);
            if(!this.mc.options.debugEnabled && BoolUtils.get("hudRender")) {
                TextRenderer textRenderer = this.getFontRenderer();
                textRenderer.drawWithShadow(matrices, Unikko.MODID + " " + Unikko.VERSION,
                        10, 10, RainbowColor.gen(0));
                textRenderer.drawWithShadow(matrices, Unikko.REL_DATE, 10, 20, RainbowColor.gen(300));
                textRenderer.drawWithShadow(matrices, (yawStr + " " + df.format(xpos) + " " + df.format(ypos) + " " +
                        df.format(zpos)), 10, screenHeight - 26, 16777215);
                textRenderer.drawWithShadow(matrices, fps, 10, screenHeight - 36, 16777215);
            }
        }
    }

}
