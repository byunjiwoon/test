package com.si.simembers.Value;

import android.content.Context;

import com.si.simembers.Api.Urls;
import com.si.simembers.Common.Schemes;

/**
 * Created by dwpark on 2017. 7. 4..
 */

public class Web {
    public static final String FACEBOOK_LOGIN = "https://www.facebook.com";

    public static final String NAVER_LOGIN = "https://devmo.sivillage.com:441/login/naverLogin";

    public static final String IF_LOGIN_SESSION_ERROR = "http://devmo.sivillage.com/fashion/hallFA?returnUrl=null";

    public static final String USER_LOGIN = Schemes.SCHEME+"user_login?";
    public static final String NM_MBR_NO = "mbrNo";
    public static final String NM_PW_TOKEN = "encLoginPwd";
    public static final String FINISH_POP_HANDLE = "popHandle";

    public static final String EXPANDED_FOOTER_VIEW = Schemes.SCHEME+"footerHeight?";
    public static final String CART_COUNT_CHANGED = Schemes.SCHEME+"cartCount?";
    public static final String QUERY_CART_COUNT = "cartCount";
    public static final String QUERY_CART_NO = "cartNo";
}
