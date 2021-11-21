/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

import java.awt.*;

public class CommonUtils {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /* This is probably from LiquidBounce */
    public static int rainbow(int delay)    {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
    public static String facing(String yaw) {
        return switch (yaw) {
            case "north" -> "[-Z]";
            case "east" -> "[+X]";
            case "south" -> "[+Z]";
            case "west" -> "[-X]";
            default -> "switch/case error";
        };
    }
}
