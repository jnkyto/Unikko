package com.ahenkeshi.unikko.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

import java.text.DecimalFormat;

public class TickRateUtils {
    private static long gameJoined = 0;
    private static long lastTickAt = 0;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void setGameJoined(long val)  {
        gameJoined = val;
    }

    public static void setLastTickAt(long val)   {
        lastTickAt = val;
    }

    public static Object getSinceLastTick(boolean formattedForHud) {
        final DecimalFormat df = new DecimalFormat("0.0");
        long now = System.currentTimeMillis();
        if (formattedForHud) {
            if(now - gameJoined > 3000 && now - lastTickAt > 1000 && !mc.isInSingleplayer()) {
                String alert = new TranslatableText("utils.tickrate.alert").getString();
                return (alert + ": " + df.format((now - lastTickAt) / 1000f));
            } else return "";
        } else return (now - lastTickAt) / 1000f;
    }
}
