package com.ahenkeshi.unikko.utils;

import java.util.HashMap;

public class BoolUtils {
    private static final HashMap<String, Boolean> booleans = new HashMap<>();

    public static void update()    {
        Boolean inGameHudShouldRender = Boolean.valueOf(ConfigUtils.getValueWithKey("hudRender"));
        Boolean discordRpc = Boolean.valueOf(ConfigUtils.getValueWithKey("discordRpc"));

        booleans.put("hudRender", inGameHudShouldRender);
        booleans.put("discordRpc", discordRpc);

        System.out.println("Unikko: BoolUtils has read boolean values from configfile");
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
