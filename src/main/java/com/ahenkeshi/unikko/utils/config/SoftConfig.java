package com.ahenkeshi.unikko.utils.config;

import com.ahenkeshi.unikko.Unikko;

import java.util.HashMap;
import java.util.Map;

public class SoftConfig {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, String> configMap;

    public SoftConfigEntry hudRender;
    public SoftConfigEntry discordRpc;
    public SoftConfigEntry rpcAll;
    public SoftConfigEntry cmdPrefix;
    public SoftConfigEntry watermarkX;
    public SoftConfigEntry watermarkY;
    public SoftConfigEntry reldateX;
    public SoftConfigEntry reldateY;
    public SoftConfigEntry yawX;
    public SoftConfigEntry yawY;
    public SoftConfigEntry fpsX;
    public SoftConfigEntry fpsY;
    public SoftConfigEntry tpsX;
    public SoftConfigEntry tpsY;
    public SoftConfigEntry lagX;
    public SoftConfigEntry lagY;
    public SoftConfigEntry lagDuration;

    public SoftConfig(HashMap<String, String> inputMap)    {
        this.configMap = new HashMap<>(inputMap);

        this.hudRender = new SoftConfigEntry("hudRender", Boolean.valueOf(configMap.get("hudRender")));
        this.discordRpc = new SoftConfigEntry("discordRpc", Boolean.valueOf(configMap.get("discordRpc")));
        this.rpcAll = new SoftConfigEntry("rpcAll", Boolean.valueOf(configMap.get("rpcAll")));
        this.cmdPrefix = new SoftConfigEntry("cmdPrefix", configMap.get("cmdPrefix"));
        this.watermarkX = new SoftConfigEntry("watermarkX", Integer.valueOf(configMap.get("watermarkX")));
        this.watermarkY = new SoftConfigEntry("watermarkY", Integer.valueOf(configMap.get("watermarkY")));
        this.reldateX = new SoftConfigEntry("reldateX", Integer.valueOf(configMap.get("reldateX")));
        this.reldateY = new SoftConfigEntry("reldateY", Integer.valueOf(configMap.get("reldateY")));
        this.yawX = new SoftConfigEntry("yawX", Integer.valueOf(configMap.get("yawX")));
        this.yawY = new SoftConfigEntry("yawY", Integer.valueOf(configMap.get("yawY")));
        this.fpsX = new SoftConfigEntry("fpsX", Integer.valueOf(configMap.get("fpsX")));
        this.fpsY = new SoftConfigEntry("fpsY", Integer.valueOf(configMap.get("fpsY")));
        this.tpsX = new SoftConfigEntry("tpsX", Integer.valueOf(configMap.get("tpsX")));
        this.tpsY = new SoftConfigEntry("tpsY", Integer.valueOf(configMap.get("tpsY")));
        this.lagX = new SoftConfigEntry("lagX", Integer.valueOf(configMap.get("lagX")));
        this.lagY = new SoftConfigEntry("lagY", Integer.valueOf(configMap.get("lagY")));
        this.lagDuration = new SoftConfigEntry("lagDuration", Integer.valueOf(configMap.get("lagDuration")));
    }

    public Map<String, String> getSoftConfigMap()   {
        return this.configMap;
    }

    public void pushHard()    {
        long saveStart = System.currentTimeMillis();

        for(Map.Entry<String, String> entry : configMap.entrySet())   {
            HardConfigUtils.putInFile(entry.getKey(), String.valueOf(entry.getValue()));
        }

        long saveFinish = System.currentTimeMillis() - saveStart;

        Unikko.logger.info("Configfile updated in " + saveFinish + "ms.");
    }

    public static class SoftConfigEntry {
        private final String key;
        private Object value;

        public SoftConfigEntry(String key, Object value)    {
            this.key = key;
            this.value = value;
        }

        public Object value()   {
            return this.value;
        }

        public void set(Object val) {
            this.value = val;
            Unikko.softConfig.getSoftConfigMap().put(this.key, String.valueOf(this.value));
        }

        public String getKey()  {
            return this.key;
        }
    }
}
