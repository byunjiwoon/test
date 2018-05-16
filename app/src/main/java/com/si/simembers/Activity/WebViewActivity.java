package com.si.simembers.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.si.simembers.Api.Urls;
import com.si.simembers.Base.BaseActivity;
import com.si.simembers.Common.G;
import com.si.simembers.Module.CookiesManager;
import com.si.simembers.Module.VideoWebChromeClient;
import com.si.simembers.Module.WebNormalClient;
import com.si.simembers.R;
import com.si.simembers.Value.Tags;
import com.si.simembers.databinding.ActivityWebViewBinding;

import retrofit2.http.HTTP;

public class WebViewActivity extends BaseWebViewActivity implements View.OnClickListener{

    public static final String USER_LOGIN_SUCC = "user_login_succ";
    public static final String PAGE_LOAD = "pageLoad";
    private static final String HTTP = "http";
    private boolean isGnbHide = false;

    ActivityWebViewBinding b;


    @Override
    protected void onSafeCreate() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        //웹페이지 렌더링할 때 가급적 가속기능으로!

        IntentFilter filter = new IntentFilter();
        filter.addAction(USER_LOGIN_SUCC);
        registerReceiver(receiverLoginSucc, filter);

        setGnb(b);

        Message webviewDrawMessage = webviewDrawHandler.obtainMessage();
        webviewDrawMessage.what = 0;
        webviewDrawHandler.sendMessageDelayed(webviewDrawMessage, 200);


    }



    private BroadcastReceiver receiverLoginSucc = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setMenuDrawer(b);
        }
    };



    @Override
    protected void onDestroy() {
        if (b != null && b.nwvContent != null) {
            b.nwvContent.destroy();
        }
        super.onDestroy();
        if (G.isAlive) {
            unregisterReceiver(receiverLoginSucc);

        }
    }

    private final Handler webviewDrawHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            init();
            onNewIntent(getIntent());
        }
    };

    private void init(){

        if(!isMenuDrawer()){
            onMenuDrawerInit();
        }

        setWebProperty();
        setWebCookie();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String accessUrl = intent.getStringExtra(Tags.ACCESS_URL);

        if(accessUrl == null)
            accessUrl = "";

        if(!accessUrl.startsWith(HTTP)){

            accessUrl = Urls.getBase() + accessUrl;

        }

        onLoadPage(accessUrl);



    }

    @Override
    protected void onLoadPage(String url) {
        super.onLoadPage(url);
        loadPage(url);

    }

    private void loadPage(String url) {
        b.nwvContent.loadUrl(url);
    }

    private void setWebCookie() {

        if (G.MY_JSESSION_MO_ID != null && !G.MY_JSESSION_MO_ID.equals("")) {

            CookiesManager.setCookieForName(CookiesManager.JSESSIONID_MO,G.MY_JSESSION_MO_ID);
            Log.d("bbb", "WebViewActivity Init() : " + G.MY_JSESSION_MO_ID);

        }

    }

    private void setWebProperty() {

        settingWebOption(b.nwvContent);
        b.nwvContent.setWebViewClient(getWebClient(onHostChangeListener));
        b.nwvContent.setWebChromeClient(getWebChromeClient(b.nwvContent, b.llNoneVideo, b.rlVideoLayout));

    }

    @Override
    protected void onFinish() {
        super.onFinish();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(isMenuDrawerOpened())
            closeMenu();
        else if(b.nwvContent.canGoBack())
            b.nwvContent.goBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onMenuDrawerInit() {
        super.onMenuDrawerInit();
        setMenuDrawer(b);
    }

    private OnHostChangeListener onHostChangeListener = new OnHostChangeListener() {
        @Override
        public void onHostChange(boolean isBaseHost) {

        }
    };



    @Override
    protected void onStop() {
        super.onStop();

        if (this.isFinishing()) {

            b.nwvContent.destroy();
            b.clWebView.removeAllViews();
        }
    }



    @Override
    public void onClick(View v) {

    }
}
