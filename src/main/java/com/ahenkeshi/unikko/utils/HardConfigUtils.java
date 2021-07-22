/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils;

import com.ahenkeshi.unikko.Unikko;
import net.fabricmc.loader.api.FabricLoader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unchecked")
public class HardConfigUtils {
    public static File configFolder = new File(String.valueOf(FabricLoader.getInstance().getConfigDir()), (Unikko.MODID));
    public static JSONObject configJson;
    public static String jsonName = (configFolder.getPath() + "/" + Unikko.MODID + "_" + Unikko.VERSION + ".json");

    public static void createFile() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        configFolder.mkdirs();
        if(!Files.exists(Paths.get(jsonName)))    {
            configJson = new JSONObject();
            createDefaultEntries();
            Files.write(Paths.get(jsonName), configJson.toJSONString().getBytes(StandardCharsets.UTF_8));
            System.out.println("Unikko: Created new configfile " + jsonName);
        } else  {
            try {
                configJson = (JSONObject) readFile(jsonName);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            System.out.println("Unikko: Configfile already exists, creation skipped");
        }
    }

    public static Object readFile(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser parser = new JSONParser();
        return parser.parse(reader);
    }

    public static void putInFile(String setting, String value) {
        if(configJson.containsKey(setting)) {configJson.replace(setting, value);}
        else  {configJson.put(setting, value);}
        System.out.println(Unikko.MODID + " " + setting + " was changed in configfile");
        try {
            Files.write(Paths.get(jsonName), configJson.toJSONString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {e.printStackTrace();}
    }

    public static String getValueWithKey(String key)    {
        if(configJson.containsKey(key)) {
            return (String) configJson.get(key);
        } else  {return "false";}
    }

    public static void createDefaultEntries()   {   // default entries to be added to configfile upon creation
        configJson.put(Unikko.MODID, Unikko.VERSION);
        configJson.put("hudRender", "true");
        configJson.put("discordRpc", "true");
        configJson.put("watermarkX", "10");
        configJson.put("watermarkY", "10");
        configJson.put("reldateX", "10");
        configJson.put("reldateY", "20");
        configJson.put("yawX", "10");
        configJson.put("yawY", "0");
        configJson.put("fpsX", "110");
        configJson.put("fpsY", "10");
    }
}
