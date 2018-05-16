package com.si.simembers.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;


import com.si.simembers.Api.ApiCallBack;
import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Api.Model.UserInfoData;
import com.si.simembers.Api.Urls;
import com.si.simembers.Base.BaseActivity;
import com.si.simembers.Common.G;
import com.si.simembers.Common.Schemes;
import com.si.simembers.Common.Utils;
import com.si.simembers.Fragment.EditorFragment;
import com.si.simembers.Fragment.EventFragment;
import com.si.simembers.Fragment.HomeFragment;
import com.si.simembers.Fragment.ImageBannerFragment;
import com.si.simembers.Fragment.MagazineFragment;
import com.si.simembers.Fragment.NowFragment;
import com.si.simembers.Fragment.OutletFragment;
import com.si.simembers.Fragment.PlanFragment;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Value.RequestCodes;
import com.si.simembers.Widget.CenterLayoutManager;
import com.si.simembers.Widget.GnbMenuRecyclerAdapter;
import com.si.simembers.Widget.MainPagerAdapter;
import com.si.simembers.Widget.VerticalOnlyNestedScrollView;
import com.si.simembers.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    public static final String GO_TO_THE_HOME = "goToTheHome";
    public static final String USER_LOGIN_SUCC = "userLoginSucc";
    public static final String HOME_NOTIFY = "homeNotify";
    private ActivityMainBinding binding;

    private static VerticalOnlyNestedScrollView sNestedScrollView;

    private GnbMenuRecyclerAdapter gnbMenuRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Fragment> fragmentList= new ArrayList<>();
    private TabInfoData tabInfoData;
    private MainPagerAdapter mainPagerAdapter;

    private Handler pageHandler = new Handler();
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabInfoData = SiApplication.get().getTabInfoData();


        //홈 배너이
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        IntentFilter filterLogin = new IntentFilter();
        filterLogin.addAction(USER_LOGIN_SUCC);
        registerReceiver(broadUserLoginSucc, filterLogin);

        IntentFilter filterHomeNotify = new IntentFilter();
        filterHomeNotify.addAction(HOME_NOTIFY);
        registerReceiver(broadHomeNotify, filterHomeNotify);

        mLayoutManager = new CenterLayoutManager(this);
        mLayoutManager.setOrientation(CenterLayoutManager.HORIZONTAL);
        binding.rvTopTab.setLayoutManager(mLayoutManager);
        gnbMenuRecyclerAdapter = new GnbMenuRecyclerAdapter(SiApplication.get().getTabInfoData(), onMainTopAdapterCallBack, this);
        binding.rvTopTab.setAdapter(gnbMenuRecyclerAdapter);
        binding.rvTopTab.scrollToPosition(gnbMenuRecyclerAdapter.getCenterPos());//3500에 해놓고
        binding.rvTopTab.smoothScrollToPosition(gnbMenuRecyclerAdapter.getCenterPos());//가운데로 맞추기



        mainPagerAdapter = new MainPagerAdapter(this, LayoutInflater.from(this), onNestedScrollViewManagementListener);
        binding.pagerMain.setAdapter(mainPagerAdapter);
        binding.pagerMain.addOnPageChangeListener(this);
        binding.pagerMain.setCurrentItem(mainPagerAdapter.getCenterPos());


        init();


    }

    private int getSchemeToPosition(String scheme){   //아울렛 예 ) siecmobile://main?tab=outlet

        Uri query = Uri.parse(scheme);
        String tab = query.getQueryParameter(Schemes.QUERY_TAB);

        for(TabInfoData.Tabs t : SiApplication.get().getTabInfoData().tabsInfo){
            Uri queryTemp = Uri.parse(t.scheme);
            String tabTemp = queryTemp.getQueryParameter(Schemes.QUERY_TAB);
            if(tab.startsWith(tabTemp)){
                return t.index;
            }


        }


        return 0;
    }

    private BroadcastReceiver broadHomeNotify = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mainPagerAdapter.getHome().refreshApiHome();
        }
    };

    @Override
    protected void onMenuSelected(String scheme) {
        int current = gnbMenuRecyclerAdapter.getRealPos(binding.pagerMain.getCurrentItem());
        int target = getSchemeToPosition(scheme);
        int loopPos = binding.pagerMain.getCurrentItem();

        if(current != target){
            goingTargetView(getTargetViewPosition(current, target, loopPos));
        }

    }



    private void init() {

        Message msg = recentProdHandler.obtainMessage();
        recentProdHandler.sendMessage(msg);

        setGnb(binding);
        setMenuDrawer(binding);
    }


    private int getTargetViewPosition(int current, int target, int loop) {
        int divide = target - current;
        if (tabInfoData != null && tabInfoData.tabsInfo != null) {
            int limit = tabInfoData.tabsInfo.size() - 1;
            if (divide == limit) {
                // first->last
                return binding.pagerMain.getCurrentItem() - 1;
            }
            else if (divide == -limit) {
                // last->first
                return binding.pagerMain.getCurrentItem() + 1;
            }
        }
        return loop + divide;
    }

    private void goToTheHome() {
        int current = mainPagerAdapter.getRealPos(binding.pagerMain.getCurrentItem());
        int target = getSchemeToPosition(Schemes.getFullScheme(Schemes.HOME));
        int loopPos = binding.pagerMain.getCurrentItem();
        if (current != target) {
            goingTargetView(getTargetViewPosition(current, target, loopPos));
        }

       /* if (sNestedScrollView != null) {
            sNestedScrollView.smoothScrollTo(0, 0);
        }*/
    }


    private void goingTargetView(final int target) {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int start = binding.pagerMain.getCurrentItem();
                if (start < target) {
                    start = start + 1;
                }
                else if (start > target) {
                    start = start - 1;
                }
                else {
                    this.cancel();
                }
                goingTargetView_(start);
            }
        };
        timer.schedule(task, 0, 10);
    }

    protected void goingTargetView_(final int target) {
        Runnable updater = new Runnable() {
            public void run() {
                binding.pagerMain.setCurrentItem(target, true);
            }
        };
        pageHandler.post(updater);
    }


    private GnbMenuRecyclerAdapter.onMainTopAdapterCallBack onMainTopAdapterCallBack =
    new GnbMenuRecyclerAdapter.onMainTopAdapterCallBack() {
        @Override
    public void onClickTab(int position) {

        int target = position % tabInfoData.tabsInfo.size();
        int current = mainPagerAdapter.getRealPos(binding.pagerMain.getCurrentItem());
        int loopPos = binding.pagerMain.getCurrentItem();
        binding.rvTopTab.smoothScrollToPosition(position);

        if (current != target) {
            int movePos = getTargetViewPosition(current, target, loopPos);
            goingTargetView(movePos);
        }

    /*       int divide = target - current;

          if(target - current == 6)
              binding.pagerMain.setCurrentItem(loopPos - 1);
          else if(target - current == -6)
              binding.pagerMain.setCurrentItem(loopPos + 1);
          else
              binding.pagerMain.setCurrentItem(loopPos + divide);*/

        }
    };

    @Override
    public void onPageSelected(int position) {

        currentPage = position % tabInfoData.tabsInfo.size();
        int movePos = mainPagerAdapter.getCenterPos() + currentPage;
        binding.rvTopTab.scrollToPosition(movePos);
        binding.rvTopTab.smoothScrollToPosition(movePos);
        gnbMenuRecyclerAdapter.selected = currentPage;
        gnbMenuRecyclerAdapter.isAnimState = false; //밑줄애니메이션 딱한번만!
        gnbMenuRecyclerAdapter.notifyDataSetChanged();
        //  gnbMenuRecyclerAdapter.;


        binding.pagerMain.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        //페이저의 변경이 일어날 경우 그리고 그 페이저가 다 그려졌을 경우 -> 페이저의 높이를 설정한다...

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }




    private void setChildViewCustomHeight(){
        mainPagerAdapter.getHome().setHomeBestSellingHeight();
    }

    private final Handler tabNotifyHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setChildViewCustomHeight();
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() { //전체 뷰가 그려질 때
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                binding.pagerMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            else {
                binding.pagerMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            // To Do
            if (tabNotifyHandler.hasMessages(0)) {
                tabNotifyHandler.removeMessages(0);
            }

            Message tabNotifyMessage = tabNotifyHandler.obtainMessage();
            tabNotifyMessage.what = 0;
            tabNotifyHandler.sendMessageDelayed(tabNotifyMessage, 200);
        }
    };

    private BroadcastReceiver broadUserLoginSucc = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {//destroy꼭해줘야함

            G.USER_LOGIN = true;

            new ApiCallBack(MainActivity.this, loginAfterCallback).execute(SiApplication.get().api.getUserInfo(Urls.APP + Urls.USER_INFO, G.MBR_NO, G.PW_TOKEN));

            new ApiCallBack(MainActivity.this, loginAfterCallback).execute(SiApplication.get().api.getSlideMenu(Urls.APP + Urls.SLIDE_MENU, G.MBR_NO, G.PW_TOKEN));






        }
    };

    private void onUserLogInOut(){
        goingTargetView(gnbMenuRecyclerAdapter.getCenterPos());
        if(G.USER_LOGIN){

        }
        else
        {
            Utils.userLogout(this);

        }


    }


    private ApiCallBack.RetrofitCallback loginAfterCallback = new ApiCallBack.RetrofitCallback() {
        @Override
        public void onSucc(Response response, String url) throws Exception {

            Toast.makeText(MainActivity.this, "도달", Toast.LENGTH_SHORT).show();
            if(url.contains(Urls.USER_INFO)){

                UserInfoData userInfoData = (UserInfoData)response.body();
                SiApplication.get().setUserInfoData(userInfoData);

                String member = "A"; //손님들은 A
                if(userInfoData != null && userInfoData.userInfo != null)
                {
                    member = "B"; // 직원인 경우는 B

                }

            }

            else if (url.contains(Urls.SLIDE_MENU)){
                SiApplication.get().setSlideMenuData((SlideMenuData)response.body());
                Utils.finalSortMyBrand();
                setMenuDrawer(binding);
            }

        }



        @Override
        public void onFail(Response response, String url) throws Exception {

            Toast.makeText(MainActivity.this, "실패", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(Call call, Throwable t, String url) throws Exception {

            Toast.makeText(MainActivity.this, "에러", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {

        if (isMenuDrawerOpened()) {
            closeMenu();
        }
        else {
            if (!TextUtils.isEmpty(G.MAIN_CURRENT_SCHEME) &&
                    G.MAIN_CURRENT_SCHEME.startsWith(Schemes.getFullScheme(Schemes.HOME))) {
                Utils.alert(MainActivity.this,getString(R.string.msg_app_finish), getString(R.string.btn_positive)
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.super.onBackPressed();
                            }
                        }, getString(R.string.btn_negative), null);
            }
            else {
                goToTheHome();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RequestCodes.WEBVIEW_ACTIVITY:
                if(resultCode == BaseWebViewActivity.RESULT_USER_LOGOUT)
                {
                    G.USER_LOGIN = false;
                    onUserLogInOut();
                }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMenuDrawer(binding);
    }

    @Override
    protected void onDestroy() {

        if(G.isAlive)
        {
            unregisterReceiver(broadUserLoginSucc);
            unregisterReceiver(broadHomeNotify);
        }
        super.onDestroy();
    }

    public void scrollToBottom() {
        Handler han = new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {

                int scroll = sNestedScrollView.getChildAt(0).getMeasuredHeight() - sNestedScrollView.getMeasuredHeight();
                sNestedScrollView.smoothScrollTo(0, scroll);


            }
        },200);
    }

    public NestedScrollView.OnScrollChangeListener getNestedScrollViewCallback() {

        return onNestedScrollViewManagementListener;

    }


    private NestedScrollView.OnScrollChangeListener onNestedScrollViewManagementListener = new NestedScrollView.OnScrollChangeListener() {

        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            sNestedScrollView = (VerticalOnlyNestedScrollView) v;
            sNestedScrollView.setAppbarLayer(binding.ablGnbArea);


            //미구현

        }
    };
}

























