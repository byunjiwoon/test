package com.si.simembers.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.si.simembers.Activity.MainActivity;
import com.si.simembers.Api.Urls;
import com.si.simembers.Common.Schemes;
import com.si.simembers.R;
import com.si.simembers.Value.Web;
import com.si.simembers.databinding.FooterMainTabBinding;
import com.si.simembers.databinding.ViewMenuHomeBinding;
import com.si.simembers.databinding.ViewMenuMdpickBinding;

public class BaseView extends View{

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private float footerHeight = 0f;

    public BaseActivity baseActivity;
    public MainActivity mainActivity;
    private WebView wvFooter;
    private Context con;
    private float footerAddedHeight;

    public BaseView(Context context){

        super(context);
        this.con = context;
        baseActivity = new BaseActivity();
        mainActivity = new MainActivity();
        footerAddedHeight = context.getResources().getDimension(R.dimen.footer_main_tab_added_height);// dimension : n.길이

    }

/*    public void loadingTabFooter(ViewMenuEventBinding b) {
        loadWebFooter(b.footerMainTab);
    }

    public void loadingTabFooter(ViewMenuPlanBinding b) {
        loadWebFooter(b.footerMainTab);
    }

    public void loadingTabFooter(ViewMenuOutletBinding b) {
        loadWebFooter(b.footerMainTab);
    }

    public void loadingTabFooter(ViewMenuNowBinding b) {
        loadWebFooter(b.footerMainTab);
    }


    public void loadingTabFooter(ViewMenuMagazineBinding b) {
        loadWebFooter(b.footerMainTab);
    }*/

    public void loadingTabFooter(ViewMenuHomeBinding b) {
        loadWebFooter(b.footerMainTab);
    }


    public void loadingTabFooter(ViewMenuMdpickBinding b) {
        loadWebFooter(b.footerMainTab);
    }

    public void loadWebFooter(FooterMainTabBinding b) {
        wvFooter = b.wvFooter;
        wvFooter.setWebViewClient(new WebNormalClient((Activity) con));
        baseActivity.loadFooter(wvFooter);
    }

    private class WebNormalClient extends com.si.simembers.Module.WebNormalClient{


        public WebNormalClient(Activity act) {
            super(act);
        }

        @Override
        protected void onPageLoading(WebView wv, String url) { // WebFooter 클릭 시 콜백처리 (shouldOverriding에 의해 호출)
            super.onPageLoading(wv, url);
            Log.d("pppdw", "get url-" + url);

            if(footerHeight <= 0){
                footerHeight = wv.getMeasuredHeight();
            }

            if(url.startsWith(HTTP) || url.startsWith(HTTPS)){
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                con.startActivity(in);//웹페이지 띄우기
            }

            if(url.startsWith(Schemes.BOTTOM_NAVIGATOR))
            {
                Uri uri = Uri.parse(url);
                String action = uri.getQueryParameter(Schemes.NAVIGATOR_ACTION);
                if(!TextUtils.isEmpty(action)){
                    if (Schemes.NAVIGATOR_ABOUT.equalsIgnoreCase(action)) {
                        goingPage(Urls.FOOTER_ABOUT);
                    } else if (Schemes.NAVIGATOR_INTRODUCE.equalsIgnoreCase(action)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Urls.FOOTER_INTRODUCE));
                        con.startActivity(intent);
                    } else if (Schemes.NAVIGATOR_NOTICE.equalsIgnoreCase(action)) {
                        goingPage(Urls.FOOTER_NOTICE);
                    }else if (Schemes.NAVIGATOR_STORE_INFO.equalsIgnoreCase(action)) {
                        goingPage(Urls.FOOTER_STORE_INFO);
                    } else if (Schemes.NAVIGATOR_CALL_PC_WEB.equalsIgnoreCase(action)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Urls.FOOTER_PC_WEB));
                        con.startActivity(intent);
                    }  else if (Schemes.NAVIGATOR_USE_TERMS.equalsIgnoreCase(action)) {
                        goingPage(Urls.FOOTER_USE_TERMS);
                    } else if (Schemes.NAVIGATOR_PRIVATE_TERMS.equalsIgnoreCase(action)) {
                        goingPage(Urls.FOOTER_PRIVATE_TERMS);
                    }

                }
                else {
                    String mail = uri.getQueryParameter(Schemes.NAVIGATOR_MAIL_SEND);
                    if (!TextUtils.isEmpty(mail)) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + mail));
                        try {
                            con.startActivity(intent);
                        } catch (Exception e) {
                            // email app is available
                        }
                    }
                }

                String link = uri.getQueryParameter(Schemes.NAVIGATOR_LINK);
                if (link != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    con.startActivity(intent);
                }

            }
            else if (url.startsWith(Web.EXPANDED_FOOTER_VIEW)) {
                Uri query = Uri.parse(url);
                boolean expand = Boolean.parseBoolean(query.getQueryParameter("expand"));
                if (expand) {
                    wv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((footerHeight * 2) + footerAddedHeight)));
                } else {
                    wv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                mainActivity.scrollToBottom(); //마저 구현해야한다. VerticalOnlyNestedScrollView 구현해보기
            }

        }

        public void goingPage(String url){
            baseActivity.goingPage(url, con);
        }


    }




}
