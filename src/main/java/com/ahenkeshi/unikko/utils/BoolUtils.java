package com.ahenkeshi.unikko.utils;

import java.util.HashMap;
import java.util.Map;

public class BoolUtils {
    private static final HashMap<String, Boolean> booleans = new HashMap<>();
    private static Boolean hudRender = Boolean.valueOf(ConfigUtils.getValueWithKey("hudRender"));
    private static Boolean discordRpc = Boolean.valueOf(ConfigUtils.getValueWithKey("discordRpc"));

    public static void saveBooleansToConfigFile()    {
        booleans.put("hudRender", hudRender);
        booleans.put("discordRpc", discordRpc);

        for(Map.Entry<String, Boolean> entry : booleans.entrySet()) {
            ConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        System.out.println("Unikko: BoolUtils has read boolean values from configfile");
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

    public static boolean get(String key)  {
        if(booleans.containsKey(key)) {
            // System.out.println("Unikko: BoolUtils - Got " + key + " with value " + booleans.get(key));
            return booleans.get(key);
        } else {
            System.out.println("Unikko: BoolUtils.java get()-error: Given key doesn't exist");
            return false;
        }
    }
}
