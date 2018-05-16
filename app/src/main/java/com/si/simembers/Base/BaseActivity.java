package com.si.simembers.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.si.simembers.Activity.MainActivity;
import com.si.simembers.Activity.WebViewActivity;
import com.si.simembers.Adapter.MenuDrawerContentAdapter;
import com.si.simembers.Adapter.SlideBrandSubAdapter;
import com.si.simembers.Adapter.SlideCategoryAdapter;
import com.si.simembers.Api.Api;
import com.si.simembers.Api.ApiCallBack;
import com.si.simembers.Api.Model.BasketCountData;
import com.si.simembers.Api.Model.RecentProdData;
import com.si.simembers.Api.Urls;
import com.si.simembers.Common.G;
import com.si.simembers.Common.TokenManager;
import com.si.simembers.Common.Utils;
import com.si.simembers.Module.CookiesManager;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Value.Preferences;
import com.si.simembers.Value.RequestCodes;
import com.si.simembers.Value.Tags;
import com.si.simembers.View.MenuDrawerHeader;
import com.si.simembers.databinding.ActivityMainBinding;
import com.si.simembers.databinding.ActivityWebViewBinding;
import com.si.simembers.databinding.IncludeGnbBinding;
import com.si.simembers.databinding.IncludeSlideMenuBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import retrofit2.Call;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {


    Activity currentAct;

    private IncludeGnbBinding includeGnbBinding;
    private IncludeSlideMenuBinding includeSlideMenuBinding;
    private MenuDrawerContentAdapter menuDrawerContentAdapter;


    private DrawerLayout dl;
    private MenuDrawerHeader menuHeader;


    public boolean isMenuDrawer() {

        if (dl == null)
            return false;
        else
            return true;
    }

    public boolean isMenuDrawerOpened(){
        if(isMenuDrawer()){
            return dl.isDrawerOpen(Gravity.LEFT);
        }
        else return false;
    }

    protected void onSafeCreate() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!G.isAlive) {
            savedInstanceState = null;
        }

        super.onCreate(savedInstanceState);

        if (!G.isAlive) {
            Intent i = getPackageManager().getLaunchIntentForPackage(getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            onSafeCreate();
        }
    }

    public void goingPage(String url, Context con) {
        currentAct = (Activity) con;
       goingPage(url);
    }


    public void loadFooter(WebView wv) {
        settingWebOption(wv);
        wv.loadUrl(Urls.getBase() + Urls.MAIN_FOOTER);
    }


    public void goingPage(String url) {
        Intent in = null;
        if (currentAct instanceof WebViewActivity) {
            in = new Intent(WebViewActivity.PAGE_LOAD);
            in.putExtra(Tags.ACCESS_URL, url);
            sendBroadcast(in);
        }
        else {
            int requestCode;
            if (url == null || url.isEmpty()) {
                return;
            }

            in = new Intent(currentAct, WebViewActivity.class);
            requestCode = RequestCodes.WEBVIEW_ACTIVITY;

            in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            in.putExtra(Tags.ACCESS_URL, url);
            currentAct.startActivityForResult(in, requestCode);
        }

        Message closeMenuMsg = new Message();
        closeMenuHandler.sendMessageDelayed(closeMenuMsg, 500); //Drawlayout에서 이동한 경우 처리
    }

    final Handler closeMenuHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             closeMenu();
        }
    };

    public void setGnb(ActivityWebViewBinding activityWebViewBinding){
        includeGnbBinding = activityWebViewBinding.includeGnb;
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) includeGnbBinding.toolBar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        includeGnbBinding.tvGnbTitle.setVisibility(View.GONE);

        includeGnbBinding.nivGnbTitle.setVisibility(View.GONE);
        includeGnbBinding.ivGnbLogo.setVisibility(View.VISIBLE);
        includeGnbBinding.btnGnbBack.setVisibility(View.VISIBLE);
        initGnb();


    }

    public void setGnb(ActivityMainBinding activityMainBinding) {
        includeGnbBinding = activityMainBinding.includeGnb;
        includeGnbBinding.tvGnbTitle.setVisibility(View.VISIBLE);
        includeGnbBinding.tvGnbTitle.setText("신세카이");
        //includeGnbBinding.ivGnbLogo.setVisibility(View.VISIBLE);
        includeGnbBinding.btnGnbBack.setVisibility(View.GONE);
        initGnb();
    }



    private void initGnb() {
        currentAct = this;
        setOnGnbBasketFavCount();
        includeGnbBinding.btnGnbMenu.setOnClickListener(onClickGnbItem);
        includeGnbBinding.btnGnbSearch.setOnClickListener(onClickGnbItem);
        includeGnbBinding.btnGnbFav.setOnClickListener(onClickGnbItem);
        includeGnbBinding.btnGnbBack.setOnClickListener(onClickGnbItem);


    }

   public void setMenuDrawer(ActivityWebViewBinding activityWebViewBinding){
        dl = activityWebViewBinding.dlWebView;
        includeSlideMenuBinding = activityWebViewBinding.includeSlideMenu;
        bindingMenuDrawer();

   }


    public void setMenuDrawer(ActivityMainBinding activityMainBinding){
        dl = activityMainBinding.dlMain;
        includeSlideMenuBinding = activityMainBinding.includeSlideMenu;
        bindingMenuDrawer();
    }

    private void bindingMenuDrawer(){
        currentAct = this;

        dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                dl.openDrawer(Gravity.LEFT);

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        includeSlideMenuBinding.llMenuDrawerHeader.removeAllViews(); // 메인액티비티 -> 웹액티비티로 갈경우 다지워주기

        includeSlideMenuBinding.llMenuDrawer.setLayoutParams(new DrawerLayout.LayoutParams(G.DEVICE_WIDTH,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.LEFT));

        //SlideMenu 위 ( MenuDrawerHeader 클래스 안에서 뷰를 그려 붙임 )
        menuHeader = new MenuDrawerHeader(this, SiApplication.get().getTabInfoData(), menuHeaderCallBack);
        includeSlideMenuBinding.llMenuDrawerHeader.addView(menuHeader.getView());

        //SlideMenu 아래
        menuDrawerContentAdapter = new MenuDrawerContentAdapter(LayoutInflater.from(this), this
                , SiApplication.get().getSlideMenuData(), onMenuDrawerContentCallback);

        includeSlideMenuBinding.pagerDrawer.setAdapter(menuDrawerContentAdapter);
        includeSlideMenuBinding.pagerDrawer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            //    menuDrawerContentAdapter.getRecentProduct();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setOnGnbVisibility(int showing) {
        if (includeGnbBinding != null
                && includeGnbBinding.toolBar != null) {
            includeGnbBinding.toolBar.setVisibility(showing);
        }
    }



    public void closeMenu(){
        if(dl != null && dl.isDrawerOpen(Gravity.LEFT)){
            G.OPEN_LNB = false;
            dl.closeDrawer(Gravity.LEFT);

            if (G.MY_BRAND_UPDATE) {
                Intent in = new Intent(MainActivity.HOME_NOTIFY);
                sendBroadcast(in);
            }
        }

    }
    private MenuDrawerContentAdapter.onMenuDrawerContentCallback onMenuDrawerContentCallback =
            new MenuDrawerContentAdapter.onMenuDrawerContentCallback() {
                @Override
                public void onClickCategoryItem(int category, int item) {

                    goingPage(SiApplication.get().getSlideMenuData().category.get(category).categoryList.get(item).moUrlMove);

                    //WiseTracker
                }

                @Override
                public void onClickBrandFav(int page, int item, boolean isSelected) {
                    String brandCode = null;
                    if (page == SlideBrandSubAdapter.BRAND_FASHION) {
                        brandCode = SiApplication.get().getSlideMenuData().menuBrand.fashion.get(item).brndHallCd;
                    }
                    else if (page == SlideBrandSubAdapter.BRAND_BEAUTY) {
                        brandCode = SiApplication.get().getSlideMenuData().menuBrand.beauty.get(item).brndHallCd;
                    }
                    else {
                        brandCode = SiApplication.get().getSlideMenuData().menuBrand.living.get(item).brndHallCd;
                    }

                    String selType = isSelected ? "I" : "D";
                    new ApiCallBack(BaseActivity.this, new ApiCallBack.RetrofitCallback() {
                        @Override
                        public void onSucc(Response response, String url) {
                            G.MY_BRAND_UPDATE = true;
                        }

                        @Override
                        public void onFail(Response response, String url) {
                        }

                        @Override
                        public void onError(Call call, Throwable t, String url) {

                        }
                    }).execute(SiApplication.get().api.setMyFavBrand(Urls.APP + Urls.UPDATE_MY_BRAND, brandCode, selType, G.MBR_NO, G.PW_TOKEN));
                }
                @Override
                public void onClickChildBrand(String url) {

                }

                @Override
                public void onNeedUserLogin() {

                    Utils.alert(BaseActivity.this,  getString(R.string.need_user_login)
                            , getString(R.string.btn_positive), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    goingPage(Urls.USER_LOGIN);
                                }
                            }, getString(R.string.btn_negative), null);

                }

                @Override
                public void onClickBrandItem(int page, int pos) {

                }

                @Override
                public void onClickRecentProd(String url) {

                    goingPage(url);

                }

                @Override
                public void onClickCsCenter() {

                }

                @Override
                public void onClickLoginOut() {

                    if (!Utils.isLogin()) {
                        goingPage(Urls.USER_LOGIN);
                    }
                    else {
                        goingPage(Urls.USER_LOGOUT);
                    }

                }

                @Override
                public void onClickSetting() {

                }

                @Override
                public void onClickStore() {

                }

                @Override
                public void onTouchRecentView(int action) {

                }
            };
    protected void onMenuSelected(String scheme){

    }

    private MenuDrawerHeader.MenuHeaderCallBack menuHeaderCallBack = new MenuDrawerHeader.MenuHeaderCallBack() {


        @Override
        public void close(String scheme) {
            closeMenu();
            if(scheme != null){
                onMenuSelected(scheme);
            }
        }

        @Override
        public void selectMenuTab(int pos) {
            if (pos == 0) {
                menuHeader.setVisibleBrandTabs(View.GONE);
            }
            else {
                menuHeader.setVisibleBrandTabs(View.VISIBLE);
            }
            includeSlideMenuBinding.pagerDrawer.setCurrentItem(pos, true);
        }

        @Override
        public void selectSlideBrandTab(int pos) {
            menuDrawerContentAdapter.getBrandPager().setCurrentItem(pos);
        }

        @Override
        public void onClickMyPage() {

            if(G.USER_LOGIN)
            {
                goingPage(Urls.MY_PAGE);
                return;
            }

            Utils.alert(BaseActivity.this, getString(R.string.need_user_login), getString(R.string.btn_positive)
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goingPage(Urls.MY_PAGE);
                        }
                    }, getString(R.string.btn_negative),null);

        }

        @Override
        public void onClickDelivery() {

            if(G.USER_LOGIN)
                goingPage(Urls.DELIVERY);
            else
                goingPage(Urls.DELIVERY_LOGIN);
        }

        @Override
        public void onClickBookmark() {

            if(G.USER_LOGIN) {
                goingPage(Urls.BOOKMARK);
                return;
            }

            Utils.alert(BaseActivity.this, getString(R.string.need_user_login)
                    , getString(R.string.btn_positive), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            goingPage(Urls.BOOKMARK);
                        }
                    }, getString(R.string.btn_negative), null);
        }
    };


    public void settingWebOption(WebView wv) {
        /**기존 siv 웹뷰 셋팅**/

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setSupportMultipleWindows(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setTextZoom(100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(wv, true);
        }
        else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
        }

        if (!TextUtils.isEmpty(G.MY_JSESSION_MO_ID)) {
            Log.d("bbb", "WebView Setting Method : " + G.MY_JSESSION_MO_ID);
            CookiesManager.setCookieForName(CookiesManager.JSESSIONID_MO, G.MY_JSESSION_MO_ID);
        }

        wv.addJavascriptInterface(new BridgeInterface(), "appIF");
        wv.addJavascriptInterface(new TrackerInterFace(), "appTrackerIF");
        // Native와 Web간의 통신을 위해~ ...... 데이터를 더욱 편하게 주고받을 수 있다.

        String userAgent = wv.getSettings().getUserAgentString();
        wv.getSettings().setUserAgentString(userAgent + " " + G.APP_SEARCH_AGENT + ":" + G.APP_AGENT + ":");
    }
    private void setOnGnbBasketFavCount() {

        if (includeGnbBinding != null && SiApplication.get().getBasketCountData() != null) {
            BasketCountData basketCountData = SiApplication.get().getBasketCountData();
            includeGnbBinding.tvGnbFavBadge.setText(String.valueOf(basketCountData.cartCount));
            includeGnbBinding.tvGnbFavBadge.setVisibility((basketCountData.cartCount > 0) ? View.VISIBLE : View.GONE);
        }
    }

    private class BridgeInterface{
       @JavascriptInterface
        public void recentProduct(String sivProducts, String recentDates){

           Preferences.saveSharedPreferencesString(BaseActivity.this, Preferences.RECENT_PROD, sivProducts);
           new Thread(){
               @Override
               public void run() {
                   Message msg = recentProdHandler.obtainMessage();
                   recentProdHandler.sendMessage(msg);
               }
           }.start();

      }
       @JavascriptInterface
       public void headerTitle(final String type, final String value) {
           Log.d("pppdw", "type->" + type + " / value->" + value);
           new Thread() {
               public void run() {
                   Bundle bundle = new Bundle();
                   bundle.putString("type", type);
                   bundle.putString("value", value);

                   Message msg = headerTitleHandler.obtainMessage();
                   msg.setData(bundle);
                   headerTitleHandler.sendMessage(msg);
               }
           }.start();
       }

        @JavascriptInterface
        public void webLoginAuth(final String mbrNo, final String encMbrNo, final String pwdToken)
                throws UnsupportedEncodingException{
            G.MBR_NO = mbrNo;
            G.ENC_MBR_NO = encMbrNo;
            G.PW_TOKEN = URLDecoder.decode(pwdToken, "utf-8");
            G.MY_JSESSION_MO_ID = CookiesManager.getCookieForName(CookiesManager.JSESSIONID_MO);

            SiApplication.get().api = null;
            SiApplication.get().api = Api.rf.create(Api.class);

            String token = new TokenManager(BaseApplication.getContext()).createToken(G.ENC_MBR_NO);
            CookiesManager.setCookieForName(CookiesManager.ENC_MBR_NO, token);

            new Thread(){
                public void run(){
                    Message msg = userLoginHandler.obtainMessage();
                    userLoginHandler.sendMessage(msg);
                }
            }.start();

        }

        @JavascriptInterface
        public void cartCount(String cartNo, String cartCount) {

        }

        @JavascriptInterface
        public int inputPageMargin() {//초반에 호출
            return BaseApplication.getContext().getResources().getDimensionPixelSize(R.dimen.app_toolbar_height);
        }



        @JavascriptInterface
        public void webViewGoBack() {

        }


    }

    final Handler userLoginHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            Preferences.saveSharedPreferencesString(BaseActivity.this, Preferences.MBR_NO, G.MBR_NO);
            Preferences.saveSharedPreferencesString(BaseActivity.this, Preferences.PW_TOKEN, G.PW_TOKEN);
            Preferences.saveSharedPreferencesString(BaseActivity.this, Preferences.ENC_MBR_NO, G.ENC_MBR_NO);

            Intent in = new Intent(MainActivity.USER_LOGIN_SUCC);
            sendBroadcast(in);

            //in = new Intent(WebViewActivity.USER_LOGIN_SUCC);
            //sendBroadcast(in);
        }



    };

    final Handler headerTitleHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            Log.d("pppdw", "type1->" + bundle.getString("type") + " / value1->" + bundle.getString("value"));
            String type = bundle.getString("type");
            String value = bundle.getString("value");
            if ("img".equalsIgnoreCase(type)) {
                if (!value.startsWith("http")) {
                    value = Urls.imgRoot + value;
                }
                changeGnbImage(value);
            }
            else {
                changeGnbTitle(value);
            }
        }
    };

    public void changeGnbTitle(String s) {
        if (includeGnbBinding != null) {
            includeGnbBinding.ivGnbLogo.setVisibility(View.GONE);
            includeGnbBinding.nivGnbTitle.setVisibility(View.GONE);
            includeGnbBinding.tvGnbTitle.setVisibility(View.VISIBLE);
            includeGnbBinding.tvGnbTitle.setText(s);
            includeGnbBinding.tvGnbTitle.startAnimation(AnimationUtils.loadAnimation(currentAct, android.R.anim.fade_in));
        }
    }


    public void changeGnbImage(final String s) {
        if (includeGnbBinding != null && G.DEVICE_WIDTH > 0) {
            includeGnbBinding.ivGnbLogo.setVisibility(View.GONE);

            includeGnbBinding.nivGnbTitle.post(new Runnable() {
                @Override
                public void run() {
                    MGlide.load(s)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource,
                                                            GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) includeGnbBinding.nivGnbTitle.getLayoutParams();
                                    params.width = (int) (resource.getIntrinsicWidth() * getResources().getDisplayMetrics().density) / 2;
                                    params.height = (int) (resource.getIntrinsicHeight() * getResources().getDisplayMetrics().density) / 2;
                                    includeGnbBinding.nivGnbTitle.setLayoutParams(params);
                                    includeGnbBinding.nivGnbTitle.setImageDrawable(resource);
                                }
                            });
                }
            });

            includeGnbBinding.nivGnbTitle.setVisibility(View.VISIBLE);
            includeGnbBinding.tvGnbTitle.setVisibility(View.GONE);
            includeGnbBinding.nivGnbTitle.startAnimation(AnimationUtils.loadAnimation(currentAct, android.R.anim.fade_in));
        }
        else {
            showGnbImage();
        }
    }

    public void showGnbImage() {
        if (includeGnbBinding != null) {
            includeGnbBinding.ivGnbLogo.setImageResource(R.drawable.img_common_gnb_sivlogo);
            includeGnbBinding.nivGnbTitle.setVisibility(View.GONE);
            includeGnbBinding.ivGnbLogo.setVisibility(View.VISIBLE);
            includeGnbBinding.tvGnbTitle.setVisibility(View.GONE);
            includeGnbBinding.tvGnbTitle.setText("");
        }
    }

    private class TrackerInterFace{
        @JavascriptInterface
        public void purchase(String event, String purchaseId, String currency, String productJson){

        }
        @JavascriptInterface
        public void addedToCart(String event, String category, String currency, String productJson) {

        }
        @JavascriptInterface
        public void viewedContent(String event, String currency, String productJson) {

        }

        @JavascriptInterface
        public void completeRegistration() {}


    }

    public void openMenu() {
        /*1.닫혀 있을 때만 열어준다
        * 2.슬라이드 메뉴의 CATEGORY, BRAND 모두 갱신 (최근품목이라하지만 사실상 전체갱신)
        * 3.toggle 상태 변경*/

        if (!dl.isDrawerOpen(Gravity.LEFT)) {
            dl.openDrawer(Gravity.LEFT);
        }

        if (menuDrawerContentAdapter != null) {
            menuDrawerContentAdapter.getRecentProduct();
        }

        if (!G.OPEN_LNB) {
            G.OPEN_LNB = true;

        }
    }

    private View.OnClickListener onClickGnbItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent in = null;
            int requestCode = RequestCodes.REQUEST_ERR;

            if (v == includeGnbBinding.btnGnbMenu) {
                if (dl != null) {
                    openMenu();
                }
            }
            else if (v == includeGnbBinding.btnGnbSearch) {

            //    in = new Intent(currentAct, GnbSearchActivity.class);
                requestCode = RequestCodes.GNB_SEARCH;
            }
            else if (v == includeGnbBinding.btnGnbFav) {
        /*        WiseTracker.setContents(WiseTrackerTag.GNB_MENU_CART);
                WiseTracker.sendTransaction();*/
                goingPage(Urls.SHOPPING_BASKET);
            }
            else if (v == includeGnbBinding.btnGnbBack) {
                onBackPressed();
            }

            if (in != null) {
                startActivityForResult(in, requestCode);
            }

        }
    };

    public final Handler recentProdHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String recentNo = Preferences.loadSharedPreferencesString(BaseActivity.this, Preferences.RECENT_PROD);
           new ApiCallBack(BaseActivity.this, recentProdCallback, true).execute(SiApplication.get().api.getRecentProd(Urls.APP + Urls.RECENT_PROD, recentNo, G.MBR_NO, G.PW_TOKEN));


        }
    };


    private ApiCallBack.RetrofitCallback recentProdCallback = new ApiCallBack.RetrofitCallback() {
        @Override
        public void onSucc(Response response, String url) {
            RecentProdData recentProdData = (RecentProdData)response.body();
            SiApplication.get().setRecentProdData(recentProdData);
        }

        @Override
        public void onFail(Response response, String url) {

        }

        @Override
        public void onError(Call call, Throwable t, String url) {

        }
    };

    protected void onResume(){
        super.onResume();
        if (!Utils.getMyNetworkStatus(this)) {
            Utils.alert(this,  getString(R.string.network_error)
                    , getString(R.string.btn_positive), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }, null, null);
        }
    }

}