/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package com.z7.lib.utils;

import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Z7Utils {
    private static final Random RANDOM = new Random();

    public static CompoundTag getPersistentData(Player player) {
        return player.getPersistentData();
    }

    public static void sendMessage(Player player, String message) {
        player.m_5661_((Component)Component.m_237113_((String)message), false);
    }

    public static void setTempData(Player player, String key, Object value) {
        CompoundTag data = Z7Utils.getPersistentData(player);
        if (value instanceof Integer) {
            data.m_128405_(key, ((Integer)value).intValue());
        } else if (value instanceof Boolean) {
            data.m_128379_(key, ((Boolean)value).booleanValue());
        } else if (value instanceof String) {
            data.m_128359_(key, (String)value);
        }
    }

    public static <T> T getTempData(Player player, String key, T defaultValue) {
        CompoundTag data = Z7Utils.getPersistentData(player);
        if (defaultValue instanceof Integer) {
            return (T)Integer.valueOf(data.m_128451_(key));
        }
        if (defaultValue instanceof Boolean) {
            return (T)Boolean.valueOf(data.m_128471_(key));
        }
        if (defaultValue instanceof String) {
            return (T)data.m_128461_(key);
        }
        return defaultValue;
    }

    public static void removeTempData(Player player, String key) {
        Z7Utils.getPersistentData(player).m_128473_(key);
    }

    public static boolean chance(double probability) {
        return RANDOM.nextDouble() < probability;
    }

    public static int randomRange(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }
}

