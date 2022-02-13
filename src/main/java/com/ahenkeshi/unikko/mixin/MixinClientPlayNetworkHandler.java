/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.TickRateUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.telemetry.TelemetrySender;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(MinecraftClient client, Screen screen, ClientConnection connection, GameProfile profile, TelemetrySender telemetrySender, CallbackInfo ci)    {
        CommandHandler.init();
    }

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void onGameJoin(CallbackInfo ci)  {
        TickRateUtils.gameJoined(System.currentTimeMillis());
    }

    @Inject(method = "onWorldTimeUpdate", at = @At("RETURN"))
    public void onWorldTimeUpdate(WorldTimeUpdateS2CPacket packet, CallbackInfo ci)   {
        TickRateUtils.onReceivePacket();
    }

    @Inject(method = "onCommandTree", at = @At("TAIL"))
    public void onCommandTree(CommandTreeS2CPacket packet, CallbackInfo ci)   {
        CommandHandler.init();
    }
}
