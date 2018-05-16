package com.si.simembers.Module;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.si.simembers.Common.G;
import com.si.simembers.Common.Schemes;
import com.si.simembers.Common.Utils;

/**
 * Created by dwpark on 2017. 6. 20..
 */

public class WebNormalClient extends WebViewClient {

    /**Origin Code**/
    private boolean isLivingLeftMenu;
    private boolean isLivingRightMenu;
    private boolean isSiecLeftMenu;
    private boolean isSiecRightMenu;
    /**Origin Code**/

    public WebNormalClient(Activity act){
        isSiecLeftMenu = false;
        isSiecRightMenu = false;
        isLivingLeftMenu = false;
        isLivingRightMenu = false;
    }

    protected void onPageLoadError(int errorCode, String errorDescription){

    }

    protected void onPageLoading(WebView wv, String url){

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        onPageLoadError(errorCode, description);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        onPageLoadError(error.getErrorCode(), error.getDescription().toString());
        super.onReceivedError(view, request, error);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d("pppdw", "get request->"+request.getUrl().toString());
        onPageLoading(view, request.getUrl().toString());

        if(!request.getUrl().toString().startsWith("http")
                && !request.getUrl().toString().startsWith("https")){
            return true;
        }else{
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("pppdw", "get request->"+url);
        onPageLoading(view, url);
        if(!url.startsWith("http")
                && !url.startsWith("https")){
            return true;
        }else{
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);


    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        Log.d("pppdw", "view->"+cancelMsg);
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    public boolean originUrlEventLogic(final Activity act, String url){
        Log.d("pppdw", "get actionUrl->"+url);
        if (Utils.onCalledNewBrowser(act, url)) {
            return true;
        }

        else if (url.startsWith("siec")) {
            Uri uri = Uri.parse(url);
            String action = uri.getQueryParameter("action");
            if ("leftMenu".equals(action)) {
                String status = uri.getQueryParameter("status");
                if (status != null) {
                    isSiecLeftMenu = "y".equals(status);
                }
            } else if ("rightMenu".equals(action)) {
                String status = uri.getQueryParameter("status");
                if (status != null) {
                    isSiecRightMenu = "y".equals(status);
                }
            }

            return true;
        }

        else if (url.startsWith("jaju")) {
            Uri uri = Uri.parse(url);
            String action = uri.getQueryParameter("action");
            if ("leftMenu".equals(action)) {
                String status = uri.getQueryParameter("status");
                if (status != null) {
                    isLivingLeftMenu = "y".equals(status);
                }
            } else if ("rightMenu".equals(action)) {
                String status = uri.getQueryParameter("status");
                if (status != null) {
                    isLivingRightMenu = "y".equals(status);
                }
            } else if ("login".equals(action)) {
                onOriginLoginEvent(uri);
            }

            return true;
        }

        return false;
    }

    protected void onOriginLoginEvent(Uri uri){
    }

}
