package com.si.simembers.View;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Common.Schemes;
import com.si.simembers.R;
import com.si.simembers.databinding.HeaderMenuDrawerBinding;

public class MenuDrawerHeader implements View.OnClickListener {


   private HeaderMenuDrawerBinding b;

    private View v;
    private Context context;
    private MenuHeaderCallBack menuHeaderCallBack;
    private TabInfoData data;

    public MenuDrawerHeader(Context context, TabInfoData data, MenuHeaderCallBack menuHeaderCallBack) {
        this.context = context;
        this.menuHeaderCallBack = menuHeaderCallBack;
        this.data = data;
        v = LayoutInflater.from(context).inflate(R.layout.header_menu_drawer, null);
        b = HeaderMenuDrawerBinding.bind(v);

        init();

    }

    public void setVisibleBrandTabs(int visible){

        b.llBrandDetail.setVisibility(visible);

    }
    private void init() {
        b.btnMenuClose.setOnClickListener(this);
        b.btnMenuMyPage.setOnClickListener(this);
        b.btnMenuDelivery.setOnClickListener(this);
        b.btnMenuBookmark.setOnClickListener(this);
        setterMiddleBtnName();
        SetterMenuCategoryTab();
    }

    private void SetterMenuCategoryTab() {

        b.tabMenuCategory.addTab(b.tabMenuCategory.newTab().setText(context.getString(R.string.menu_tab_category)));
        b.tabMenuCategory.addTab(b.tabMenuCategory.newTab().setText(context.getString(R.string.menu_tab_brand)));
        b.tabMenuCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                menuHeaderCallBack.selectMenuTab(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        b.tabBrandDetail.addTab(b.tabBrandDetail.newTab().setText(context.getString(R.string.menu_header_brand_tabs_fashion)));
        b.tabBrandDetail.addTab(b.tabBrandDetail.newTab().setText(context.getString(R.string.menu_header_brand_tabs_beauty)));
        b.tabBrandDetail.addTab(b.tabBrandDetail.newTab().setText(context.getString(R.string.menu_header_brand_tabs_living)));
        b.tabBrandDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                menuHeaderCallBack.selectSlideBrandTab(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setterMiddleBtnName() {

        if (data == null || data.tabsInfo == null) {
            return;
        }
        Button[] btn = new Button[]{b.btnMenuMiddle01, b.btnMenuMiddle02, b.btnMenuMiddle03, b.btnMenuMiddle04, b.btnMenuMiddle05, b.btnMenuMiddle06};
        int j = 0;
        for (int i = 0; i < data.tabsInfo.size(); i++) {
            if (!data.tabsInfo.get(i).scheme.startsWith(Schemes.getFullScheme(Schemes.HOME))) {//홈버튼 거르기 위해서
                btn[j].setText(data.tabsInfo.get(i).title);
                btn[j].setTag(data.tabsInfo.get(i).scheme);
                btn[j].setOnClickListener(this);
                j++;
            } else {
                b.btnMenuHome.setTag(data.tabsInfo.get(i).scheme);
                b.btnMenuHome.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View v) {

        if(v == b.btnMenuClose){
            menuHeaderCallBack.close(null);
        }
        else if (v == b.btnMenuHome || v == b.btnMenuMiddle01 || v == b.btnMenuMiddle02 || v == b.btnMenuMiddle03 || v == b.btnMenuMiddle04 || v == b.btnMenuMiddle05 || v == b.btnMenuMiddle06){
            //WiseTracker

             menuHeaderCallBack.close(v.getTag().toString());
        }

        else if(v == b.btnMenuDelivery){
            menuHeaderCallBack.onClickDelivery();
        }
        else if(v == b.btnMenuBookmark){
            menuHeaderCallBack.onClickBookmark();
        }
        else if(v == b.btnMenuMyPage){
            menuHeaderCallBack.onClickMyPage();
        }
    }

    public View getView(){
        return v;
    }

    public interface MenuHeaderCallBack{

        public void close(String scheme);

        public void selectMenuTab(int pos);

        public void selectSlideBrandTab(int pos);

        public void onClickMyPage();

        public void onClickDelivery();

        public void onClickBookmark();

    }


}
