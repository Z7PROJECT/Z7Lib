/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.format;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class JVazFile {
    private final File file;
    private final Map<String, String> metadata;
    private final Map<String, byte[]> classes;
    private final Map<String, byte[]> resources;
    private String mainClass;

    public JVazFile(File file) {
        this.file = file;
        this.metadata = new HashMap<String, String>();
        this.classes = new HashMap<String, byte[]>();
        this.resources = new HashMap<String, byte[]>();
    }

    public static void init() {
    }

    public void load() throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(this.file));){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                byte[] data = this.readAllBytes(zis);
                if (entry.getName().startsWith("classes/")) {
                    String className = entry.getName().substring(8).replace("/", ".");
                    if (className.endsWith(".class")) {
                        className = className.substring(0, className.length() - 6);
                    }
                    this.classes.put(className, data);
                } else if (entry.getName().startsWith("assets/")) {
                    this.resources.put(entry.getName(), data);
                } else if (entry.getName().equals("META-INF/manifest.jvzm")) {
                    this.loadMetadata(data);
                }
                zis.closeEntry();
            }
        }
    }

    private void loadMetadata(byte[] data) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts;
                if (!line.contains("=") || (parts = line.split("=", 2)).length != 2) continue;
                this.metadata.put(parts[0].trim(), parts[1].trim());
            }
        }
        this.mainClass = this.metadata.get("mainClass");
    }

    public void save(File outputFile) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile));){
            ZipEntry manifestEntry = new ZipEntry("META-INF/manifest.jvzm");
            zos.putNextEntry(manifestEntry);
            zos.write(this.saveMetadata().getBytes());
            zos.closeEntry();
            for (Map.Entry<String, byte[]> entry : this.classes.entrySet()) {
                String className = entry.getKey().replace(".", "/") + ".class";
                ZipEntry classEntry = new ZipEntry("classes/" + className);
                zos.putNextEntry(classEntry);
                zos.write(entry.getValue());
                zos.closeEntry();
            }
            for (Map.Entry<String, byte[]> entry : this.resources.entrySet()) {
                ZipEntry resourceEntry = new ZipEntry(entry.getKey());
                zos.putNextEntry(resourceEntry);
                zos.write(entry.getValue());
                zos.closeEntry();
            }
        }
    }

    private String saveMetadata() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Z7Lib JVaz Manifest\n");
        sb.append("# Made with Z7Lib Framework\n");
        sb.append("formatVersion=1.0\n");
        for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        sb.append("madeWith=Z7Lib ").append("1.1.0").append("\n");
        sb.append("loader=Z7Loader-1.0\n");
        return sb.toString();
    }

    public byte[] getClassData(String className) {
        return this.classes.get(className);
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public String getMetadata(String key) {
        return this.metadata.get(key);
    }

    public void setMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public void addClass(String className, byte[] classData) {
        this.classes.put(className, classData);
    }

    public void addResource(String path, byte[] data) {
        this.resources.put(path, data);
    }

    private byte[] readAllBytes(InputStream is) throws IOException {
        int n;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        while ((n = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        return buffer.toByteArray();
    }
}

