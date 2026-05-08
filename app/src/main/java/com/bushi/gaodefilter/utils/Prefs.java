package com.bushi.gaodefilter.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String SP_NAME = "gaode_filter_config";
    private static final String KEY_MIN_VALUE = "min_value";
    private static final String KEY_ENABLED = "is_enabled";
    private static final int DEFAULT_VALUE = 1000;

    public static boolean isEnabled(Context context) {
        try {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            return sp.getBoolean(KEY_ENABLED, true);
        } catch (Exception e) {
            return true;
        }
    }

    public static void setEnabled(Context context, boolean enabled) {
        try {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            sp.edit().putBoolean(KEY_ENABLED, enabled).apply();
        } catch (Exception ignored) {
        }
    }

    public static int getMinValue(Context context) {
        try {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            String val = sp.getString(KEY_MIN_VALUE, String.valueOf(DEFAULT_VALUE));
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return DEFAULT_VALUE;
        }
    }

    public static void setMinValue(Context context, int value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            sp.edit().putString(KEY_MIN_VALUE, String.valueOf(value)).apply();
        } catch (Exception ignored) {
        }
    }
}
