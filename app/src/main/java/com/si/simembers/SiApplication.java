package com.si.simembers;

import android.support.v4.app.Fragment;

import com.si.simembers.Api.Api;
import com.si.simembers.Api.Model.BasketCountData;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Api.Model.RecentProdData;
import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Api.Model.UserInfoData;
import com.si.simembers.Fragment.HomeFragment;
import com.si.simembers.Fragment.ImageBannerFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by byun on 2018-03-16.
 */

public class SiApplication {

    private static SiApplication sApplication;

    public static SiApplication get() { return sApplication; }

    public static Api api;

    private RecentProdData recentProdData;

    private BasketCountData basketCountData;

    private UserInfoData userInfoData;

    private SlideMenuData slideMenuData;

    private TabInfoData tabInfoData;

    private MainHomeTabData homeTabData;

    public SiApplication(){
        super();
        sApplication = this;
    }

    private ArrayList<ImageBannerFragment> HomeBannerList;

    public ArrayList<ImageBannerFragment> getHomeBannerList() {
        return HomeBannerList;
    }

    public void setHomeBannerList(ArrayList<ImageBannerFragment> HomeBannerList)
    {
        this.HomeBannerList = HomeBannerList;
    }

    public BasketCountData getBasketCountData() {
        return basketCountData;
    }

    public void setBasketCountData(BasketCountData basketCountData) {
        this.basketCountData = basketCountData;
    }

    public UserInfoData getUserInfoData() {
        return userInfoData;
    }

    public void setUserInfoData(UserInfoData userInfoData) {
        this.userInfoData = userInfoData;
    }

    public SlideMenuData getSlideMenuData() {
        return slideMenuData;
    }

    public void setSlideMenuData(SlideMenuData slideMenuData) {
        this.slideMenuData = slideMenuData;
    }



    public TabInfoData getTabInfoData() {
        return tabInfoData;
    }

    public void setTabInfoData(TabInfoData tabInfoData) {
        this.tabInfoData = tabInfoData;
    }

    public MainHomeTabData getHomeTabData() {
        return homeTabData;
    }

    public void setHomeTabData(MainHomeTabData homeTabData) {
        this.homeTabData = homeTabData;
    }

    public void setRecentProdData(RecentProdData recentProdData){
        this.recentProdData = recentProdData;
    }

    public RecentProdData getRecentProdData(){
        return this.recentProdData;

    }


















}
