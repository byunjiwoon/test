package com.si.simembers.Value;

/**
 * Created by dwpark on 2017. 7. 4..
 */

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

public class Preferences {
    public static final String MBR_NO = "mbrNo";
    public static final String PW_TOKEN = "pwToken";
    public static final String ENC_MBR_NO = "encMbrNo";
    public static final String LAST_LOADED_SPLASH = "lastLoadedSplash";
    public static final String CART_NO = "cartNo";
    public static final String RECENT_PROD = "sivProducts";
    public static final String PWD_CHANGE_NO_POP = "pwdChangeNoPop";
    public static final String FORWARD_POPUP_TO_DAY = "forwardPopupToDay";

    public static final String APP_SAVE_NAME = "profile_data";

    public static void saveSharedPreferencesString(Context context, String key, String text) {
        SharedPreferences pref = context.getSharedPreferences(APP_SAVE_NAME, Service.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        String value = encode(text);

        edit.putString(key, value);
        edit.commit();
    }

    public static String loadSharedPreferencesString(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(APP_SAVE_NAME, Service.MODE_PRIVATE);
        String value = pref.getString(key, null);

        if (!TextUtils.isEmpty(value)) {
            value = decode(value);
        }

        return value;
    }

    public static void removeSharedPreferences(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(APP_SAVE_NAME, Service.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(key);
        edit.commit();
    }

    private static String encode(String str) {
        String encode;
        try {
            encode = Base64.encodeToString(str.getBytes(), 0);
        }
        catch (Exception e) {
            encode = str;
        }

        return encode;
    }

    private static String decode(String str) {
        String decode;
        try {
            decode = new String(Base64.decode(str, 0));
        }
        catch (Exception e) {
            decode = str;
        }

        return decode;
    }
}

