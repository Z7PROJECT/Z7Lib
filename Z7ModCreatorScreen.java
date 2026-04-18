/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$Builder
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package com.z7.lib.client.gui;

import java.io.File;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class Z7ModCreatorScreen
extends Screen {
    private EditBox modNameEdit;
    private EditBox authorEdit;
    private EditBox versionEdit;
    private EditBox descriptionEdit;
    private Button createButton;
    private Button cancelButton;
    private String statusMessage = "";
    private boolean isSuccess = false;

    public Z7ModCreatorScreen() {
        super((Component)Component.m_237113_((String)"\u00a7d\u00a7lZ7Lib - \u0421\u043e\u0437\u0434\u0430\u043d\u0438\u0435 \u043c\u043e\u0434\u0430"));
    }

    protected void m_7856_() {
        super.m_7856_();
        int centerX = this.f_96543_ / 2;
        int centerY = this.f_96544_ / 2;
        this.m_142416_((GuiEventListener)new Button.Builder((Component)Component.m_237113_((String)"\u00a7d\u00a7l=== Z7Lib Mod Creator ==="), button -> {}).m_252987_(centerX - 100, centerY - 80, 200, 20).m_253136_());
        this.m_142416_((GuiEventListener)new Button.Builder((Component)Component.m_237113_((String)"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043c\u043e\u0434\u0430:"), button -> {}).m_252987_(centerX - 100, centerY - 50, 100, 20).m_253136_());
        this.modNameEdit = new EditBox(this.f_96547_, centerX + 10, centerY - 50, 180, 20, (Component)Component.m_237113_((String)""));
        this.modNameEdit.m_94199_(32);
        this.modNameEdit.m_94144_("MyMod");
        this.modNameEdit.m_94151_(text -> {
            this.statusMessage = "";
        });
        this.m_142416_((GuiEventListener)this.modNameEdit);
        this.m_142416_((GuiEventListener)new Button.Builder((Component)Component.m_237113_((String)"\u0410\u0432\u0442\u043e\u0440:"), button -> {}).m_252987_(centerX - 100, centerY - 25, 100, 20).m_253136_());
        this.authorEdit = new EditBox(this.f_96547_, centerX + 10, centerY - 25, 180, 20, (Component)Component.m_237113_((String)""));
        this.authorEdit.m_94199_(64);
        this.authorEdit.m_94144_("Player");
        this.m_142416_((GuiEventListener)this.authorEdit);
        this.m_142416_((GuiEventListener)new Button.Builder((Component)Component.m_237113_((String)"\u0412\u0435\u0440\u0441\u0438\u044f:"), button -> {}).m_252987_(centerX - 100, centerY, 100, 20).m_253136_());
        this.versionEdit = new EditBox(this.f_96547_, centerX + 10, centerY, 180, 20, (Component)Component.m_237113_((String)""));
        this.versionEdit.m_94199_(16);
        this.versionEdit.m_94144_("1.0.0");
        this.m_142416_((GuiEventListener)this.versionEdit);
        this.m_142416_((GuiEventListener)new Button.Builder((Component)Component.m_237113_((String)"\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435:"), button -> {}).m_252987_(centerX - 100, centerY + 25, 100, 20).m_253136_());
        this.descriptionEdit = new EditBox(this.f_96547_, centerX + 10, centerY + 25, 180, 20, (Component)Component.m_237113_((String)""));
        this.descriptionEdit.m_94199_(128);
        this.descriptionEdit.m_94144_("My awesome mod!");
        this.m_142416_((GuiEventListener)this.descriptionEdit);
        this.createButton = Button.m_253074_((Component)Component.m_237113_((String)"\u00a7a\u00a7l[ \u0421\u043e\u0437\u0434\u0430\u0442\u044c \u043c\u043e\u0434 ]"), button -> this.createMod()).m_252987_(centerX - 100, centerY + 60, 200, 20).m_253136_();
        this.m_142416_((GuiEventListener)this.createButton);
        this.cancelButton = Button.m_253074_((Component)Component.m_237113_((String)"\u00a7c[ \u041e\u0442\u043c\u0435\u043d\u0430 ]"), button -> this.m_7379_()).m_252987_(centerX - 100, centerY + 85, 200, 20).m_253136_();
        this.m_142416_((GuiEventListener)this.cancelButton);
        this.m_7522_((GuiEventListener)this.modNameEdit);
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(graphics);
        super.m_88315_(graphics, mouseX, mouseY, partialTick);
        int centerX = this.f_96543_ / 2;
        int centerY = this.f_96544_ / 2;
        if (!this.statusMessage.isEmpty()) {
            graphics.m_280056_(this.f_96547_, this.statusMessage, centerX - this.f_96547_.m_92895_(this.statusMessage) / 2, centerY + 110, this.isSuccess ? 65280 : 0xFF0000, true);
        }
        graphics.m_280056_(this.f_96547_, "\u00a77\u041c\u043e\u0434 \u0431\u0443\u0434\u0435\u0442 \u0441\u043e\u0437\u0434\u0430\u043d \u0432: mods/z7lib/projects/<modid>/", centerX - 100, centerY + 130, 0x888888, true);
    }

    private void createMod() {
        String modName = this.modNameEdit.m_94155_().trim();
        String author = this.authorEdit.m_94155_().trim();
        String version = this.versionEdit.m_94155_().trim();
        String description = this.descriptionEdit.m_94155_().trim();
        if (modName.isEmpty()) {
            this.statusMessage = "\u00a7c\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043c\u043e\u0434\u0430!";
            this.isSuccess = false;
            return;
        }
        if (author.isEmpty()) {
            this.statusMessage = "\u00a7c\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0438\u043c\u044f \u0430\u0432\u0442\u043e\u0440\u0430!";
            this.isSuccess = false;
            return;
        }
        try {
            String modId = modName.toLowerCase().replaceAll("[^a-z0-9]", "");
            String baseDir = "mods/z7lib/projects/" + modId;
            new File(baseDir + "/src/main/java/com/" + modId + "/main").mkdirs();
            new File(baseDir + "/src/main/resources/META-INF").mkdirs();
            new File(baseDir + "/src/main/resources/assets/" + modId).mkdirs();
            String buildGradle = "plugins {\n    id 'eclipse'\n    id 'idea'\n    id 'maven-publish'\n    id 'net.minecraftforge.gradle' version '[6.0,6.2)'\n}\n\nversion = '" + version + "'\ngroup = 'com." + modId + ".main'\n\nbase {\n    archivesName = '" + modName + "-Forge-1.20.1'\n}\n\njava {\n    toolchain {\n        languageVersion = JavaLanguageVersion.of(17)\n    }\n}\n\nminecraft {\n    mappings channel: 'official', version: '1.20.1'\n}\n\ndependencies {\n    minecraft 'net.minecraftforge:forge:1.20.1-47.3.0'\n}\n";
            this.writeFile(baseDir + "/build.gradle", buildGradle);
            String modsToml = "modLoader=\"javafml\"\nloaderVersion=\"[47,)\"\nlicense=\"MIT\"\n\n[[mods]]\nmodId=\"" + modId + "\"\nversion=\"" + version + "\"\ndisplayName=\"" + modName + "\"\ndescription='''\n" + description + "\n'''\nauthors=\"" + author + "\"\n\n[[dependencies." + modId + "]]\n    modId=\"forge\"\n    mandatory=true\n    versionRange=\"[47,)\"\n    ordering=\"NONE\"\n    side=\"BOTH\"\n\n[[dependencies." + modId + "]]\n    modId=\"minecraft\"\n    mandatory=true\n    versionRange=\"[1.20.1,1.21)\"\n    ordering=\"NONE\"\n    side=\"BOTH\"\n\n[[dependencies." + modId + "]]\n    modId=\"z7lib\"\n    mandatory=true\n    versionRange=\"[1.0.9,)\"\n    ordering=\"AFTER\"\n    side=\"BOTH\"\n";
            this.writeFile(baseDir + "/src/main/resources/META-INF/mods.toml", modsToml);
            String mainClass = "package com." + modId + ".main;\n\nimport net.minecraftforge.fml.common.Mod;\nimport org.slf4j.Logger;\nimport org.slf4j.LoggerFactory;\n\n@Mod(\"" + modId + "\")\npublic class " + modName.replaceAll("[^a-zA-Z0-9]", "") + " {\n    private static final Logger LOGGER = LoggerFactory.getLogger(" + modName.replaceAll("[^a-zA-Z0-9]", "") + ".class);\n    public static final String VERSION = \"" + version + "\";\n\n    public " + modName.replaceAll("[^a-zA-Z0-9]", "") + "() {\n        LOGGER.info(\"{} v{} loaded!\", \"" + modName + "\", VERSION);\n    }\n}\n";
            this.writeFile(baseDir + "/src/main/java/com/" + modId + "/main/" + modName.replaceAll("[^a-zA-Z0-9]", "") + ".java", mainClass);
            this.writeFile(baseDir + "/settings.gradle", "pluginManagement {\n    repositories {\n        gradlePluginPortal()\n        maven { url = 'https://maven.minecraftforge.net/' }\n    }\n}\nrootProject.name = '" + modName + "'\n");
            this.writeFile(baseDir + "/gradle.properties", "org.gradle.jvmargs=-Xmx4G\norg.gradle.daemon=true\n");
            this.statusMessage = "\u00a7a\u2713 \u041c\u043e\u0434 \u0441\u043e\u0437\u0434\u0430\u043d: " + baseDir;
            this.isSuccess = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask(){

                @Override
                public void run() {
                    if (((Z7ModCreatorScreen)Z7ModCreatorScreen.this).f_96541_.f_91074_ != null) {
                        ((Z7ModCreatorScreen)Z7ModCreatorScreen.this).f_96541_.f_91074_.m_6915_();
                    }
                }
            }, 2000L);
        }
        catch (Exception e) {
            this.statusMessage = "\u00a7c\u041e\u0448\u0438\u0431\u043a\u0430: " + e.getMessage();
            this.isSuccess = false;
        }
    }

    private void writeFile(String path, String content) throws Exception {
        try (FileWriter writer = new FileWriter(path);){
            writer.write(content);
        }
    }

    public boolean m_7043_() {
        return false;
    }
}

