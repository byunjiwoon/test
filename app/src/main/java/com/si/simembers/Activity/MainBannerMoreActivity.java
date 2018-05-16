package com.si.simembers.Activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.si.simembers.Adapter.HomeBannerMoreAdapter;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Base.BaseActivity;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Widget.HomeBannerPagerAdapter;
import com.si.simembers.databinding.ActivityMainBannerMoreBinding;

import java.util.List;

/**
 * Created by byun on 2018-04-10.
 */

public class MainBannerMoreActivity extends BaseActivity implements View.OnClickListener {



    ActivityMainBannerMoreBinding b;
    private HomeBannerMoreAdapter homeBannerMoreAdapter;
    private List<MainHomeTabData.NormalBanner> bannerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main_banner_more);
        overridePendingTransition(R.anim.slide_bottom_up, 0);

        init();

    }

    private void init() {
        b.btMainBannerMoreFinish.setOnClickListener(this);
        settingList();

    }

    private void settingList() {
        bannerList = SiApplication.get().getHomeTabData().normalBanner;
        homeBannerMoreAdapter = new HomeBannerMoreAdapter(bannerList, callback);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        b.rvMainBannerMore.setLayoutManager(llm);
        b.rvMainBannerMore.setAdapter(homeBannerMoreAdapter);




    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_bottom_down);
    }

    HomeBannerMoreAdapter.onHomeBannerMoreCallback callback = new HomeBannerMoreAdapter.onHomeBannerMoreCallback() {
        @Override
        public void onClickItem(int pos) {
            String url = bannerList.get(pos).imgUrlMovePath;
            Toast.makeText(MainBannerMoreActivity.this, "WebViewActivity 가즈아", Toast.LENGTH_SHORT).show();

            goingPage(url, MainBannerMoreActivity.this);



        }
    };

    @Override
    public void onClick(View view) {
        if(view == b.btMainBannerMoreFinish)
            finish();
    }
}
