package com.si.simembers.Common;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.si.simembers.Activity.MainActivity;
import com.si.simembers.Activity.SplashActivity;
import com.si.simembers.Adapter.SlideBrandAdapter;
import com.si.simembers.Adapter.SlideBrandSubAdapter;
import com.si.simembers.Api.Api;
import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Module.CookiesManager;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Value.Preferences;
import com.si.simembers.databinding.CommonAlertBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byun on 2018-03-28.
 */


public class Utils {


        public static synchronized void saveJSession(String cookies) {
            if (TextUtils.isEmpty(cookies)) return;


            String[] cookieList = cookies.split(";");
            for (String cookie : cookieList) {

                //Log.e("DWorks", "Cookie Name : " + cookie);
                if (cookie.contains(CookiesManager.JSESSIONID_MO)) {
                    String jSessionMo = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                    if (!TextUtils.isEmpty(jSessionMo)) {
                        G.MY_JSESSION_MO_ID = jSessionMo;

                        Log.d("bbb", "saveJSession: " + jSessionMo);

                        CookiesManager.setCookieForName(CookiesManager.JSESSIONID_MO, jSessionMo);
                    }
                }

            }
        }

    public static boolean isLogin() {
        if (!G.USER_LOGIN &&  // 단 하나의 정보라도  누락되어 있으며, 현재 비로그인 상태인 경우!
                (G.PW_TOKEN == null || G.PW_TOKEN.isEmpty()
                        || G.MBR_NO == null || G.MBR_NO.isEmpty()
                        || G.ENC_MBR_NO == null || G.ENC_MBR_NO.isEmpty())) {
            return false;
        }
        else {
            return true;
        }
    }

        public static SlideMenuData brandFavSort(SlideMenuData.MenuBrand origin, int page, int item, boolean isSelected){


            if(SiApplication.get().getSlideMenuData().menuBrand.myBrand == null)
                SiApplication.get().getSlideMenuData().menuBrand.myBrand = new ArrayList<>();

            if (origin.myBrand == null)
                origin.myBrand = SiApplication.get().getSlideMenuData().menuBrand.myBrand;

            String brandCode ="";

            switch (page){
                case SlideBrandSubAdapter.BRAND_FASHION:
                    brandCode = SiApplication.get().getSlideMenuData().menuBrand.fashion.get(item).brndHallCd;
                    break;

                case SlideBrandSubAdapter.BRAND_BEAUTY:
                    brandCode = SiApplication.get().getSlideMenuData().menuBrand.beauty.get(item).brndHallCd;
                    break;

                case SlideBrandSubAdapter.BRAND_LIVING:
                    brandCode = SiApplication.get().getSlideMenuData().menuBrand.living.get(item).brndHallCd;
                    break;



            }

            if(isSelected) // 별표 눌렀을 때!
            {
                boolean isAlreadyAdded = false;
                for(int i=0; i<origin.myBrand.size(); i++)
                {
                    if(origin.myBrand.get(i) != null && brandCode.equalsIgnoreCase(origin.myBrand.get(i))){
                        isAlreadyAdded = true;
                        break;
                    }
                }

                if(!isAlreadyAdded){
                    SiApplication.get().getSlideMenuData().menuBrand.myBrand.add(brandCode);

                }


            }
            else{
                boolean isAlreadyRemoved = true;
                int selPos = 0;
                for(int i=0; i<origin.myBrand.size(); i++){
                    if(origin.myBrand.get(i) != null && brandCode.equalsIgnoreCase(origin.myBrand.get(i))){
                        selPos = i;
                        isAlreadyRemoved = false; // 아직 제거되지 않음!!
                        break;
                    }
                }

                if(!isAlreadyRemoved){  //아직 제거되지 않았으니 제거!
                    List<String> dummy = new ArrayList<>();
                    for(int i=0; i<origin.myBrand.size(); i++){

                        if(selPos!=i)
                            dummy.add(origin.myBrand.get(i));
                    }
                    origin.myBrand = dummy;
                }
            }

            SiApplication.get().getSlideMenuData().menuBrand.myBrand = origin.myBrand;
            return finalSortMyBrand(page);
        }

    public static SlideMenuData finalSortMyBrand(int page) {
        return brandSortLogic(page);
    } // 클릭 시

    public static SlideMenuData finalSortMyBrand(){  //로그인 시 로그인 정보에 맞게

            for(int i=0; i< SlideBrandAdapter.PAGE; i++){
                brandSortLogic(i);
            }


            return SiApplication.get().getSlideMenuData();

    }

    private static SlideMenuData brandSortLogic(int page) {

            SlideMenuData slideMenuData = SiApplication.get().getSlideMenuData();

            if(slideMenuData.menuBrand.myBrand == null)
                slideMenuData.menuBrand.myBrand = new ArrayList<>();

            List<SlideMenuData.MenuBrandChild> target = null;

            switch (page){
                case SlideBrandSubAdapter.BRAND_FASHION:
                    target = slideMenuData.menuBrand.fashion;
                    break;
                case SlideBrandSubAdapter.BRAND_BEAUTY:
                    target = slideMenuData.menuBrand.beauty;
                    break;
                case SlideBrandSubAdapter.BRAND_LIVING:
                    target = slideMenuData.menuBrand.living;
                    break;
            }

            List<SlideMenuData.MenuBrandChild> selectDummy = new ArrayList<>();
            List<SlideMenuData.MenuBrandChild> noneSelectDummy = new ArrayList<>();

            for(int i=0; i<target.size(); i++){

                if(target.get(i).brndHallCd != null){

                    boolean isSelect = false;

                   for( String s : slideMenuData.menuBrand.myBrand){

                       if(target.get(i).brndHallCd.equalsIgnoreCase(s)){
                           isSelect = true;
                           break;
                       }
                   }
                    if(isSelect){
                       selectDummy.add(target.get(i));
                    }

                    else{
                       noneSelectDummy.add(target.get(i));
                    }
                }

                else
                    noneSelectDummy.add(target.get(i));

            }

            List<SlideMenuData.MenuBrandChild> sortTarget = new ArrayList<>();
            sortTarget.addAll(selectDummy);
            sortTarget.addAll(noneSelectDummy);

            if(page == SlideBrandSubAdapter.BRAND_FASHION)
                SiApplication.get().getSlideMenuData().menuBrand.fashion = sortTarget;
            else if(page == SlideBrandSubAdapter.BRAND_BEAUTY)
                SiApplication.get().getSlideMenuData().menuBrand.beauty = sortTarget;
            else if(page == SlideBrandSubAdapter.BRAND_LIVING)
                SiApplication.get().getSlideMenuData().menuBrand.living = sortTarget;

            return SiApplication.get().getSlideMenuData();
    }

    public static void setEachWeightForTabsItem(int pos, TabLayout tl) {
            String length = tl.getTabAt(pos).getText().toString().replace(" ", "");
            int weight = length.length();
            if (weight < 3) {
                weight += 1;
            }
            ViewGroup slidingTabStrip = (ViewGroup) tl.getChildAt(0);
            View tab = slidingTabStrip.getChildAt(pos);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.weight = weight;
            tab.setLayoutParams(layoutParams);
        }

        //G.MY_JSESSION_MO_ID = cookies;

    public static boolean onCalledNewBrowser(Context mCon, String strUrl) {
        if (strUrl.startsWith("siecmobile://blink?targetUrl=") ||
                strUrl.startsWith("SIECMobile://blink?targetUrl=")) {
            Uri mUri = Uri.parse(strUrl);
            String strTargetUrl = mUri.getQueryParameter("targetUrl");
            if (strTargetUrl == null
                    || strTargetUrl.isEmpty()
                    || !(strTargetUrl.length() > 0)) {
            }
            else {
                mCon.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(strTargetUrl)));
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static synchronized String gettingPriceText(Context context, int price, int discountPrice, boolean isBr, boolean isStaffShowing, String lightColor, String darkColor) {
        String price_ = null;
        String br, staffFlag;
        if (isBr) {
            br = "<BR>";
        }
        else {
            br = "";
        }

       if (SiApplication.get().getUserInfoData() != null && SiApplication.get().getUserInfoData().userInfo != null
                && SiApplication.get().getUserInfoData().userInfo.empYn != null &&SiApplication.get().getUserInfoData().userInfo.empYn.equalsIgnoreCase("Y")) {
            if (isStaffShowing) {
                staffFlag = context.getString(R.string.staff_price_namespace);
            }
            else {
                staffFlag = "";
            }
        }
        else {
            staffFlag = "";
        }

        if (discountPrice == price) {
            price_ = "<font color='" + darkColor + "'>" +
                    context.getString(R.string.price_token, Utils.numberSetComma(discountPrice))
                    + "</font>";
        }
        else {
            price_ = "<font color='" + lightColor + "'>" +
                    context.getString(R.string.price_token, Utils.numberSetComma(price))
                    + "</font>" + br
                    + "<font color='" + darkColor + "'> / " + staffFlag + " " +
                    context.getString(R.string.price_token, Utils.numberSetComma(discountPrice))
                    + "</font>";
        }

        return price_;

    }
    public static String numberSetComma(int n) {
        String str = String.valueOf(n);
        Double d = Math.ceil(Math.round(Double.parseDouble(str)));
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(d);
    }

    public synchronized static boolean resizedImageView(ImageView iv) {
        if (iv == null) {
            return false;
        }

        iv.requestLayout();

        int width = iv.getMeasuredWidth();
        int height = iv.getMeasuredHeight();
        int line = 0;
        if (width > 0) {
            line = width;
        }
        else if (height > 0) {
            line = height;
        }
        else {
            return false;
        }

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) iv.getLayoutParams();
        params.width = (int) (line * 0.85);
        params.height = (int) (line * 0.85);
        iv.setLayoutParams(params);
        return true;
    }

    private static AlertDialog commonAlert;

    public static void alert(Context ctx, String msg, String poBtn,
                             final View.OnClickListener poListener, String neBtn, final View.OnClickListener neListener) {

        if (commonAlert != null && commonAlert.isShowing()) {
            try {
                commonAlert.dismiss();
            }
            catch (Exception e) {

            }
        }

        View v = LayoutInflater.from(ctx).inflate(R.layout.common_alert, null);
        final CommonAlertBinding b = DataBindingUtil.bind(v);
        AlertDialog.Builder builder;

//        if (t != null) ab.setTitle(t);
        if (msg != null) b.tvMsg.setText(msg);

        builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(false);

        if (poBtn != null) {
            b.flPositive.setVisibility(View.VISIBLE);
            b.btPositive.setText(poBtn); //버튼 글씨
            b.btPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (poListener != null) {
                        b.btPositiveDummy.performClick();
                    }
                    commonAlert.dismiss();
                }
            });
            b.btPositiveDummy.setOnClickListener(poListener); //버튼 사각형 배경
        }

        if (neBtn != null) {
            b.flNegative.setVisibility(View.VISIBLE);
            b.btNegative.setText(neBtn);
            b.btNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (neListener != null) {
                        b.btNegativeDummy.performClick();
                    }
                    commonAlert.dismiss();
                }
            });
            b.btNegativeDummy.setOnClickListener(neListener);
        }

        builder.setView(b.getRoot());
        commonAlert = builder.create();

        commonAlert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                commonAlert.dismiss();
            }
        });

        try {
            commonAlert.show();
        }
        catch (Exception e) {

        }
    }

    public static boolean getMyNetworkStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (null != activeNetwork) {
                switch (activeNetwork.getType()) {
                    case ConnectivityManager.TYPE_MOBILE:
                    case ConnectivityManager.TYPE_WIFI:
                        return true;
                }
            }
        }

        return false;
    }
    
    
    public static void userLogout(Context context){
        onUserLogout(context, true);
    }

    private static void onUserLogout(Context context, boolean isFinish) {

        G.MY_JSESSION_MO_ID = null;
        SiApplication.get().setUserInfoData(null);
        G.PW_TOKEN = null;
        G.ENC_MBR_NO = null;
        SiApplication.get().api = Api.rf.create(Api.class);

        if(SiApplication.get().getBasketCountData() != null){
            SiApplication.get().getBasketCountData().cartCount = 0;
        }

        String userInputId = CookiesManager.getCookieForName(CookiesManager.USER_INPUT_ID);
        userInputId = userInputId == null ? "" : userInputId;

        CookiesManager.removeAllCookies(); //로그아웃 시 쿠키 다 삭제

        Preferences.removeSharedPreferences(context, Preferences.MBR_NO);
        Preferences.removeSharedPreferences(context, Preferences.PW_TOKEN);
        Preferences.removeSharedPreferences(context, Preferences.ENC_MBR_NO);

        //로그아웃하면 쿠키정보가 달랑 이 두개 밖에 없다.
        CookiesManager.setCookieForName(CookiesManager.ENC_MBR_NO, "");
        CookiesManager.setCookieForName(CookiesManager.USER_INPUT_ID, userInputId);

        G.USER_LOGIN = false;

        if (!(context instanceof SplashActivity) && isFinish) {
            if (context instanceof MainActivity) {

            }
            else {
                ((Activity) context).finish();
            }
        }

    }


}


