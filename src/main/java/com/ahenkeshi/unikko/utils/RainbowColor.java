package com.ahenkeshi.unikko.utils;

import java.awt.*;

public class RainbowColor {
    public static int gen(int delay)    {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}
