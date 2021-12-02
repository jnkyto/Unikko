/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.utils.config;

import com.ahenkeshi.unikko.Unikko;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joona Kytöniemi kjoona@outlook.com
 * @since 1.1.0
 *
 * Handling of Unikko's configuration values in the memory.
 */
public class SoftConfig {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, String> configMap;

    /**
     * Defines every config entry used by Unikko to be loaded into memory.
     * I felt that public variables were the best option.
     */
    public SoftConfigEntry<Boolean> hudRender;
    public SoftConfigEntry<Boolean> discordRpc;
    public SoftConfigEntry<Boolean> rpcAll;
    public SoftConfigEntry<String> cmdPrefix;
    public SoftConfigEntry<Integer> watermarkX;
    public SoftConfigEntry<Integer> watermarkY;
    public SoftConfigEntry<Integer> reldateX;
    public SoftConfigEntry<Integer> reldateY;
    public SoftConfigEntry<Integer> yawX;
    public SoftConfigEntry<Integer> yawY;
    public SoftConfigEntry<Integer> fpsX;
    public SoftConfigEntry<Integer> fpsY;
    public SoftConfigEntry<Integer> tpsX;
    public SoftConfigEntry<Integer> tpsY;
    public SoftConfigEntry<Integer> lagX;
    public SoftConfigEntry<Integer> lagY;
    public SoftConfigEntry<Integer> lagDuration;

    /**
     * Creates a new SoftConfig object, and creates SoftConfigEntries from every <key,value>
     * contained in the inputMap.
     *
     * @param inputMap A configuration map, ideally fetched from HardConfigUtils.
     */
    public SoftConfig(Map<String, String> inputMap)    {
        this.configMap = new HashMap<>(inputMap);

        this.hudRender = new SoftConfigEntry<>("hudRender", Boolean.valueOf(configMap.get("hudRender")));
        this.discordRpc = new SoftConfigEntry<>("discordRpc", Boolean.valueOf(configMap.get("discordRpc")));
        this.rpcAll = new SoftConfigEntry<>("rpcAll", Boolean.valueOf(configMap.get("rpcAll")));
        this.cmdPrefix = new SoftConfigEntry<>("cmdPrefix", configMap.get("cmdPrefix"));
        this.watermarkX = new SoftConfigEntry<>("watermarkX", Integer.valueOf(configMap.get("watermarkX")));
        this.watermarkY = new SoftConfigEntry<>("watermarkY", Integer.valueOf(configMap.get("watermarkY")));
        this.reldateX = new SoftConfigEntry<>("reldateX", Integer.valueOf(configMap.get("reldateX")));
        this.reldateY = new SoftConfigEntry<>("reldateY", Integer.valueOf(configMap.get("reldateY")));
        this.yawX = new SoftConfigEntry<>("yawX", Integer.valueOf(configMap.get("yawX")));
        this.yawY = new SoftConfigEntry<>("yawY", Integer.valueOf(configMap.get("yawY")));
        this.fpsX = new SoftConfigEntry<>("fpsX", Integer.valueOf(configMap.get("fpsX")));
        this.fpsY = new SoftConfigEntry<>("fpsY", Integer.valueOf(configMap.get("fpsY")));
        this.tpsX = new SoftConfigEntry<>("tpsX", Integer.valueOf(configMap.get("tpsX")));
        this.tpsY = new SoftConfigEntry<>("tpsY", Integer.valueOf(configMap.get("tpsY")));
        this.lagX = new SoftConfigEntry<>("lagX", Integer.valueOf(configMap.get("lagX")));
        this.lagY = new SoftConfigEntry<>("lagY", Integer.valueOf(configMap.get("lagY")));
        this.lagDuration = new SoftConfigEntry<>("lagDuration", Integer.valueOf(configMap.get("lagDuration")));
    }

    public Map<String, String> getSoftConfigMap()   {
        return this.configMap;
    }

    /**
     * Pushes every <key,value> in the configMap to HardConfigUtils, to be written in the
     * configuration file.
     */
    public void pushHard()    {
        long saveStart = System.currentTimeMillis();

        for(Map.Entry<String, String> entry : configMap.entrySet())   {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        long saveFinish = System.currentTimeMillis() - saveStart;

        Unikko.logger.info("Configfile updated in " + saveFinish + "ms.");
    }

    /**
     * @author Joona Kytöniemi kjoona@outlook.com
     * @since 1.1.0
     *
     * Basically just a String,Object tuple to be used as a configuration entry.
     */
    public static class SoftConfigEntry<T> {
        private final String key;
        private T value;

        public SoftConfigEntry(String key, T value)    {
            this.key = key;
            this.value = value;
        }

        public T value()   {
            return this.value;
        }

        public void set(T val) {
            this.value = val;
            Unikko.softConfig.getSoftConfigMap().put(this.key, String.valueOf(this.value));
        }

        public String key()  {
            return this.key;
        }
    }
}
