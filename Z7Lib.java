/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.fml.common.Mod
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.z7.lib;

import com.z7.lib.config.Z7ConfigManager;
import com.z7.lib.format.JVazLoader;
import com.z7.lib.loader.Z7ModLoader;
import com.z7.lib.network.Z7Network;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(value="z7lib")
public class Z7Lib {
    public static final String MOD_ID = "z7lib";
    public static final String MOD_NAME = "Z7 Library";
    public static final String VERSION = "1.1.0";
    public static final String LOADER_VERSION = "Z7Loader-1.1";
    public static final String LANG_VERSION = "Z7Lang-1.0";
    public static final String RESOURCE_PACK_VERSION = "1.1.0";
    private static Logger LOGGER;
    private static boolean initialized;

    public Z7Lib() {
        Z7Lib.init();
    }

    public static void init() {
        if (initialized) {
            return;
        }
        LOGGER = LoggerFactory.getLogger((String)MOD_ID);
        LOGGER.info("===========================================");
        LOGGER.info("  Z7Lib v{} initializing...", (Object)"1.1.0");
        LOGGER.info("  Loader: {}", (Object)LOADER_VERSION);
        LOGGER.info("  Z7Lang: {}", (Object)LANG_VERSION);
        LOGGER.info("  Resource Pack: {}", (Object)"1.1.0");
        LOGGER.info("  Made with Z7Lib Framework");
        LOGGER.info("===========================================");
        Z7ModLoader.init();
        JVazLoader.init();
        Z7ConfigManager.init();
        Z7Network.init();
        Z7ModLoader.loadAllMods();
        initialized = true;
        LOGGER.info("===========================================");
        LOGGER.info("  Z7Lib initialized successfully!");
        LOGGER.info("  Loaded {} mods", (Object)Z7ModLoader.getLoadedModCount());
        LOGGER.info("  Z7Lang ready for .z7 files!");
        LOGGER.info("===========================================");
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static String getVersion() {
        return "1.1.0";
    }

    public static String getLoaderVersion() {
        return LOADER_VERSION;
    }

    public static String getLangVersion() {
        return LANG_VERSION;
    }

    public static String getResourcePackVersion() {
        return "1.1.0";
    }

    static {
        initialized = false;
    }
}

