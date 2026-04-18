/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.config;

import com.z7.lib.config.Z7Config;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Z7ConfigManager {
    private static final Map<String, Z7Config> configs = new HashMap<String, Z7Config>();
    private static Path configDir;

    public static void init() {
    }

    public static void setConfigDir(Path path) {
        configDir = path;
        for (Z7Config config : configs.values()) {
            config.load(configDir);
        }
    }

    public static void registerConfig(Z7Config config) {
        configs.put(config.modId, config);
        if (configDir != null) {
            config.load(configDir);
        }
    }

    public static <T extends Z7Config> T getConfig(String modId) {
        return (T)configs.get(modId);
    }

    public static Path getConfigDir() {
        return configDir;
    }
}

