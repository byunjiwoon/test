package com.si.simembers.Module;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.si.simembers.Api.Urls;
import com.si.simembers.Base.BaseApplication;

/**
 * Created by byun on 2018-03-28.
 */

public class CookiesManager {

    public static final String AUTO_LOGIN = "autoLoginYn";
    public static final String AUTO_LOGIN_Y = "Y";

    public static final String RECENT_PROD = "sivProducts";

    public static final String CART_NO = "cartNo";
    public static final String ENC_MBR_NO = "mbrNo";
    public static final String USER_INPUT_ID = "userInputId";
    public static final String PWD_CHANGE_NO_POP = "pwdChangeNoPop";

    public static final String JSESSIONID_MO = "JSESSIONID_MO";

    public static void removeAllCookies() {
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(BaseApplication.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        cookieManager.removeSessionCookie();

        cookieSyncManager.sync();
    }

    public static String getAllCookies() {
       CookieSyncManager.createInstance(BaseApplication.getContext());
        CookieManager.getInstance().setAcceptCookie(true);
        return CookieManager.getInstance().getCookie(Urls.getBase());
    }

    public static void setCookieForName(String name, String value) {
        CookieSyncManager.createInstance(BaseApplication.getContext());
        CookieManager.getInstance().setAcceptCookie(true);

        String cookies = CookieManager.getInstance().getCookie(Urls.getBase());
        if (cookies != null && cookies.contains(name)) {
            int start = cookies.indexOf(name); //
            int end = cookies.indexOf(";", start); // name부터 ; 까지

            if (end <= 0) { // 없으면 -1을 리턴하니
                end = cookies.length();
            }

            String temp = cookies.substring(start, end);
            cookies = cookies.replace(temp, name + "=" + value);
        }
        else {
            cookies = cookies + ";" + name + "=" + value;
        }

        CookieManager.getInstance().removeAllCookie();

        String[] cookiesArray = cookies.split(";");
        for (String s : cookiesArray) {
            CookieManager.getInstance().setCookie(Urls.getBase(), s);
        }

        CookieSyncManager.getInstance().sync();
    }
    public static String getCookieForName(String name) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        String cookies = cookieManager.getCookie(Urls.getBase());
        String getVal = null;

        if (cookies == null) {
            return null;
        }

        String[] temp = cookies.split(";");
        for (String ar1 : temp) {
            if (ar1.contains(name)) {
                String[] temp1 = ar1.split("=", 2);
                if (temp1.length > 1) {
                    getVal = temp1[1];
                }
                else {
                    getVal = "";
                }
                break;
            }
        }
        return getVal;
    }
}
