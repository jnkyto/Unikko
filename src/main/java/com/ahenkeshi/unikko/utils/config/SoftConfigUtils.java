/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils.config;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.utils.ChatInfoUtils;
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
    private static Boolean rpcAll = Boolean.valueOf(HardConfigUtils.getValueWithKey("rpcAll"));
    private static String cmdPrefix = HardConfigUtils.getValueWithKey("cmdPrefix");
    private static int watermarkX = Integer.parseInt(HardConfigUtils.getValueWithKey("watermarkX"));
    private static int watermarkY = Integer.parseInt(HardConfigUtils.getValueWithKey("watermarkY"));
    private static int reldateX = Integer.parseInt(HardConfigUtils.getValueWithKey("reldateX"));
    private static int reldateY = Integer.parseInt(HardConfigUtils.getValueWithKey("reldateY"));
    private static int yawX = Integer.parseInt(HardConfigUtils.getValueWithKey("yawX"));
    private static int yawY = Integer.parseInt(HardConfigUtils.getValueWithKey("yawY"));
    private static int fpsX = Integer.parseInt(HardConfigUtils.getValueWithKey("fpsX"));
    private static int fpsY = Integer.parseInt(HardConfigUtils.getValueWithKey("fpsY"));
    private static int tpsX = Integer.parseInt(HardConfigUtils.getValueWithKey("tpsX"));
    private static int tpsY = Integer.parseInt(HardConfigUtils.getValueWithKey("tpsY"));
    private static int lagX = Integer.parseInt(HardConfigUtils.getValueWithKey("lagX"));
    private static int lagY = Integer.parseInt(HardConfigUtils.getValueWithKey("lagY"));
    private static int lagDuration = Integer.parseInt(HardConfigUtils.getValueWithKey("lagDuration"));

    public static void saveBooleansToConfigFile()    {
        long saveStart = System.currentTimeMillis();
        booleans.put("hudRender", hudRender);
        booleans.put("discordRpc", discordRpc);
        booleans.put("rpcAll", rpcAll);
        strings.put("cmdPrefix", cmdPrefix);
        values.put("watermarkX", watermarkX);
        values.put("watermarkY", watermarkY);
        values.put("reldateX", reldateX);
        values.put("reldateY", reldateY);
        values.put("yawX", yawX);
        values.put("yawY", yawY);
        values.put("fpsX", fpsX);
        values.put("fpsY", fpsY);
        values.put("tpsX", tpsX);
        values.put("tpsY", tpsY);
        values.put("lagX", lagX);
        values.put("lagY", lagY);
        values.put("lagDuration", lagDuration);


        for(Map.Entry<String, Boolean> entry : booleans.entrySet()) {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        for(Map.Entry<String, Integer> entry : values.entrySet())   {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        for(Map.Entry<String, String> entry : strings.entrySet())   {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }
        long saveFinish = System.currentTimeMillis() - saveStart;
        Unikko.logger.info("Configfile updated in " + saveFinish + "ms.");
    }

    public static void updateBoolean(String key, String val)   {
        switch (key) {
            case "hudRender" -> hudRender = Boolean.valueOf(val);
            case "discordRpc" -> discordRpc = Boolean.valueOf(val);
            case "rpcAll" -> rpcAll = Boolean.valueOf(val);
            default -> ChatInfoUtils.sendError(Text.of("Unikko: Unexpected value in SoftConfigUtils updateBoolean: " + key));
        }
    }

    public static int getInt(String key)  {
        try {
            return values.get(key);
        } catch (NullPointerException e)   {
            ChatInfoUtils.sendError(Text.of("Unikko: SoftConfigUtils getInt error! Given key doesn't exist:" + key));
            return 0;
        }
    }

    public static boolean getBoolean(String key)    {
        try {
            return booleans.get(key);
        } catch (NullPointerException e)   {
            ChatInfoUtils.sendError(Text.of("Unikko: SoftConfigUtils getBoolean error! Given key doesn't exist:" + key));
            return false;
        }
    }

    public static String getString(String key)  {
        try {
            return strings.get(key);
        } catch (NullPointerException e) {
            ChatInfoUtils.sendError(Text.of("Unikko: SoftConfigUtils getString error! Given key doesn't exist:" + key));
            return "error";
        }
    }
}
