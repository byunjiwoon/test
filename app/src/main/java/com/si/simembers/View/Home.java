package com.si.simembers.View;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.si.simembers.Activity.MainBannerMoreActivity;
import com.si.simembers.Adapter.MainHomeBestsellingItemsAdapter;
import com.si.simembers.Adapter.MainHomeRecommendBrandAdapter;
import com.si.simembers.Adapter.MainHomeStaffBannerAdapter;
import com.si.simembers.Api.ApiCallBack;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Api.Urls;
import com.si.simembers.Base.BaseView;
import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Widget.HomeBannerPagerAdapter;
import com.si.simembers.Widget.ResizingViewPager;
import com.si.simembers.databinding.IncludeHomeBestsellingBinding;
import com.si.simembers.databinding.IncludeHomeMyBrandBinding;
import com.si.simembers.databinding.IncludeHomeRecommendBrandBinding;
import com.si.simembers.databinding.IncludeHomeStaffBannerBinding;
import com.si.simembers.databinding.ViewMenuHomeBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by byun on 2018-04-07.
 */

public class Home extends BaseView implements View.OnClickListener, ApiCallBack.RetrofitCallback{

    public ViewMenuHomeBinding b;


    private Context context;
    private LayoutInflater inflater;

    private View v;

    //홈배너
    private View include;
    private ResizingViewPager bannerPager;
    private HomeBannerPagerAdapter homeBannerPagerAdapter;

    private IncludeHomeBestsellingBinding includeHomeBestsellingBinding;
    private IncludeHomeRecommendBrandBinding includeHomeRecommendBrandBinding;
    private IncludeHomeStaffBannerBinding includeHomeStaffBannerBinding;
    private IncludeHomeMyBrandBinding includeHomeMyBrandBinding;

    private MainHomeStaffBannerAdapter mainHomeStaffBannerAdapter;
    private MainHomeBestsellingItemsAdapter mainHomeBestsellingItemsAdapter;
    private MainHomeRecommendBrandAdapter mainHomeRecommendBrandAdapter;
    private MainHomeTabData mainHomeTabData;

    private Handler autoScrollingBannerHandler;

    public Home(Context context, LayoutInflater inflater,  NestedScrollView.OnScrollChangeListener scCallback) {
        super(context);

        mainHomeTabData = SiApplication.get().getHomeTabData();
        this.inflater = inflater;

        this.context = context;
        v = inflater.inflate(R.layout.view_menu_home, null, true);
        b = ViewMenuHomeBinding.bind(v);

        loadingTabFooter(b);

        init();

        autoScrollingBannerHandler = new Handler();
        autoScrollingBannerHandler.post(mRunnable);

        if(scCallback != null)
            b.scMain.setOnScrollChangeListener(scCallback);

    }


    private void init() {

        settingNormalBanner();
        settingHomeCategory();
        settingStaffBanner();
        settingBestSelling();
        settingRecommendBrand();
        settingMyBrand();
    }

    private void settingNormalBanner() {
        include = b.getRoot().findViewById(R.id.include_home_normal_banner);
        final TextView title = (TextView)include.findViewById(R.id.tvHomeNormalBannerTitle);
        final TextView desc = (TextView)include.findViewById(R.id.tvHomeNormalBannerDesc);
        final TextView pagingCnt = (TextView)include.findViewById(R.id.tvHomeNormalBannerPagingCnt);
        ImageButton nextBtn = (ImageButton)include.findViewById(R.id.btHomeNormalBannerNext);
        ImageButton prevBtn = (ImageButton)include.findViewById(R.id.btHomeNormalBannerPrev);
        ImageButton moreBtn = (ImageButton)include.findViewById(R.id.btHomeNormalBannerMore);


        bannerPager = (ResizingViewPager) include.findViewById(R.id.rvpHomeNormalBanner);


        title.setText(mainHomeTabData.normalBanner.get(0).txt);
        desc.setText(mainHomeTabData.normalBanner.get(0).subTxt);
        pagingCnt.setText(mainHomeTabData.normalBanner.get(0).index+1+"/"+mainHomeTabData.normalBanner.size());

        bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {

                title.setText(mainHomeTabData.normalBanner.get(i%mainHomeTabData.normalBanner.size()).txt);
                desc.setText(mainHomeTabData.normalBanner.get(i%mainHomeTabData.normalBanner.size()).subTxt);
                pagingCnt.setText(mainHomeTabData.normalBanner.get(i%mainHomeTabData.normalBanner.size()).index+1+"/"+mainHomeTabData.normalBanner.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        homeBannerPagerAdapter =
                new HomeBannerPagerAdapter(LayoutInflater.from(context), mainHomeTabData.normalBanner);
        bannerPager.setAdapter(homeBannerPagerAdapter);
        bannerPager.setCurrentItem(homeBannerPagerAdapter.getCenterPos());

        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
    }

    private void settingRecommendBrand() {
        includeHomeRecommendBrandBinding = b.includeHomeRecommendBrand;
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

    private void settingBestSelling() {

        includeHomeBestsellingBinding = b.includeHomeBestselling;
        // Binding.in
        if( includeHomeBestsellingBinding.tabHomeBestsellingItems.getTabCount() > 0)
            includeHomeBestsellingBinding.tabHomeBestsellingItems.removeAllTabs();

        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(context.getString(R.string.tabs_home_category_women_fashion)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(context.getString(R.string.tabs_home_category_man_fashion)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(context.getString(R.string.tabs_home_category_others)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(context.getString(R.string.tabs_home_category_kids)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(context.getString(R.string.tabs_home_category_beauty)));
        includeHomeBestsellingBinding.tabHomeBestsellingItems
                .addTab(includeHomeBestsellingBinding.tabHomeBestsellingItems.newTab()
                        .setText(context.getString(R.string.tabs_home_category_living)));

        for (int i = 0; i < includeHomeBestsellingBinding.tabHomeBestsellingItems.getTabCount(); i++) {
            Utils.setEachWeightForTabsItem(i, includeHomeBestsellingBinding.tabHomeBestsellingItems);
        }

        mainHomeBestsellingItemsAdapter
                = new MainHomeBestsellingItemsAdapter(context, inflater, mainHomeTabData.bestSelling
                , onMainHomeBestsellingItemsCallback);



        includeHomeBestsellingBinding.nvpHomeBestSelling.setAdapter(mainHomeBestsellingItemsAdapter);




    /*    includeHomeBestsellingBinding.nvpHomeBestSelling // viewpager.addOnPageChangeListener(onPageChangeListener)
                .addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(includeHomeBestsellingBinding.tabHomeBestsellingItems));
*/
        includeHomeBestsellingBinding.nvpHomeBestSelling.setOffscreenPageLimit(10);

        includeHomeBestsellingBinding.nvpHomeBestSelling.setSwipingEnabled(false);




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



    public void setHomeBestSellingHeight(){

        bannerPager.setChildHeight(homeBannerPagerAdapter
                        .getView(bannerPager.getCurrentItem()));

        includeHomeBestsellingBinding.nvpHomeBestSelling.setChildHeight(
                mainHomeBestsellingItemsAdapter.getHomeBestSellingItemsRecyclerView(includeHomeBestsellingBinding.tabHomeBestsellingItems.getSelectedTabPosition()));
        //리사이클러뷰의 사이즈만큼 높이

        includeHomeStaffBannerBinding.rvpHomeStaffBanner
                .setChildHeight(mainHomeStaffBannerAdapter
                        .getView(includeHomeStaffBannerBinding.rvpHomeStaffBanner.getCurrentItem()));

    }

    private MainHomeBestsellingItemsAdapter.onMainHomeBestsellingItemsCallback onMainHomeBestsellingItemsCallback
            = new MainHomeBestsellingItemsAdapter.onMainHomeBestsellingItemsCallback() {
        @Override
        public void onClickItem(int pageType, int itemPos) {

        }
    };


    private void settingMyBrand() {

        includeHomeMyBrandBinding = b.includeHomeMyBrand;

        if (G.USER_LOGIN && mainHomeTabData.myBrand != null && mainHomeTabData.myBrand.size() > 0) {
            includeHomeMyBrandBinding.includeHomeMyBrand.setVisibility(View.VISIBLE);
        } else {
            mainHomeTabData.myBrand = new ArrayList<>();
            includeHomeMyBrandBinding.includeHomeMyBrand.setVisibility(View.GONE);
        }
    }

    private void settingStaffBanner() {

        includeHomeStaffBannerBinding = b.includeHomeStaffBanner;
        mainHomeStaffBannerAdapter = new MainHomeStaffBannerAdapter(LayoutInflater.from(context), mainHomeTabData.staffBanner, onMainHomeStaffBannerCallback, context);
        includeHomeStaffBannerBinding.rvpHomeStaffBanner.setAdapter(mainHomeStaffBannerAdapter);
        includeHomeStaffBannerBinding.rvpHomeStaffBanner.setCurrentItem(mainHomeStaffBannerAdapter.getCenterPos());
        includeHomeStaffBannerBinding.rvpHomeStaffBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            includeHomeStaffBannerBinding.rvpHomeStaffBanner
                .setChildHeight(mainHomeStaffBannerAdapter.getView(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      //  setOnDispatchTouchListener(context, includeHomeStaffBannerBinding.rvpHomeStaffBanner);

        if (mainHomeTabData != null && mainHomeTabData.staffBanner != null && mainHomeTabData.staffBanner.size() > 0) {
            includeHomeStaffBannerBinding.includeHomeNormalBanner.setVisibility(View.VISIBLE);
  /*          Message planUpdatingMsg = staffBannerHandler.obtainMessage();
            planUpdatingMsg.what = 0;
            staffBannerHandler.sendMessageDelayed(planUpdatingMsg, 100);*/

            if (mainHomeTabData.staffBanner.size() <= 1) {
                includeHomeStaffBannerBinding.btHomeStaffBannerPrev.setVisibility(View.GONE);
                includeHomeStaffBannerBinding.btHomeStaffBannerNext.setVisibility(View.GONE);

                includeHomeStaffBannerBinding.rvpHomeStaffBanner.setSwipingEnabled(false);
            } else {
                includeHomeStaffBannerBinding.btHomeStaffBannerPrev.setVisibility(View.VISIBLE);
                includeHomeStaffBannerBinding.btHomeStaffBannerNext.setVisibility(View.VISIBLE);

                includeHomeStaffBannerBinding.rvpHomeStaffBanner.setSwipingEnabled(true);
            }
        } else {
            includeHomeStaffBannerBinding.includeHomeNormalBanner.setVisibility(View.GONE);
        }

    }

    final Handler staffBannerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            includeHomeStaffBannerBinding.rvpHomeStaffBanner.setCurrentItem(0);
        }
    };

    private void settingHomeCategory() {

    }

    public View getView() {
        return v;
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

    private MainHomeStaffBannerAdapter.onMainHomeStaffBannerCallback onMainHomeStaffBannerCallback = new MainHomeStaffBannerAdapter.onMainHomeStaffBannerCallback() {
        @Override
        public void onClickItem(int pos) {
            String url = mainHomeTabData.staffBanner.get(pos).imgUrlMovePath;

        }
    };

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            if(mainHomeTabData == null){

                refreshApiHome();
                return;
            }

            if (homeBannerPagerAdapter != null && mainHomeTabData.normalBanner != null
                    && mainHomeTabData.normalBanner.size() > 1) {
                normalBannerNextPage();
            }


            autoScrollingBannerHandler.postDelayed(mRunnable, 3000);



        }
    };

    public void refreshApiHome(){

        new ApiCallBack(context,this).
                execute(SiApplication.get().api.getMainHomeTab(Urls.APP + Urls.MAIN_HOME_TAB, G.MBR_NO, G.PW_TOKEN));

    }

    @Override
    public void onClick(View view) {

        Intent intent = null;

        if (view == include.findViewById(R.id.btHomeNormalBannerPrev)) {
            normalBannerPrevPage();
        } else if (view == include.findViewById(R.id.btHomeNormalBannerNext)) {
            normalBannerNextPage();
        } else if (view == include.findViewById(R.id.btHomeNormalBannerMore)) {
            intent = new Intent(context, MainBannerMoreActivity.class);
        }

        if(intent != null) context.startActivity(intent);


    }

    private void normalBannerNextPage() {

        bannerPager.setCurrentItem(bannerPager.getCurrentItem()+1, true);

    }

    private void normalBannerPrevPage() {

        bannerPager.setCurrentItem(bannerPager.getCurrentItem()-1, true);

    }

    @Override
    public void onSucc(Response response, String url) throws Exception {

        mainHomeTabData = (MainHomeTabData) response.body();
        init();

    }

    @Override
    public void onFail(Response response, String url) throws Exception {

    }

    @Override
    public void onError(Call call, Throwable t, String url) throws Exception {

    }
}

