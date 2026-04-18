/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package com.z7.lib.network;

import com.z7.lib.Z7Lib;
import com.z7.lib.network.Z7Packet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class Z7Network {
    private static final Map<ResourceLocation, BiConsumer<Player, byte[]>> handlers = new HashMap<ResourceLocation, BiConsumer<Player, byte[]>>();

    public static void init() {
        Z7Lib.getLogger().info("Z7Network initialized");
    }

    public static void registerHandler(ResourceLocation id, BiConsumer<Player, byte[]> handler) {
        handlers.put(id, handler);
    }

    public static void sendToClient(ServerPlayer player, ResourceLocation id, Z7Packet packet) {
        Z7Network.sendToClientInternal(player, id, packet.toBytes());
    }

    public static void sendToServer(ResourceLocation id, Z7Packet packet) {
        Z7Network.sendToServerInternal(id, packet.toBytes());
    }

    protected static void receiveFromClient(ResourceLocation id, byte[] data, Player player) {
        BiConsumer<Player, byte[]> handler = handlers.get(id);
        if (handler != null) {
            handler.accept(player, data);
        }
    }

    public static native void sendToClientInternal(ServerPlayer var0, ResourceLocation var1, byte[] var2);

    public static native void sendToServerInternal(ResourceLocation var0, byte[] var1);
}

