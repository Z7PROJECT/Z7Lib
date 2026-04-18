/*
 * Decompiled with CFR 0.152.
 */
package com.z7.lib.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Z7Timer {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    public static void schedule(Runnable task, long delay, TimeUnit unit) {
        scheduler.schedule(task, delay, unit);
    }

    public static void scheduleRepeating(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}

