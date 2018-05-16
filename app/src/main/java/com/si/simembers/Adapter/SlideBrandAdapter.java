package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Common.Utils;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.databinding.ListMenuDrawerBinding;
import com.si.simembers.databinding.RowHomeRecommendBrandBinding;
import com.si.simembers.databinding.RowMenuDrawerBrandBinding;

public class SlideBrandAdapter extends PagerAdapter {

    public static final int PAGE = 3;
    private LayoutInflater inflater;
    private Context context;
    private SlideMenuData slideMenuData;
    private SlideBrandSubAdapter[] slideBrandSubAdapter;
    private BrandCallback callback;



    public SlideBrandAdapter(Context context,LayoutInflater inflater,  SlideMenuData slideMenuData, BrandCallback callback) {

        this.inflater = inflater;
        this.context = context;
        this.slideMenuData = slideMenuData;
        this.slideBrandSubAdapter = new SlideBrandSubAdapter[PAGE];
        this.callback = callback;

    }


    @Override
    public int getCount() {
        return PAGE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final int page = position;
        ListMenuDrawerBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_menu_drawer, container, false);
        RowMenuDrawerBrandBinding headerFav = RowMenuDrawerBrandBinding.bind(inflater.inflate(R.layout.row_menu_drawer_brand, null));
        headerFav.llBrandRoot.setVisibility(View.VISIBLE);
        headerFav.btnBrandTitle.setText(context.getString(R.string.menu_header_brand_fav));
        headerFav.btnBrandTitle.setCompoundDrawables(null,null,null,null);

        slideBrandSubAdapter[page] = new SlideBrandSubAdapter(context, slideMenuData, position
                , new SlideBrandSubAdapter.SubFooterListener() {
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
        }
        , new SlideBrandSubAdapter.SubListener() {
            @Override
            public void onClickFav(int pos, boolean isSelected) {

                callback.onClickBrandFav(page, pos, isSelected);
                slideBrandSubAdapter[page].refresh(Utils.brandFavSort(SiApplication.get().getSlideMenuData().menuBrand, page, pos, isSelected), page);

            }
            @Override
            public void onClickBody(int pos) {

            }

            @Override
            public void onClickChildBrand(String url) {

            }

            @Override
            public void onNeedUserLogin() {

                callback.onNeedUserLogin();

            }
        });

        getRecentProduct();
        LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvMenuDrawer.setLayoutManager(llm);
        binding.rvMenuDrawer.setItemViewCacheSize(50);
        binding.rvMenuDrawer.setAdapter(slideBrandSubAdapter[page]);

        container.addView(binding.getRoot());

        return binding.getRoot();

    }


    public interface BrandCallback{

        void onClickBrandFav(int page, int pos, boolean isSelected);

        void onNeedUserLogin();

        void onClickBrandItem(int page, int pos);

        void onClickChildBrand(String url);

        void onClickRecentProd(String url);

        void onClickCsCenter();

        void onClickLoginOut();

        void onClickSetting();

        void onClickStore();

        void onTouchRecentView(int action);

    }

    public void getRecentProduct() {
        if (slideBrandSubAdapter[0] != null) {
            slideBrandSubAdapter[0].notifyDataSetChanged();
        }

        if (slideBrandSubAdapter[1] != null) {
            slideBrandSubAdapter[1].notifyDataSetChanged();
        }

        if (slideBrandSubAdapter[2] != null) {
            slideBrandSubAdapter[2].notifyDataSetChanged();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
