/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.loader;

import com.z7.lib.format.JVazFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Z7ModLoader {
    private static final Logger LOGGER = Logger.getLogger("Z7ModLoader");
    private static final List<LoadedMod> loadedMods = new ArrayList<LoadedMod>();
    private static File modsDir;

    public static void init() {
        modsDir = new File("mods/z7lib");
        if (!modsDir.exists()) {
            modsDir.mkdirs();
            LOGGER.info("Created Z7Lib mods directory: " + modsDir.getAbsolutePath());
        }
    }

    public static void loadAllMods() {
        LOGGER.info("Scanning for .jvaz mods in " + modsDir.getAbsolutePath());
        File[] jvazFiles = modsDir.listFiles((dir, name) -> name.endsWith(".jvaz"));
        if (jvazFiles == null || jvazFiles.length == 0) {
            LOGGER.info("No .jvaz mods found");
            return;
        }
        for (File jvazFile : jvazFiles) {
            try {
                Z7ModLoader.loadMod(jvazFile);
            }
            catch (Exception e) {
                LOGGER.severe("Failed to load mod: " + jvazFile.getName());
                e.printStackTrace();
            }
        }
    }

    private static void loadMod(File jvazFile) throws IOException {
        LOGGER.info("Loading mod: " + jvazFile.getName());
        JVazFile jvaz = new JVazFile(jvazFile);
        jvaz.load();
        String modId = jvaz.getMetadata("modId");
        String modVersion = jvaz.getMetadata("version");
        String madeWith = jvaz.getMetadata("madeWith");
        if (modId == null) {
            LOGGER.severe("Mod " + jvazFile.getName() + " has no modId!");
            return;
        }
        LOGGER.info("  Mod ID: " + modId);
        LOGGER.info("  Version: " + modVersion);
        LOGGER.info("  Made with: " + (madeWith != null ? madeWith : "Unknown"));
        byte[] mainClassBytes = jvaz.getClassData(jvaz.getMainClass());
        if (mainClassBytes != null) {
            LOGGER.info("  Main class: " + jvaz.getMainClass());
        }
        LoadedMod loadedMod = new LoadedMod(modId, modVersion, jvazFile, jvaz);
        loadedMods.add(loadedMod);
        LOGGER.info("  Loaded successfully!");
    }

    public static int getLoadedModCount() {
        return loadedMods.size();
    }

    public static List<LoadedMod> getLoadedMods() {
        return loadedMods;
    }

    public static LoadedMod getModById(String modId) {
        for (LoadedMod mod : loadedMods) {
            if (!mod.getModId().equals(modId)) continue;
            return mod;
        }
        return null;
    }

    public static class LoadedMod {
        private final String modId;
        private final String version;
        private final File file;
        private final JVazFile jvazFile;

        public LoadedMod(String modId, String version, File file, JVazFile jvazFile) {
            this.modId = modId;
            this.version = version;
            this.file = file;
            this.jvazFile = jvazFile;
        }

        public String getModId() {
            return this.modId;
        }

        public String getVersion() {
            return this.version;
        }

        public File getFile() {
            return this.file;
        }

        public JVazFile getJVazFile() {
            return this.jvazFile;
        }

        public String getMadeWith() {
            return this.jvazFile.getMetadata("madeWith");
        }
    }
}

