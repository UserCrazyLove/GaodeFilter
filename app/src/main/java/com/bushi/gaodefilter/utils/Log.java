package com.bushi.gaodefilter.utils;

import de.robv.android.xposed.XposedBridge;

public class Log {
    private static final String TAG = "GaoDeFilter";

    public static void i(String msg) {
        XposedBridge.log(TAG + " [INFO]: " + msg);
    }

    public static void e(String msg) {
        XposedBridge.log(TAG + " [ERROR]: " + msg);
    }

    public static void e(Throwable t) {
        XposedBridge.log(TAG + " [ERROR]: " + t.getMessage());
        XposedBridge.log(t);
    }

    public static void d(String msg) {
        XposedBridge.log(TAG + " [DEBUG]: " + msg);
    }
}
