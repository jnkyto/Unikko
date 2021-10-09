package com.ahenkeshi.unikko.utils;

public class CommonUtils {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
