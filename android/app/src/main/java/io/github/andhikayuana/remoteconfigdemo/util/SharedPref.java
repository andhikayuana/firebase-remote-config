package io.github.andhikayuana.remoteconfigdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.github.andhikayuana.remoteconfigdemo.App;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 6/7/17
 */

public class SharedPref {

    private static SharedPreferences getPref() {
        Context context = App.getContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveString(String key, String value) {
        getPref().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getPref().getString(key, null);
    }

    public static void remove(String key) {
        getPref().edit().remove(key).apply();
    }

    public static SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }
}
