package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.R;
import com.si.simembers.Widget.NoneSwipePager;
import com.si.simembers.databinding.ListMenuDrawerBinding;
import com.si.simembers.databinding.PagerMenuDrawerSubBinding;

public class MenuDrawerContentAdapter extends PagerAdapter {


    private final int CATEGORY = 0;
    private final int BRAND = 1;
    private final int PAGE = 2;
    private LayoutInflater inflater;
    private Context context;
    private SlideMenuData slideMenuData;

    private SlideCategoryAdapter slideCategoryAdapter;
    private SlideBrandAdapter slideBrandAdapter;

    private PagerMenuDrawerSubBinding brand;
    private ListMenuDrawerBinding category;

    private onMenuDrawerContentCallback callback;

    public MenuDrawerContentAdapter(LayoutInflater inflater, Context context, SlideMenuData slideMenuData, onMenuDrawerContentCallback callback) {
        this.inflater = inflater;
        this.context = context;
        this.slideMenuData = slideMenuData;
        this.callback = callback;
    }


    @Override
    public int getCount() {
        return PAGE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public void getRecentProduct() {
        if (slideBrandAdapter != null) {
            slideBrandAdapter.getRecentProduct();
            slideCategoryAdapter.notifyDataSetChanged();
        }
    }


    public interface onMenuDrawerContentCallback {
        void onClickCategoryItem(int category, int item);

        void onClickBrandFav(int page, int item, boolean isSelected);

        void onClickChildBrand(String url);

        void onNeedUserLogin();

        void onClickBrandItem(int page, int pos);

        void onClickRecentProd(String url);

        void onClickCsCenter();

        void onClickLoginOut();

        void onClickSetting();

        void onClickStore();

        void onTouchRecentView(int action);
    }




    public NoneSwipePager getBrandPager() {
        return brand.pagerDrawerSub;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if(position == CATEGORY)
        {
            category = DataBindingUtil.inflate(inflater, R.layout.list_menu_drawer, container, false);
            slideCategoryAdapter = new SlideCategoryAdapter(context, slideMenuData, slideMenuListener);
            slideCategoryAdapter.setFooterCallbackListener(new SlideCategoryAdapter.MenuDrawerFooterListener() {
                @Override
                public void onClickRecentProd(String url) {
                    callback.onClickRecentProd(url);
                }

                @Override
                public void onClickCsCenter() {

                }

                @Override
                public void onClickLoginOut() {

                    callback.onClickLoginOut();

                }

                @Override
                public void onClickSetting() {

                }

                @Override
                public void onClickStore() {

                }

                @Override
                public void onTouchRecentView(int action) {

                }
            });
            getRecentProduct();
            LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            category.rvMenuDrawer.setLayoutManager(llm);
            category.rvMenuDrawer.setAdapter(slideCategoryAdapter);
            container.addView(category.getRoot());

            return category.getRoot();

        }
        else if(position == BRAND)
        {
            brand = DataBindingUtil.inflate(inflater, R.layout.pager_menu_drawer_sub, container, false);
            slideBrandAdapter = new SlideBrandAdapter(context, inflater, slideMenuData, new SlideBrandAdapter.BrandCallback() {
                @Override
                public void onClickBrandFav(int page, int pos, boolean isSelected) {

                    callback.onClickBrandFav(page,pos,isSelected);

                }

                @Override
                public void onNeedUserLogin() {

                    callback.onNeedUserLogin();

                }

                @Override
                public void onClickBrandItem(int page, int pos) {

                }

                @Override
                public void onClickChildBrand(String url) {

                }

                @Override
                public void onClickRecentProd(String url) {

                }

                @Override
                public void onClickCsCenter() {

                }

                @Override
                public void onClickLoginOut() {

                }

                @Override
                public void onClickSetting() {

                }

                @Override
                public void onClickStore() {

                }

                @Override
                public void onTouchRecentView(int action) {

                }
            });
            brand.pagerDrawerSub.setAdapter(slideBrandAdapter);
            brand.pagerDrawerSub.setOffscreenPageLimit(10);
            container.addView(brand.getRoot());
            return brand.getRoot();

        }
        else{


            return null;

        }


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    private SlideCategoryAdapter.MenuDrawerListener slideMenuListener = new SlideCategoryAdapter.MenuDrawerListener() {
        @Override
        public void onClickCategory(int pos) {

        }

        @Override
        public void onClickItem(int category, int item) {

            callback.onClickCategoryItem(category, item);

        }
    };


}
