/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.commands.Commands
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.z7.lib.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.z7.lib.client.gui.Z7ModCreatorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="z7lib", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Z7LibCreateModCommand {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register((LiteralArgumentBuilder)Commands.m_82127_((String)"z7lib").then(Commands.m_82127_((String)"createmod").executes(context -> {
            Minecraft.m_91087_().m_91152_((Screen)new Z7ModCreatorScreen());
            return 1;
        })));
    }
}

