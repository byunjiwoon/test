package com.si.simembers.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.si.simembers.Api.Model.RecentProdData;
import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.databinding.RowMenuRecentProdBinding;

import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class MenuRecentProdAdapter extends RecyclerView.Adapter<MenuRecentProdAdapter.Holder> {


    private RecentProdData recentProdData;
    private onRecentProdCallback callback;

    public interface onRecentProdCallback {
        void onClickItem(int pos);
    }

    public MenuRecentProdAdapter(RecentProdData recentProdData, onRecentProdCallback callback) {
        this.recentProdData = recentProdData;
        this.callback = callback;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        return  new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_recent_prod , parent,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        final int pos = position;
        String url = recentProdData.prodList.get(position).imgPath;

        final WeakReference<CircleImageView> img = new WeakReference<>(holder.binding.civRecentProd);
        //WeakReference는 GC가 발생할 때 무조건 제거
        //img 인스턴스가 더 이상 사용되지않을 때 GC발생


        MGlide.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .sizeMultiplier(0.1f).placeholder(R.drawable.sifamily_logo_noimage)
                .error(R.drawable.sifamily_logo_noimage)
                .into(new SimpleTarget<GlideDrawable>(){

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (img.get() == null) {
                            return;
                        }

                        if (recentProdData.prodList.get(pos).productSct == null || !recentProdData.prodList.get(pos).productSct.equalsIgnoreCase(G.LIVING)) {
                            img.get().setSelected(Utils.resizedImageView(holder.binding.civRecentProd));
                        } else {
                            img.get().setSelected(true);
                        }

                        img.get().setImageDrawable(resource);
                    }

                });


        if (recentProdData.prodList.get(position).soldOutYn != null
                && recentProdData.prodList.get(position).soldOutYn.equals("Y")) {
      //품절일 경우
            holder.binding.flRecentProdSoldout.setVisibility(View.VISIBLE);
            holder.binding.civRecentProdDim.setVisibility(View.GONE);
        } else {
            holder.binding.flRecentProdSoldout.setVisibility(View.GONE);
            holder.binding.civRecentProdDim.setVisibility(View.VISIBLE);
        }

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickItem(pos);
            }
        });



    }

    @Override
    public int getItemCount() {
        return recentProdData.prodList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        public RowMenuRecentProdBinding binding;

        public Holder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}




