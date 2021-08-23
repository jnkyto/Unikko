/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("FieldMayBeFinal")
public class SoftConfigUtils {
    private static final Map<String, Boolean> booleans = new HashMap<>();
    private static final Map<String, Integer> values = new HashMap<>();
    private static final Map<String, String> strings = new HashMap<>();

    private static Boolean hudRender = Boolean.valueOf(HardConfigUtils.getValueWithKey("hudRender"));
    private static Boolean discordRpc = Boolean.valueOf(HardConfigUtils.getValueWithKey("discordRpc"));
    private static String cmdPrefix = HardConfigUtils.getValueWithKey("cmdPrefix");
    private static int watermarkX = Integer.parseInt(HardConfigUtils.getValueWithKey("watermarkX"));
    private static int watermarkY = Integer.parseInt(HardConfigUtils.getValueWithKey("watermarkY"));
    private static int reldateX = Integer.parseInt(HardConfigUtils.getValueWithKey("reldateX"));
    private static int reldateY = Integer.parseInt(HardConfigUtils.getValueWithKey("reldateY"));
    private static int yawX = Integer.parseInt(HardConfigUtils.getValueWithKey("yawX"));
    private static int yawY = Integer.parseInt(HardConfigUtils.getValueWithKey("yawY"));
    private static int fpsX = Integer.parseInt(HardConfigUtils.getValueWithKey("fpsX"));
    private static int fpsY = Integer.parseInt(HardConfigUtils.getValueWithKey("fpsY"));

    public static void saveBooleansToConfigFile()    {
        booleans.put("hudRender", hudRender);
        booleans.put("discordRpc", discordRpc);
        strings.put("cmdPrefix", cmdPrefix);
        values.put("watermarkX", watermarkX);
        values.put("watermarkY", watermarkY);
        values.put("reldateX", reldateX);
        values.put("reldateY", reldateY);
        values.put("yawX", yawX);
        values.put("yawY", yawY);
        values.put("fpsX", fpsX);
        values.put("fpsY", fpsY);

        for(Map.Entry<String, Boolean> entry : booleans.entrySet()) {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        for(Map.Entry<String, Integer> entry : values.entrySet())   {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        for(Map.Entry<String, String> entry : strings.entrySet())   {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        System.out.println("Unikko: SoftConfigUtils has saved values to configfile");
    }

    public static void updateBoolean(String setting, String val)   {
        switch (setting) {
            case "hudRender" -> hudRender = Boolean.valueOf(val);
            case "discordRpc" -> discordRpc = Boolean.valueOf(val);
            default -> throw new IllegalStateException("Unikko: Unexpected value in SoftConfigUtils/updateBoolean: " + setting);
        }
    }

    public static int getInt(String key)  {
        try {
            return values.get(key);
        } catch (NullPointerException e)   {
            ChatInfoUtils.sendError(Text.of("Unikko: SoftConfigUtils getInt error! Given key doesn't exist"));
            return 0;
        }
    }

    public static boolean getBoolean(String key)    {
        try {
            return booleans.get(key);
        } catch (NullPointerException e)   {
            ChatInfoUtils.sendError(Text.of("Unikko: SoftConfigUtils getBoolean error! Given key doesn't exist"));
            return false;
        }
    }

    public static String getString(String key)  {
        try {
            return strings.get(key);
        } catch (NullPointerException e) {
            ChatInfoUtils.sendError(Text.of("Unikko: SoftConfigUtils getString error! Given key doesn't exist"));
            return null;
        }
    }
}
