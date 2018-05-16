package com.si.simembers.Common;

/**
 * Created by byun on 2018-04-07.
 */

public class Schemes {
    public static final String SCHEME = "siecmobile://";
    public static final String MAIN = "main";
    public static final String SHOW_POPUP = "showPopup";
    public static final String DEEPLINK = "deeplink";
    public static final String SCHEME_MAIN = SCHEME + MAIN + "?";
    public static final String SCHEME_DEEPLINK = SCHEME + DEEPLINK;
    public static final String QUERY_TAB = "tab";
    public static final String HOME = "home";
    public static final String PICK = "pick";
    public static final String EVENT = "event";
    public static final String PLAN = "plan";
    public static final String OUTLET = "outlet";
    public static final String NOW = "now";
    public static final String MAGAZINE = "magazine";

    public static final String SEARCH_LAYER = SCHEME + "search_layer?";
    public static final String SEARCH_LAYER_QUERY = "query";
    public static final String SEARCH_LAYER_CLOSE = "close";

    public static final String BOTTOM_NAVIGATOR = SCHEME + "navigator?";
    public static final String NAVIGATOR_ACTION = "action";
    public static final String NAVIGATOR_LINK = "link";
    public static final String NAVIGATOR_HOME = "home";
    public static final String NAVIGATOR_MYPAGE = "mypage";
    public static final String NAVIGATOR_SIFAMILY = "sifamily";
    public static final String NAVIGATOR_RECENT_PROD = "recentproduct";
    public static final String NAVIGATOR_WISHLIST = "wishlist";

    public static final String NAVIGATOR_ABOUT = "about";
    public static final String NAVIGATOR_INTRODUCE = "introduce";
    public static final String NAVIGATOR_NOTICE = "notice";
    public static final String NAVIGATOR_STORE_INFO = "info";
    public static final String NAVIGATOR_CALL_PC_WEB = "pc";
    public static final String NAVIGATOR_USE_TERMS = "useTerms";
    public static final String NAVIGATOR_PRIVATE_TERMS = "privateInfoTerms";
    public static final String NAVIGATOR_MAIL_SEND = "mailSend";

    public static final String SHERE_KAKAOSTORY = "intent:storylink";

    public static String getFullScheme(String tab) {
        return SCHEME_MAIN + QUERY_TAB + "=" + tab;
    }

    public static String getDeeplink(String url) {
        if (url == null) return null;

        String returnUrl = "";
        String[] deeps = url.split(SCHEME_DEEPLINK);

        if (1 < deeps.length && 1 < deeps[1].length()) {
            returnUrl = deeps[1];

            if (returnUrl.startsWith("?")) {
                returnUrl = returnUrl.substring(1, returnUrl.length());
            }
        }

        return returnUrl; // not deeplink pattern
    }
}