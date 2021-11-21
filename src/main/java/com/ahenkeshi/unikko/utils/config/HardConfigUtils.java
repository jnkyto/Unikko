/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils.config;

import com.ahenkeshi.unikko.Unikko;
import com.ahenkeshi.unikko.exceptions.ConfigVersionMismatchException;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Joona Kytöniemi kjoona@outlook.com
 * @since 1.0.0
 *
 * File operation utilities for the configuration file.
 */
@SuppressWarnings("unchecked")
public class HardConfigUtils {
    private static final File configFolder = new File(String.valueOf(FabricLoader.getInstance().getConfigDir()), (Unikko.MODID));
    private static JSONObject configJson;

    private static final String jsonName = (configFolder.getPath() + "/" + Unikko.MODID + "_" + Unikko.VERSION + ".json");


    /**
     * Creates a configuration file if it doesn't exist. Otherwise, runs readFile() to parse existing file.
     * Also creates a config hashmap to pass on to SoftConfig.
     *
     * @throws IOException Normal exception that can occur from file operation issues.
     */
    public static void createFile() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        configFolder.mkdirs();
        if(!Files.exists(Paths.get(jsonName)))    {
            configJson = new JSONObject();
            createDefaultEntries();
            Files.writeString(Paths.get(jsonName), configJson.toJSONString());
            Unikko.logger.info("Created new configfile " + jsonName);
        } else  {
            try {
                configJson = (JSONObject) readFile(jsonName);
                if (!getValueWithKey(Unikko.MODID).equals(Unikko.VERSION))  {
                    throw new ConfigVersionMismatchException("Version set in configfile doesn't match mod version!");
                    // Check config file for version mismatch. This should only occur if user has erroneously edited
                    // config manually.
                }
            } catch (ParseException | ConfigVersionMismatchException pe) {
                CrashReport report = new CrashReport("Unikko was creating its configfile.", pe);
                pe.printStackTrace();
                MinecraftClient.printCrashReport(report);
            }
            Unikko.logger.info("Configfile already exists, creation skipped");
        }
    }

    /**
     * Reads and parses an existing configuration file.
     *
     * @param filename The path to the configuration file.
     * @return Return parsed file that will be cast to a JSONObject.
     * @throws IOException Normal exception that can occur from file operation issues.
     * @throws ParseException Exception that occurs when config file is unlike what was expected.
     */
    public static Object readFile(String filename) throws IOException, ParseException {
        FileReader reader = new FileReader(filename);
        JSONParser parser = new JSONParser();
        return parser.parse(reader);
    }

    /**
     * Writes the provided setting+value to the configuration file.
     *
     * @param setting The key to be created/changed.
     * @param value The value of the key to be changed/created.
     */
    public static void putInFile(String setting, String value) {
        if(configJson.containsKey(setting)) {configJson.replace(setting, value);}
        else  {configJson.put(setting, value);}
        try {
            Files.writeString(Paths.get(jsonName), configJson.toJSONString());
        } catch (IOException e) {
            CrashReport report = new CrashReport("Unikko was reading existing configfile.", e);
            e.printStackTrace();
            MinecraftClient.printCrashReport(report);
        }
    }

    /**
     * Fetches the value of a key (setting) from the configuration file.
     *
     * @param key The key where the value will be fetched.
     * @return The value of the provided key. If the key doesn't exist, return null.
     */
    public static String getValueWithKey(String key)    {
        if(configJson.containsKey(key)) {
            return (String) configJson.get(key);
        } else  {return "false";}
    }

    /**
     * Creates the default configuration file entries.
     */
    public static void createDefaultEntries()   {   // default entries to be added to configfile upon creation
        configJson.put(Unikko.MODID, Unikko.VERSION);
        configJson.put("hudRender", "true");
        configJson.put("discordRpc", "true");
        configJson.put("rpcAll", "true");
        configJson.put("cmdPrefix", ";");
        configJson.put("watermarkX", "10");
        configJson.put("watermarkY", "10");
        configJson.put("reldateX", "10");
        configJson.put("reldateY", "20");
        configJson.put("yawX", "10");
        configJson.put("yawY", "188");
        configJson.put("fpsX", "110");
        configJson.put("fpsY", "10");
        configJson.put("tpsX", "170");
        configJson.put("tpsY", "10");
        configJson.put("lagX", "170");
        configJson.put("lagY", "20");
        configJson.put("lagDuration", "2000");
        Unikko.logger.info("Default config entries created.");
    }

    /**
     * Getter method for the configuration JSON.
     *
     * @return A copy of the configuration JSON-object, cast to a Map-object
     */
    public static Map<String, String> getConfigMap() {
        return ((Map<String, String>) configJson.clone());
    }
}

