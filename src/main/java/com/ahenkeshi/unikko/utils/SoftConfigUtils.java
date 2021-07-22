/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("FieldMayBeFinal")
public class SoftConfigUtils {
    private static final HashMap<String, Boolean> booleans = new HashMap<>();
    private static final HashMap<String, Integer> values = new HashMap<>();
    private static Boolean hudRender = Boolean.valueOf(HardConfigUtils.getValueWithKey("hudRender"));
    private static Boolean discordRpc = Boolean.valueOf(HardConfigUtils.getValueWithKey("discordRpc"));
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

        System.out.println("Unikko: SoftConfigUtils has saved values to configfile");
    }

    public static void updateBoolean(String setting, String bool)   {
        switch (setting)    {
            case "hudRender":
                hudRender = Boolean.valueOf(bool);
                break;
            case "discordRpc":
                discordRpc = Boolean.valueOf(bool);
                break;
            default:
                throw new IllegalStateException("Unikko: Unexpected value in BoolUtils/updateBoolean: " + setting);
        }
    }

    public static Object get(String key)  {
        if(booleans.containsKey(key)) {
            // System.out.println("Unikko: SoftConfigUtils - Got " + key + " with value " + booleans.get(key));
            return booleans.get(key);
        } else if(values.containsKey(key)) {
            return values.get(key);
        }
        else {
            System.out.println("Unikko: SoftConfigUtils.java get()-error: Given key doesn't exist");
            return false;
        }
    }
}
