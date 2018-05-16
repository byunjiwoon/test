package com.si.simembers.Api;

import android.util.Log;

import com.google.gson.Gson;

import com.si.simembers.Api.Model.BasketCountData;
import com.si.simembers.Api.Model.BrandFavData;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Api.Model.RecentProdData;
import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Api.Model.SplashData;

import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Api.Model.UserInfoData;
import com.si.simembers.Base.BaseActivity;
import com.si.simembers.Base.BaseApplication;
import com.si.simembers.Common.G;
import com.si.simembers.Common.TokenManager;
import com.si.simembers.Module.CookiesManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by dwp on 2017. 5. 31..
 */

public interface Api
{
    String MBR_NO = "mbrNo";
    String PW_TOKEN = "passwd";
    String BRND_HALL_CD = "brndHallCd";
    String BRAND_FAV_FLAG = "uFlag";
    String APP_HASH = "appHash";
    String OUTLET_MORE_TYPE = "chn";
    String PAGE = "currPg";
    String CART_NO = "cartNo";
    String CURRENT_PAGE = "currentPage";
    String HOME_BRAND_TYPE = "brndType";
    String OUTLET_VIEW_NUM = "viewNum";
    String RECENT_PROD_NO = "sivProducts";




    /* 최근 본 상품 */
    @FormUrlEncoded
    @POST
    Call<RecentProdData> getRecentProd(@Url String url, @Field(RECENT_PROD_NO) String recentProdNo, @Field(MBR_NO) String mbrNo, @Field(PW_TOKEN) String pwToken);


    /* slide menu update brand fav */
    @FormUrlEncoded
    @POST
    Call<BrandFavData> setMyFavBrand(@Url String url, @Field(BRND_HALL_CD) String brandCd, @Field(BRAND_FAV_FLAG) String insertFlag, @Field(MBR_NO) String mbrNo, @Field(PW_TOKEN) String pwToken);


    /* get userInfo */
    @FormUrlEncoded
    @POST
    Call<UserInfoData> getUserInfo(@Url String url, @Field(MBR_NO) String mbrNo, @Field(PW_TOKEN) String pwToken);

    /* splash */
    @FormUrlEncoded
    @POST
    Call<SplashData> getSplash(@Url String url, @Field(APP_HASH) String appHash);

    /* splash */
    @FormUrlEncoded
    @POST
    Call<BasketCountData> getBasketCount(@Url String url, @Field(CART_NO) String cartNo, @Field(MBR_NO) String mbrNo, @Field(PW_TOKEN) String pwToken);

    /* slide menu */
    @FormUrlEncoded
    @POST
    Call<SlideMenuData> getSlideMenu(@Url String url, @Field(MBR_NO) String mbrNo, @Field(PW_TOKEN) String pwToken);

    /* main tabs->home */
    @FormUrlEncoded
    @POST
    Call<MainHomeTabData> getMainHomeTab(@Url String url, @Field(MBR_NO) String mbrNo, @Field(PW_TOKEN) String pwToken);



    /* 메인 탭 정보 겟 */
    @GET
    Call<TabInfoData> getTabs(@Url String url);



    Gson gson = new Gson();
    OkHttpClient.Builder http = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    String cookieForMember = "DummyCookie=dummy";
                    if (G.ENC_MBR_NO != null && !G.ENC_MBR_NO.equals("")) {
                        String token = new TokenManager(BaseApplication.getContext()).createToken(G.ENC_MBR_NO);
                        cookieForMember = CookiesManager.ENC_MBR_NO + "=" + token;
                    }

                    if (G.MY_JSESSION_MO_ID != null && !G.MY_JSESSION_MO_ID.equals("")) {
                        cookieForMember = (cookieForMember == null) ? CookiesManager.JSESSIONID_MO + "=" + G.MY_JSESSION_MO_ID
                                : cookieForMember + ";" + CookiesManager.JSESSIONID_MO + "=" + G.MY_JSESSION_MO_ID;
                    }

                    Request request = original.newBuilder()
                            .addHeader("User-Agent", G.APP_SEARCH_AGENT + ":" + G.APP_AGENT + ": AOS:")
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .addHeader("Cookie", cookieForMember)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });

    Retrofit rf = new Retrofit.Builder()
            .baseUrl(Urls.getBase())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(http.build())
            .build();
}
