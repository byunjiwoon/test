package com.si.simembers.Widget;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Fragment.ImageBannerFragment;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.databinding.PageHomeNormalBannerBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byun on 2018-03-21.
 */

public class HomeBannerPagerAdapter extends PagerAdapter {

    private final int LOOP_CNT = 500;
    private LayoutInflater inflater;
    private List<MainHomeTabData.NormalBanner> list;
    private NetworkImageView[] ivItem;


    public HomeBannerPagerAdapter(LayoutInflater inflater, List<MainHomeTabData.NormalBanner> list)
    {
        this.inflater = inflater;
        this.list = list;
        this.ivItem = new NetworkImageView[list.size()];
    }

    public void refresh(List<MainHomeTabData.NormalBanner> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size()*LOOP_CNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        PageHomeNormalBannerBinding v = DataBindingUtil.inflate(inflater, R.layout.page_home_normal_banner, container, false);
        final int pos = position % list.size();
        MainHomeTabData.NormalBanner data = list.get(pos);
        ivItem[pos] = v.ivBanner;
        final WeakReference<NetworkImageView> img = new WeakReference<NetworkImageView>(ivItem[pos]);

        MGlide.load(data.imgPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.big_banner_noimage)
                .error(R.drawable.big_banner_noimage)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if(img.get()!=null){
                            img.get().setImageDrawable(resource);
                        }
                    }
                });

        container.addView(v.getRoot());
        return v.getRoot();

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);
    }

    public View getView(int position){
        final int pos = position%list.size();
        return ivItem[pos];
    }

    public int getCenterPos(){

        return getCount() / 2;
    }

}
