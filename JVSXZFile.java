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

public class JVSXZFile {
    private final File file;
    private final Map<String, String> metadata;
    private final Map<String, String> sources;
    private final Map<String, byte[]> resources;

    public JVSXZFile(File file) {
        this.file = file;
        this.metadata = new HashMap<String, String>();
        this.sources = new HashMap<String, String>();
        this.resources = new HashMap<String, byte[]>();
    }

    public void load() throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(this.file));){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().startsWith("src/")) {
                    String sourceName = entry.getName().substring(4);
                    this.sources.put(sourceName, this.readAsString(zis));
                } else if (entry.getName().startsWith("resources/")) {
                    this.resources.put(entry.getName(), this.readAllBytes(zis));
                } else if (entry.getName().equals("META-INF/manifest.jvzm")) {
                    this.loadMetadata(this.readAllBytes(zis));
                }
                zis.closeEntry();
            }
        }
    }

    public void save(File outputFile) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile));){
            ZipEntry manifestEntry = new ZipEntry("META-INF/manifest.jvzm");
            zos.putNextEntry(manifestEntry);
            zos.write(this.saveMetadata().getBytes());
            zos.closeEntry();
            for (Map.Entry<String, String> entry : this.sources.entrySet()) {
                ZipEntry sourceEntry = new ZipEntry("src/" + entry.getKey());
                zos.putNextEntry(sourceEntry);
                zos.write(entry.getValue().getBytes());
                zos.closeEntry();
            }
            for (Map.Entry<String, String> entry : this.resources.entrySet()) {
                ZipEntry resourceEntry = new ZipEntry(entry.getKey());
                zos.putNextEntry(resourceEntry);
                zos.write((byte[])entry.getValue());
                zos.closeEntry();
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
    }

    private String saveMetadata() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Z7Lib JVSXZ Source Manifest\n");
        sb.append("# Made with Z7Lib Framework\n");
        sb.append("formatVersion=1.0\n");
        sb.append("madeWith=Z7Lib ").append("1.1.0").append("\n");
        for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public String getMetadata(String key) {
        return this.metadata.get(key);
    }

    public void setMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public void addSource(String path, String content) {
        this.sources.put(path, content);
    }

    public String getSource(String path) {
        return this.sources.get(path);
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

    private String readAsString(InputStream is) throws IOException {
        int n;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        while ((n = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        return buffer.toString("UTF-8");
    }
}

