/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.z7.lib.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="z7lib", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Z7LibCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher dispatcher = event.getDispatcher();
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"z7lib").executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a7d\u00a7lZ7Lib Framework \u00a7r\u00a77v1.1.0"), false);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a79Z7Loader: \u00a7f1.1"), false);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a79Z7Lang: \u00a7f1.0"), false);
            return 1;
        })).then(Commands.m_82127_((String)"help").executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a7d\u00a7l=== Z7Lib \u041a\u043e\u043c\u0430\u043d\u0434\u044b ==="), false);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a7f/z7lib - \u0418\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044f \u043e \u043c\u043e\u0434\u0435"), false);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a7f/z7lib help - \u041f\u043e\u043c\u043e\u0449\u044c"), false);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a7f/z7lib reload - \u041f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0438"), false);
            return 1;
        }))).then(Commands.m_82127_((String)"reload").executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"\u00a7a\u041a\u043e\u043d\u0444\u0438\u0433\u0438 \u043f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d\u044b!"), true);
            return 1;
        })));
    }
}

