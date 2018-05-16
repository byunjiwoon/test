package com.si.simembers.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.Widget.NetworkImageView;
import com.si.simembers.databinding.RowBannerMoreBinding;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by byun on 2018-04-10.
 */

public class HomeBannerMoreAdapter extends RecyclerView.Adapter<HomeBannerMoreAdapter.Holder> {

    private List<MainHomeTabData.NormalBanner> list;
    private HomeBannerMoreAdapter.onHomeBannerMoreCallback callback;

    public HomeBannerMoreAdapter(List<MainHomeTabData.NormalBanner> list, HomeBannerMoreAdapter.onHomeBannerMoreCallback callback) {
        super();
        this.list = list;
        this.callback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_banner_more,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final int pos = position;
        MainHomeTabData.NormalBanner data = list.get(pos);

        if (pos < list.size() - 1) {
            holder.binding.vDivide.setVisibility(View.GONE);
        } else {
            holder.binding.vDivide.setVisibility(View.VISIBLE);
        }

        holder.binding.tvBannerMoreTitle.setText(data.txt);
        holder.binding.tvBannerMoreSubTitle.setText(data.subTxt);

        holder.binding.btBannerMoreItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                callback.onClickItem(position);

            }
        });


        final WeakReference<NetworkImageView> img = new WeakReference<>(holder.binding.nivBannerMorePict);

        MGlide.load(data.imgPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .sizeMultiplier(0.1f).placeholder(R.drawable.big_banner_noimage)
                .error(R.drawable.big_banner_noimage)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (img.get() != null) {
                            img.get().setImageDrawable(resource);
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public RowBannerMoreBinding binding;


        public Holder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }
    }

    public interface onHomeBannerMoreCallback{
        void onClickItem(int pos);
    }
}
