/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

public class RunningValues {
    private static int yawY;
    private static boolean initialized;

    public static void setYawY(int sh) {
        yawY = sh - 26;
    }

    public static void setInitialized(boolean i)    {
        initialized = i;
    }

    public static int getYawY() {
        return yawY;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
