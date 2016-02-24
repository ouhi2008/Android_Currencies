package com.apress.gerber.currencies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by fwang5 on 2/24/2016.
 */
public class PrefsMgr {
    private static SharedPreferences sSharedPreferences;

    public static void setString(Context context,String locale, String code) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sSharedPreferences.edit();
        editor.putString(locale,code);
        editor.commit();
    }

    public static String getString(Context context,String locale){
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sSharedPreferences.getString(locale,null);
    }
}
