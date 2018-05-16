package com.si.simembers.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.si.simembers.Api.Urls;
import com.si.simembers.Base.BaseActivity;
import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.Module.CookiesManager;
import com.si.simembers.Module.VideoWebChromeClient;

import com.si.simembers.Module.WebNormalClient;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Value.Preferences;
import com.si.simembers.Widget.NestedWebView;



public class BaseWebViewActivity extends BaseActivity {


    public static final int RESULT_USER_LOGOUT = 1001;
    public static final int RESULT_SELECTED_OTHER_TAB = 1002;

    protected void onMenuDrawerInit(){

    }

    public interface OnHostChangeListener{
        void onHostChange(boolean isBaseHost);
    }

    protected void onLoadPage(String url){

    }

    public VideoWebChromeClient getWebChromeClient(NestedWebView wv, View nonVideoLayout, ViewGroup videoLayout){

        View loadingView= getLayoutInflater().inflate(R.layout.video_loading_layout,null);
        VideoWebChromeClient chromeClient = new VideoWebChromeClient(nonVideoLayout, videoLayout, loadingView, wv);
        return chromeClient;
    }

    private class VideoWebChromeClient extends com.si.simembers.Module.VideoWebChromeClient{

        public VideoWebChromeClient(View activityNonVideoView, ViewGroup activityVideoView, View loadingView, NestedWebView webView) {
            super(activityNonVideoView, activityVideoView, loadingView, webView);
        }


        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    public WebNormalClient getWebClient(OnHostChangeListener listener){

        return new WebNormalClient(this, listener);

    }

    private class WebNormalClient extends com.si.simembers.Module.WebNormalClient{

        private OnHostChangeListener listener;

        public WebNormalClient(Activity act, OnHostChangeListener listener) {
            super(act);
            this.listener = listener;
        }

        private void hostCheck(String url) {

            if (!url.startsWith("http")) return;

            if (listener != null) {
                Uri BaseUri = Uri.parse(Urls.getBase());
                Uri targetUri = Uri.parse(url);
                int isBaseHost = TextUtils.equals(BaseUri.getHost(), targetUri.getHost()) ? 1 : 0;
                handler.sendEmptyMessageDelayed(isBaseHost, 700);
            }
        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                boolean isBaseHost = msg.what == 0;
                listener.onHostChange(isBaseHost);
            }
        };



        @Override
        public void onPageStarted(final WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            setOnGnbVisibility(View.VISIBLE);
            hostCheck(url);

            showGnbImage();

            if (url.contains(Urls.USER_LOGOUT)
                    || url.contains(Urls.USER_LOGOUT_AJAX)
                    || url.contains(Urls.USER_LOGOUT_SETTING)) {
                // 유져 로그아웃.
                G.USER_LOGIN = false;
                SiApplication.get().getBasketCountData().cartCount = 0;
                setResult(BaseWebViewActivity.RESULT_USER_LOGOUT);
                finish();
            } else if (G.USER_LOGIN
                    && (url.contains(Urls.USER_LOGIN)
                    || url.contains(Urls.USER_LOGIN_)
                    || url.contains(Urls.USER_LOGIN_NO_PORT)
                    || url.contains(Urls.USER_LOGIN_NO_PORT_))) {
                onFinish();
                Log.d("pppdw", "WEBVIEW JSESSION->" + CookiesManager.getAllCookies());
                if (view != null) {
                    Handler han = new Handler();
                    han.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.reload();
                        }
                    }, 200);
                }
            }
        }

        @Override
        protected void onPageLoadError(int errorCode, String errorDescription) {
            super.onPageLoadError(errorCode, errorDescription);
        }


        @Override
        protected void onPageLoading(WebView wv, String url) {
            super.onPageLoading(wv, url);
        }

        @Override
        protected void onOriginLoginEvent(Uri uri) {
            super.onOriginLoginEvent(uri);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("pppdw", "onPageFinished->" + url);

            Log.e("DWORKS","request Cookie : " + G.MY_JSESSION_MO_ID);

            Log.e("DWORKS","response cookie : " + CookiesManager.getCookieForName("JSESSIONID_MO"));

            //String cookies = CookiesManager.getAllCookies();

            Utils.saveJSession(CookiesManager.getCookieForName(CookiesManager.JSESSIONID_MO));

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void onFinish(){

    }




}
