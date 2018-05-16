package com.si.simembers.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.CookieManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.si.simembers.Api.Api;
import com.si.simembers.Api.ApiCallBack;
import com.si.simembers.Api.Model.BasketCountData;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Api.Model.UserInfoData;
import com.si.simembers.Api.Urls;
import com.si.simembers.Base.BaseActivity;
import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.Fragment.EditorFragment;
import com.si.simembers.Fragment.EventFragment;
import com.si.simembers.Fragment.HomeFragment;
import com.si.simembers.Fragment.ImageBannerFragment;
import com.si.simembers.Fragment.MagazineFragment;
import com.si.simembers.Fragment.NowFragment;
import com.si.simembers.Fragment.OutletFragment;
import com.si.simembers.Fragment.PlanFragment;
import com.si.simembers.GNBMenuData;
import com.si.simembers.Api.Model.SplashData;
import com.si.simembers.Module.CookiesManager;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Value.Preferences;
import com.si.simembers.View.Home;


import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by byun on 2018-03-15.
 */

public class SplashActivity extends AppCompatActivity implements ApiCallBack.RetrofitCallback{


    private static final long LODING_TIME = 5000;
    private String TAG = "SplashActivity";
    private SplashData splashData;
    private Handler mHandler;
    private Runnable mRunnable;

    private ImageView ivSplash;
    private String imgUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        new SiApplication();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        G.DEVICE_WIDTH = displayMetrics.widthPixels;
        G.DEVICE_HEIGHT = displayMetrics.heightPixels;
        G.APP_VERSION = getVersionName();
        //G.APP_AGENT = G.AGENT + G.APP_VERSION;
        G.APP_AGENT = G.AGENT + "4.2";  // 일단 하드코딩 이렇게 해야 rest api 동작
        G.isAlive = true;

        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        ivSplash = (ImageView)findViewById(R.id.ivSplash);

        init();

      // CookiesManager.getAllCookies();

    }

    private void enterMain() {

        if (SiApplication.get().getHomeTabData() == null
                || SiApplication.get().getSlideMenuData() == null
                || SiApplication.get().getUserInfoData() == null
                || SiApplication.get().getTabInfoData() == null) { //데이터 없이 메인으로 넘어가는 경우가 있음
            return;
        }

        String recentNo = Preferences.loadSharedPreferencesString(this, Preferences.RECENT_PROD);
        if (!TextUtils.isEmpty(recentNo)) {
            CookiesManager.setCookieForName(CookiesManager.RECENT_PROD, recentNo);
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void  init(){

        if (TextUtils.isEmpty(Urls.getRoot())) {
            Urls.init(this);
        }
        String appHash = getMyHash();
        if (appHash == null) {
            appHash = "1234";
        }
        SiApplication.get().api = Api.rf.create(Api.class);
        setUserLoginStatus();


        Log.d("bbb", "getSplash() Call");
        new ApiCallBack(this, SplashActivity.this, true).execute(SiApplication.get().api.getSplash(Urls.APP + Urls.SPLASH, appHash));





    }

    private void setUserLoginStatus() {//아직 미완성

        G.MBR_NO = Preferences.loadSharedPreferencesString(this, Preferences.MBR_NO);
        G.PW_TOKEN = Preferences.loadSharedPreferencesString(this, Preferences.PW_TOKEN);
        G.ENC_MBR_NO = Preferences.loadSharedPreferencesString(this, Preferences.ENC_MBR_NO);
        Log.d("byun",  G.ENC_MBR_NO+"=MBR_NO ");
        if (G.MBR_NO == null || G.PW_TOKEN == null || G.ENC_MBR_NO == null) {
            Utils.userLogout(this);
        }

        else {
            // 종료 후 로그인한 기록이 있는데 자동저장 안했을 경우
            if (CookiesManager.AUTO_LOGIN_Y.equalsIgnoreCase(CookiesManager.getCookieForName(CookiesManager.AUTO_LOGIN))) {
                // 쿠키 값
                G.USER_LOGIN = true;
            }
            else {
                G.USER_LOGIN = false;
                Utils.userLogout(this);
            }
        }

    }

    private String getVersionName() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            return info.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            return "1.0";
        }
    }




    private String getMyHash() {
        PackageInfo info = null;
        try {//3333 06 8210242
            info = this.getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("bbb", "getMyHash: "+info.signatures[0].toCharsString());
        return info.signatures[0].toCharsString();
    }



    @Override
    public void onSucc(Response response, String url) {

        if(url.contains(Urls.SPLASH)){
            splashData = (SplashData)response.body();
            Random rand = new Random();
            int index = rand.nextInt(splashData.splash.size());
            imgUrl = splashData.splash.get(index).imageUrl;

            ivSplash.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        MGlide.load(SplashActivity.this,imgUrl)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(ivSplash);
                    }
                    catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            Log.d(TAG, "onSucc: hashCheck = "+splashData.hashCheck);
            Log.d(TAG, "onSucc: MBR_NO = "+G.MBR_NO);
            Log.d(TAG, "onSucc: PW_TOKEN = "+G.PW_TOKEN);


            String cookies = null;   // 로그인 기능 도입시 수정필요!
            Log.d("bbb", "getBasketCount() Call");

            new ApiCallBack(this, this, true)
                    .execute(SiApplication.get().api.getBasketCount(Urls.APP + Urls.BASKET_COUNT, cookies, G.MBR_NO, G.PW_TOKEN));

        }

        else if (url.contains(Urls.BASKET_COUNT)){

            BasketCountData basketCountData = (BasketCountData)response.body();
            SiApplication.get().setBasketCountData(basketCountData);
            new ApiCallBack(this, this, true).execute(SiApplication.get().api.getUserInfo(Urls.APP + Urls.USER_INFO, G.MBR_NO, G.PW_TOKEN));

        }
        else{
            if(url.contains(Urls.USER_INFO)){
                UserInfoData userInfoData = (UserInfoData)response.body();
                SiApplication.get().setUserInfoData(userInfoData);
                new ApiCallBack(this, this, true).execute(SiApplication.get().api.getSlideMenu(Urls.APP + Urls.SLIDE_MENU, G.MBR_NO, G.PW_TOKEN));

            }
            else if(url.contains(Urls.SLIDE_MENU))
            {
                SlideMenuData slideMenuData = (SlideMenuData)response.body();
                SiApplication.get().setSlideMenuData(slideMenuData);
                new ApiCallBack(this, this, true).execute(SiApplication.get().api.getTabs(Urls.APP + Urls.MAIN_TABS));

            }

            else if(url.contains(Urls.MAIN_TABS)){
               TabInfoData tabInfoData = (TabInfoData) response.body();
               SiApplication.get().setTabInfoData(tabInfoData);
                new ApiCallBack(this, this, true).execute(SiApplication.get().api.getMainHomeTab(Urls.APP + Urls.MAIN_HOME_TAB, G.MBR_NO, G.PW_TOKEN));

            }

            else if(url.contains(Urls.MAIN_HOME_TAB)){
               MainHomeTabData mainHomeTabData = (MainHomeTabData) response.body();
               SiApplication.get().setHomeTabData(mainHomeTabData);

            }

            enterMain();

        }

    }



    @Override
    public void onFail(Response response, String url) {

    }

    @Override
    public void onError(Call call, Throwable t, String url){

    }

}
