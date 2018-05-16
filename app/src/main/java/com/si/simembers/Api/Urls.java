package com.si.simembers.Api;

import android.content.Context;

import com.si.simembers.R;

/**
 * Created by byun on 2018-03-28.
 */

public class Urls {

    private static String root;
    public static String imgRoot;
    public static String WEB_HOME;
    public static String APP;
    public static String MAIN_TABS;
    public static String MAIN_EVENT_TAB;
    public static String MAIN_PLAN_TAB;
    public static String SLIDE_MENU;
    public static String UPDATE_MY_BRAND;
    public static String S_I_FAMILY_LIST;
    public static String MAIN_NOW_TAB;
    public static String MAIN_HOME_TAB;
    public static String HOME_BRAND_MORE;
    public static String MAIN_OUTLET_TAB;
    public static String MAIN_OUTLET_MORE_TAB;
    public static String OUTLET_BRAND_MORE;
    public static String OUTLET_4BANNER_MORE;
    public static String MAIN_MAGAZINE_TAB;
    public static String MAIN_PICK_TAB;
    public static String SPLASH;
    public static String USER_INFO;
    public static String RECENT_PROD;
    public static String EVENT_WINNER;
    public static String FORWARD_POPUP;

    public static String BASKET_COUNT;

    public static String MY_PAGE;
    public static String DELIVERY_LOGIN;
    public static String DELIVERY;
    public static String BOOKMARK;
    public static String SHOPPING_BASKET;
    public static String GNB_SEARCH;
    public static String GNB_SEARCH_DETAIL;
    public static String SIV_STORE;
    public static String CUSTOMER_CENTER;
    public static String SETTING;

    public static String USER_LOGIN;
    public static String USER_LOGIN_;
    public static String USER_LOGIN_NO_PORT;
    public static String USER_LOGIN_NO_PORT_;
    public static String USER_LOGOUT_AJAX;
    public static String USER_LOGOUT;
    public static String USER_LOGOUT_SETTING;

    public static String MAIN_FOOTER;

    public static String MAGAZINE_JAJU_N;

    public static String HOME_CATE_WOMEN;
    public static String HOME_CATE_MAN;
    public static String HOME_CATE_OTHERS;
    public static String HOME_CATE_KIDS;
    public static String HOME_CATE_BEAUTY;
    public static String HOME_CATE_LIVING;

    public static String FOOTER_ABOUT;
    public static String FOOTER_INTRODUCE;
    public static String FOOTER_NOTICE;
    public static String FOOTER_STORE_INFO;
    public static String FOOTER_PC_WEB;
    public static String FOOTER_USE_TERMS;
    public static String FOOTER_PRIVATE_TERMS;

    public static void init(Context context){

        root = context.getResources().getString(R.string.api_root);
        imgRoot = context.getResources().getString(R.string.img_root);
        WEB_HOME = context.getResources().getString(R.string.api_web_home);
        APP = context.getResources().getString(R.string.api_app);
        MAIN_TABS = context.getResources().getString(R.string.api_main_tabs);
        MAIN_EVENT_TAB = context.getResources().getString(R.string.api_main_event_tab);
        MAIN_PLAN_TAB = context.getResources().getString(R.string.api_main_plan_tab);
        SLIDE_MENU = context.getResources().getString(R.string.api_slide_menu);
        UPDATE_MY_BRAND = context.getResources().getString(R.string.api_update_my_brand);
        S_I_FAMILY_LIST = context.getResources().getString(R.string.api_s_i_family_list);
        MAIN_NOW_TAB = context.getResources().getString(R.string.api_main_now_tab);
        MAIN_HOME_TAB = context.getResources().getString(R.string.api_main_home_tab);
        HOME_BRAND_MORE = context.getResources().getString(R.string.api_home_brand_more);
        MAIN_OUTLET_TAB = context.getResources().getString(R.string.api_main_outlet_tab);
        MAIN_OUTLET_MORE_TAB = context.getResources().getString(R.string.api_main_outlet_more_tab);
        OUTLET_BRAND_MORE = context.getResources().getString(R.string.api_outlet_brand_more);
        OUTLET_4BANNER_MORE = context.getResources().getString(R.string.api_outlet_4banner_more);
        MAIN_MAGAZINE_TAB = context.getResources().getString(R.string.api_main_magazine_tab);
        MAIN_PICK_TAB = context.getResources().getString(R.string.api_main_pick_tab);
        SPLASH = context.getResources().getString(R.string.api_splash);
        USER_INFO = context.getResources().getString(R.string.api_user_info);
        RECENT_PROD = context.getResources().getString(R.string.api_recent_prod);
        EVENT_WINNER = context.getResources().getString(R.string.api_event_winner);
        FORWARD_POPUP = context.getResources().getString(R.string.api_main_popup);
        BASKET_COUNT = context.getResources().getString(R.string.api_basket_count);
        MY_PAGE = context.getResources().getString(R.string.api_my_page);
        DELIVERY_LOGIN = context.getResources().getString(R.string.api_delivery_login);
        DELIVERY = context.getResources().getString(R.string.api_delivery);
        BOOKMARK = context.getResources().getString(R.string.api_bookmark);
        SHOPPING_BASKET = context.getResources().getString(R.string.api_shopping_basket);
        GNB_SEARCH = context.getResources().getString(R.string.api_gnb_search);
        GNB_SEARCH_DETAIL = context.getResources().getString(R.string.api_gnb_search_detail);
        SIV_STORE = context.getResources().getString(R.string.api_siv_store);
        CUSTOMER_CENTER = context.getResources().getString(R.string.api_customer_center);
        SETTING = context.getResources().getString(R.string.api_setting);
        USER_LOGIN = context.getResources().getString(R.string.api_user_login);
        USER_LOGIN_ = context.getResources().getString(R.string.api_user_login_);
        USER_LOGIN_NO_PORT = context.getResources().getString(R.string.api_user_login_no_port);
        USER_LOGIN_NO_PORT_ = context.getResources().getString(R.string.api_user_login_no_port_);
        USER_LOGOUT_AJAX = context.getResources().getString(R.string.api_user_logout_ajax);
        USER_LOGOUT = context.getResources().getString(R.string.api_user_logout);
        USER_LOGOUT_SETTING = context.getResources().getString(R.string.api_user_logout_setting);
        MAIN_FOOTER = context.getResources().getString(R.string.api_main_footer);
        MAGAZINE_JAJU_N = context.getResources().getString(R.string.api_magazine_jaju_n);
        HOME_CATE_WOMEN = context.getResources().getString(R.string.api_home_cate_women);
        HOME_CATE_MAN = context.getResources().getString(R.string.api_home_cate_man);
        HOME_CATE_OTHERS = context.getResources().getString(R.string.api_home_cate_others);
        HOME_CATE_KIDS = context.getResources().getString(R.string.api_home_cate_kids);
        HOME_CATE_BEAUTY = context.getResources().getString(R.string.api_home_cate_beauty);
        HOME_CATE_LIVING = context.getResources().getString(R.string.api_home_cate_living);
        FOOTER_ABOUT = context.getResources().getString(R.string.api_footer_about);
        FOOTER_INTRODUCE = context.getResources().getString(R.string.api_footer_introduce);
        FOOTER_NOTICE = context.getResources().getString(R.string.api_footer_notice);
        FOOTER_STORE_INFO = context.getResources().getString(R.string.api_footer_store_info);
        FOOTER_PC_WEB = context.getResources().getString(R.string.api_footer_pc_web);
        FOOTER_USE_TERMS = context.getResources().getString(R.string.api_footer_user_terms);
        FOOTER_PRIVATE_TERMS = context.getResources().getString(R.string.api_footer_private_terms);
    }

    public static String getRoot() {
        return root;
    }

    public static String getBase() {
        return getRoot();
    }
}
