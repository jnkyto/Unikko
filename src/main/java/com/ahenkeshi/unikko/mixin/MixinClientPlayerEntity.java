/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.mixin;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.cmd.CommandHandler;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity extends AbstractClientPlayerEntity {

    @Unique private final String cmdPrefix = Unikko.SOFTCONFIG.cmdPrefix.value();

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile)  {
        super(world, profile);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        if (message.startsWith(cmdPrefix))    {
            Unikko.LOGGER.info("Command " + message + " was used.");
            try {
                CommandHandler.dispatch(message.substring(cmdPrefix.length()));
            } catch (CommandSyntaxException e) {
                ChatInfoUtils.sendFeedback(new TranslatableText("command.notfound"));
                e.printStackTrace();
            }
            ci.cancel();
        }
    }
}
