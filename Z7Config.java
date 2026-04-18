/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package com.z7.lib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

public abstract class Z7Config {
    protected final String modId;
    protected final String fileName;
    protected Path configPath;
    protected JsonObject data;
    protected final Gson gson;

    public Z7Config(String modId, String fileName) {
        this.modId = modId;
        this.fileName = fileName;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.data = new JsonObject();
    }

    public void load(Path configDir) {
        this.configPath = configDir.resolve(this.modId).resolve(this.fileName + ".json");
        File configFile = this.configPath.toFile();
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile);){
                this.data = JsonParser.parseReader((Reader)reader).getAsJsonObject();
            }
            catch (IOException e) {
                e.printStackTrace();
                this.data = new JsonObject();
            }
        }
        this.setDefaults();
        this.save();
    }

    protected abstract void setDefaults();

    public void save() {
        if (this.configPath != null) {
            try (FileWriter writer = new FileWriter(this.configPath.toFile());){
                this.gson.toJson((JsonElement)this.data, (Appendable)writer);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected int getInt(String key, int defaultValue) {
        if (this.data.has(key)) {
            return this.data.get(key).getAsInt();
        }
        this.data.addProperty(key, (Number)defaultValue);
        return defaultValue;
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        if (this.data.has(key)) {
            return this.data.get(key).getAsBoolean();
        }
        this.data.addProperty(key, Boolean.valueOf(defaultValue));
        return defaultValue;
    }

    protected String getString(String key, String defaultValue) {
        if (this.data.has(key)) {
            return this.data.get(key).getAsString();
        }
        this.data.addProperty(key, defaultValue);
        return defaultValue;
    }

    protected void set(String key, Object value) {
        if (value instanceof Integer) {
            this.data.addProperty(key, (Number)((Integer)value));
        } else if (value instanceof Boolean) {
            this.data.addProperty(key, (Boolean)value);
        } else if (value instanceof String) {
            this.data.addProperty(key, (String)value);
        }
        this.save();
    }
}

