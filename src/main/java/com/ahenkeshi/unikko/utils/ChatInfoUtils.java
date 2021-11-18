/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatInfoUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void sendFeedback(Text message)   {
        mc.inGameHud.getChatHud().addMessage(message);
    }

    public static void sendError(Text error) {
        sendFeedback(new LiteralText("").append(error).formatted(Formatting.RED));
        if(!(mc.world == null) && !(mc.player == null))   {
            mc.world.playSound(
                    mc.player,
                    mc.player.getBlockPos(),
                    SoundEvents.BLOCK_ANVIL_LAND,
                    SoundCategory.BLOCKS,
                    1f,
                    1f
            );
        }
    }
}
