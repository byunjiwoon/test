package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
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
import com.si.simembers.Common.G;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.Widget.NetworkImageView;
import com.si.simembers.databinding.PageHomeStaffBannerBinding;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by byun on 2018-04-09.
 */

public class MainHomeStaffBannerAdapter extends PagerAdapter {

    private final int LOOP_CNT = 500;
    private LayoutInflater inflater;
    private onMainHomeStaffBannerCallback callback;
    private Context context;

    private ImageView[] ivItem;
    private List<MainHomeTabData.StaffBanner> list;

    public MainHomeStaffBannerAdapter(LayoutInflater inflater, List<MainHomeTabData.StaffBanner> list, onMainHomeStaffBannerCallback callback, Context context) {
        this.inflater = inflater;
        this.list = list;
        this.callback = callback;
        this.ivItem = new NetworkImageView[list.size()];
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size() * LOOP_CNT;
    }

    public void refresh(List<MainHomeTabData.StaffBanner> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCenterPos()
    {
        return getCount()/2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        PageHomeStaffBannerBinding v = DataBindingUtil.inflate(inflater, R.layout.page_home_staff_banner, container, false);
        final int pos = position % list.size();
        MainHomeTabData.StaffBanner data = list.get(pos);

        ivItem[pos] = v.ivHomeStaffBanner;

        Glide.with(context).load(data.imgPath)
                .placeholder(R.drawable.secret_banner_noimage)
                .error(R.drawable.secret_banner_noimage)
                .dontAnimate().into(v.ivHomeStaffBanner);

        v.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickItem(pos);
            }
        });

        container.addView(v.getRoot());
        return v.getRoot();



    }

    public interface onMainHomeStaffBannerCallback{

        void onClickItem(int pos);

    }

    public View getView(int pos) {
        return ivItem[pos%list.size()];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
