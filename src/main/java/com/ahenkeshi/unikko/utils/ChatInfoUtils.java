package com.ahenkeshi.unikko.utils;

import net.minecraft.client.MinecraftClient;
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
    }
}
