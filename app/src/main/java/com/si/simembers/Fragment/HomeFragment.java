package com.si.simembers.Fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.si.simembers.Adapter.MainHomeBestsellingItemsAdapter;
import com.si.simembers.Adapter.MainHomeRecommendBrandAdapter;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Widget.GnbMenuRecyclerAdapter;
import com.si.simembers.Widget.HomeBannerPagerAdapter;
import com.si.simembers.databinding.IncludeHomeBestsellingBinding;
import com.si.simembers.databinding.IncludeHomeMyBrandBinding;
import com.si.simembers.databinding.IncludeHomeRecommendBrandBinding;
import com.si.simembers.databinding.ViewMenuHomeBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byun on 2018-03-19.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{




    public static MainHomeTabData mainHomeTabData;
    private LayoutInflater inflater;
    private ViewGroup container;

    ViewMenuHomeBinding Binding;
    private IncludeHomeBestsellingBinding includeHomeBestsellingBinding;
    private IncludeHomeRecommendBrandBinding includeHomeRecommendBrandBinding;
    private IncludeHomeMyBrandBinding includeHomeMyBrandBinding;
    private MainHomeBestsellingItemsAdapter mainHomeBestsellingItemsAdapter;
    Context context;

    int page;
    private MainHomeRecommendBrandAdapter mainHomeRecommendBrandAdapter;

    public static HomeFragment homeFragment;


    public static HomeFragment newInstance() {

        if (homeFragment == null) {

            homeFragment = new HomeFragment();

            return homeFragment;
        }

        return homeFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        this.context = getActivity();

        Binding = DataBindingUtil.inflate(inflater, R.layout.view_menu_home, container, false);
        return Binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        mainHomeTabData = SiApplication.get().getHomeTabData();
        init();



    }

    /*private ImageBannerFragment.BannerCallBack bannerCallBack =
            new ImageBannerFragment.BannerCallBack() {
                @Override
                public void setBanner() {

                }
            };

*/

    private void init(){

        settingNormalBanner();
        settingHomeCategory();
        settingStaffBanner();
        settingBestSelling();
        settingRecommendBrand();
        settingMyBrand();

    }

    private void settingNormalBanner() {

        final View include = Binding.getRoot().findViewById(R.id.include_home_normal_banner);
        final TextView title = (TextView)include.findViewById(R.id.tvHomeNormalBannerTitle);
        final TextView desc = (TextView)include.findViewById(R.id.tvHomeNormalBannerDesc);
        final TextView pagingCnt = (TextView)include.findViewById(R.id.tvHomeNormalBannerPagingCnt);
        ViewPager bannerPager = (ViewPager)include.findViewById(R.id.rvpHomeNormalBanner);

        title.setText(SiApplication.get().getHomeTabData().normalBanner.get(0).txt);
        desc.setText(SiApplication.get().getHomeTabData().normalBanner.get(0).subTxt);
        pagingCnt.setText(SiApplication.get().getHomeTabData().normalBanner.get(0).index+1+"/"+SiApplication.get().getHomeTabData().normalBanner.size());

        bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {

                title.setText(SiApplication.get().getHomeTabData().normalBanner.get(i).txt);
                desc.setText(SiApplication.get().getHomeTabData().normalBanner.get(i).subTxt);
                pagingCnt.setText(SiApplication.get().getHomeTabData().normalBanner.get(i).index+1+"/"+SiApplication.get().getHomeTabData().normalBanner.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

     //   HomeBannerPagerAdapter homeBannerPagerAdapter = new HomeBannerPagerAdapter(SiApplication.get().getHomeBannerList(), getChildFragmentManager());
    //    bannerPager.setAdapter(homeBannerPagerAdapter);
        bannerPager.setCurrentItem(0);

    }

    private void settingHomeCategory() {

    }

    private void settingStaffBanner() {

    }

    private void settingRecommendBrand() {//HOT BRAND

        includeHomeRecommendBrandBinding = Binding.includeHomeRecommendBrand;
        boolean isRecommandBrandShowing = false;

/*
        if (G.USER_LOGIN && mainHomeTabData.myBrand != null && mainHomeTabData.myBrand.size() > 0) {
            isRecommandBrandShowing = true;
        }
        includeHomeRecommendBrandBinding.llHomeRecommentBrand.setVisibility(isRecommandBrandShowing ? View.VISIBLE : View.GONE);
*/


        mainHomeRecommendBrandAdapter = new MainHomeRecommendBrandAdapter(context, recommendBrandcallback);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        includeHomeRecommendBrandBinding.rvHomeRecommendBrand.setLayoutManager(llm);
        includeHomeRecommendBrandBinding.rvHomeRecommendBrand.setAdapter(mainHomeRecommendBrandAdapter);
        includeHomeRecommendBrandBinding.rvHomeRecommendBrand.setNestedScrollingEnabled(false);
        includeHomeRecommendBrandBinding.footerExpandMore.llExpandMore.setOnClickListener(this);

        if (mainHomeTabData.recommendBrand != null
                && mainHomeTabData.recommendBrand.size() > 0) {
            includeHomeRecommendBrandBinding.includeHomeRecommendBrand.setVisibility(View.VISIBLE);
        } else {
            includeHomeRecommendBrandBinding.includeHomeRecommendBrand.setVisibility(View.GONE);
        }

        if (!(mainHomeTabData.recommendBrandCnt > 1)) {
            includeHomeRecommendBrandBinding.footerExpandMore.footerExpandMore.setVisibility(View.GONE);
        } else {
            includeHomeRecommendBrandBinding.footerExpandMore.footerExpandMore.setVisibility(View.VISIBLE);
        }


    }

    private MainHomeRecommendBrandAdapter.onMainHomeRecommendBrandCallback recommendBrandcallback
            = new MainHomeRecommendBrandAdapter.onMainHomeRecommendBrandCallback(){

        @Override
        public void onClickBanner(int pos) {

            Toast.makeText(context, "메인클릭", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClickLeftItem(int pos, int itemPos) {

            Toast.makeText(context, "왼쪽", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onClickCenterItem(int pos, int itemPos) {
            Toast.makeText(context, "센터", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onClickRightItem(int pos, int itemPos) {
            Toast.makeText(context, "우측", Toast.LENGTH_SHORT).show();


        }
    };

    private void settingMyBrand() {

        includeHomeMyBrandBinding = Binding.includeHomeMyBrand;

        if (G.USER_LOGIN && mainHomeTabData.myBrand != null && mainHomeTabData.myBrand.size() > 0) {
            includeHomeMyBrandBinding.includeHomeMyBrand.setVisibility(View.VISIBLE);
        } else {
            mainHomeTabData.myBrand = new ArrayList<>();
            includeHomeMyBrandBinding.includeHomeMyBrand.setVisibility(View.GONE);
        }
    }

    private void settingBestSelling() {

      includeHomeBestsellingBinding = Binding.includeHomeBestselling;
       // Binding.in
        if( includeHomeBestsellingBinding.tabHomeBestsellingItems.getTabCount() > 0)
            includeHomeBestsellingBinding.tabHomeBestsellingItems.removeAllTabs();

        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(this.getString(R.string.tabs_home_category_women_fashion)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(this.getString(R.string.tabs_home_category_man_fashion)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(this.getString(R.string.tabs_home_category_others)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(this.getString(R.string.tabs_home_category_kids)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(this.getString(R.string.tabs_home_category_beauty)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(this.getString(R.string.tabs_home_category_living)));

        for (int i = 0; i < includeHomeBestsellingBinding.tabHomeBestsellingItems.getTabCount(); i++) {
            Utils.setEachWeightForTabsItem(i, includeHomeBestsellingBinding.tabHomeBestsellingItems);
        }

        mainHomeBestsellingItemsAdapter
                = new MainHomeBestsellingItemsAdapter(context, inflater, SiApplication.get().getHomeTabData().bestSelling
                , onMainHomeBestsellingItemsCallback);

        includeHomeBestsellingBinding.nvpHomeBestSelling.setAdapter(mainHomeBestsellingItemsAdapter);

    /*    includeHomeBestsellingBinding.nvpHomeBestSelling // viewpager.addOnPageChangeListener(onPageChangeListener)
                .addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(includeHomeBestsellingBinding.tabHomeBestsellingItems));
*/
        includeHomeBestsellingBinding.nvpHomeBestSelling.setOffscreenPageLimit(10);

        includeHomeBestsellingBinding.nvpHomeBestSelling.setSwipingEnabled(false);

        includeHomeBestsellingBinding.nvpHomeBestSelling.setChildHeight(
                mainHomeBestsellingItemsAdapter.getHomeBestSellingItemsRecyclerView(0));//리사이클러뷰의 사이즈만큼 높이

        includeHomeBestsellingBinding.tabHomeBestsellingItems.addOnTabSelectedListener(onBestSellingTabListener);

        includeHomeBestsellingBinding.tabHomeBestsellingItems.getTabAt(0).select();

        if (SiApplication.get().getHomeTabData().bestSelling.size() > 0
                && SiApplication.get().getHomeTabData().bestSelling.get(0).moreBestItemUrl != null
                && !SiApplication.get().getHomeTabData().bestSelling.get(0).moreBestItemUrl.isEmpty()) {
            includeHomeBestsellingBinding.footer.footerArrowMore.setVisibility(View.VISIBLE);
        } else {
            includeHomeBestsellingBinding.footer.footerArrowMore.setVisibility(View.GONE);
        }






    }

    private MainHomeBestsellingItemsAdapter.onMainHomeBestsellingItemsCallback onMainHomeBestsellingItemsCallback
            = new MainHomeBestsellingItemsAdapter.onMainHomeBestsellingItemsCallback() {
        @Override
        public void onClickItem(int pageType, int itemPos) {

        }
    };



    @Override
    public void onClick(View v) {

        if(v == includeHomeBestsellingBinding.footer.llArrowFooter){
            //웹액티비티 처리..
        }

    }

    private TabLayout.OnTabSelectedListener onBestSellingTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            MainHomeTabData mainHomeTabData = SiApplication.get().getHomeTabData();

            Log.d("byun", "tab.getPosition(): " + tab.getPosition());

            if (mainHomeTabData.bestSelling.size() > 0
                    && mainHomeTabData.bestSelling.get(tab.getPosition()).moreBestItemUrl != null
                    && !mainHomeTabData.bestSelling.get(tab.getPosition()).moreBestItemUrl.isEmpty()) {
                includeHomeBestsellingBinding.footer.footerArrowMore.setVisibility(View.VISIBLE);
            } else {
                includeHomeBestsellingBinding.footer.footerArrowMore.setVisibility(View.GONE);
            }

            includeHomeBestsellingBinding.nvpHomeBestSelling.setCurrentItem(tab.getPosition());

       /*     includeHomeBestsellingBinding.nvpHomeBestSelling
                    .setChildHeight(mainHomeBestsellingItemsAdapter.getHomeBestSellingItemsRecyclerView(tab.getPosition()));*/
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}

























