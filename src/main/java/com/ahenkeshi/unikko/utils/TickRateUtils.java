/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode

***Heavily*** inspired by Meteor Client, which in turn was inspired by Kami Client
*/

package com.ahenkeshi.unikko.utils;

import com.ahenkeshi.unikko.Unikko;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @author Joona Kytöniemi kjoona@outlook.com
 * @since 1.1.0
 *
 * Static utility class for getting the server tickrate.
 */
public class TickRateUtils {
    private static long gameJoined = 0;
    private static long lastTickAt = 0;
    private final static float[] tickrates = new float[20];
    private static int nextIndex = 0;

    private static final DecimalFormat df = new DecimalFormat("0.0");
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void gameJoined(long val)  {
        Arrays.fill(tickrates, 0);
        nextIndex = 0;
        gameJoined = lastTickAt = val;
    }

    public static Object getSinceLastTick(boolean formattedForHud) {
        long now = System.currentTimeMillis();
        if (formattedForHud) {
            if(now - gameJoined > 3000 && now - lastTickAt > Unikko.softConfig.lagDuration.value()
                    && !mc.isInSingleplayer()) {
                String alert = new TranslatableText("utils.tickrate.alert").getString();
                return (alert + ": " + df.format((now - lastTickAt) / 1000f));
            } else return "";
        } else return (now - lastTickAt) / 1000f;
    }

    public static void onReceivePacket() {
        long now = System.currentTimeMillis();
        float elapsed = (float) (now - lastTickAt) / 1000.0F;
        tickrates[nextIndex] = CommonUtils.clamp(20.0f / elapsed, 0.0f, 20.0f);
        nextIndex = (nextIndex + 1) % tickrates.length;
        lastTickAt = now;
    }

    public static String getTickrate()  {
        int tickAmount = 0;
        float tickratesSum = 0.0f;
        long now = System.currentTimeMillis();

        if(now - gameJoined < 4000) return "20,0";
        for(float tickrate : tickrates) {
            if (tickrate > 0)   {
                tickratesSum += tickrate;
                tickAmount++;
            }
        }
        return df.format(tickratesSum / tickAmount);
    }
}
