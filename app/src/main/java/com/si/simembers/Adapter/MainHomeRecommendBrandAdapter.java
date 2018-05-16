package com.si.simembers.Adapter;

import android.content.Context;
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
import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.Widget.NetworkImageView;
import com.si.simembers.databinding.RowHomeRecommendBrandBinding;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.internal.Util;

/**
 * Created by byun on 2018-04-04.
 */

public class MainHomeRecommendBrandAdapter extends RecyclerView.Adapter<MainHomeRecommendBrandAdapter.Holder>{

    private Context context;
    private List<MainHomeTabData.BrandInfo> list;
    private onMainHomeRecommendBrandCallback callback;

    public MainHomeRecommendBrandAdapter(Context context, onMainHomeRecommendBrandCallback callback) {
        this.context = context;
        list = SiApplication.get().getHomeTabData().recommendBrand;
        this.callback = callback;
    }

    public interface onMainHomeRecommendBrandCallback {
        void onClickBanner(int pos);

        void onClickLeftItem(int pos, int itemPos);

        void onClickCenterItem(int pos, int itemPos);

        void onClickRightItem(int pos, int itemPos);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_recommend_brand, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final int pos = position;
        MainHomeTabData.BrandInfo data = list.get(pos);

     //   Glide.with(context).load(data.bnrImgPath).into(holder.binding.nivHomeRecommendBrand);

        final WeakReference<NetworkImageView> img = new WeakReference<>(holder.binding.nivHomeRecommendBrand);

        MGlide.load(context, data.bnrImgPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .sizeMultiplier(0.1f).placeholder(R.drawable.product_img_noimage)
                .error(R.drawable.product_img_noimage)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (img.get() == null) {
                            return;
                        }

                        img.get().setImageDrawable(resource);
                    }
                });




        holder.binding.nivHomeRecommendBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickBanner(pos);
            }
        });

        if (data.child.size() > 0) settingLeftView(holder.binding, pos);

        if (data.child.size() > 1) settingCenterView(holder.binding, pos);

        if (data.child.size() > 2) settingRightView(holder.binding, pos);

    }

    private void settingRightView(RowHomeRecommendBrandBinding binding, final int pos) {
        final MainHomeTabData.ChildItem data = list.get(pos).child.get(2);

        final WeakReference<NetworkImageView> img = new WeakReference<NetworkImageView>(binding.nivRecommendBrandChildRight);


        MGlide.load(context, data.imgPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .sizeMultiplier(0.1f).placeholder(R.drawable.product_img_noimage)
                .error(R.drawable.product_img_noimage)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (img.get() == null) {
                            return;
                        }

                        if (data.productSct == null || !data.productSct.equalsIgnoreCase(G.LIVING)) {
                            img.get().setSelected(Utils.resizedImageView(img.get()));
                        } else {
                            img.get().setSelected(true);
                        }
                        img.get().setImageDrawable(resource);
                    }
                });


        //  Glide.with(context).load(data.imgPath).into(binding.nivRecommendBrandChildRight);

        binding.tvHomeRecommendBrandRight.setText(data.brndNm);
        binding.tvHomeRecommendTitleRight.setText(data.productNm);
        String price = Utils.gettingPriceText(context, data.nmlPrc, data.realSalePrc, true, false, "#999999", "#212121");
        binding.tvHomeRecommendPriceRight.setText(price);



        if (data.soldOutYn != null
                && data.soldOutYn.equals("Y")) {
            binding.flRecommendBrandChildSoldOutCenter.setVisibility(View.VISIBLE);
        } else {
            binding.flRecommendBrandChildSoldOutCenter.setVisibility(View.GONE);
            if (data.saleYn != null && data.saleYn.equalsIgnoreCase("Y")) {
                binding.tvRecommendBrandChildSaleCenter.setVisibility(View.VISIBLE);
            } else {
                binding.tvRecommendBrandChildSaleCenter.setVisibility(View.GONE);
            }
        }

        binding.llRecommendBrandRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickRightItem(pos, 2);
            }
        });



    }

    private void settingCenterView(RowHomeRecommendBrandBinding binding, final int pos) {
        final MainHomeTabData.ChildItem data = list.get(pos).child.get(1);
      //  Glide.with(context).load(data.imgPath).into(binding.nivRecommendBrandChildCenter);

        final WeakReference<NetworkImageView> img = new WeakReference<NetworkImageView>(binding.nivRecommendBrandChildCenter);
        MGlide.load(context, data.imgPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .sizeMultiplier(0.1f).placeholder(R.drawable.product_img_noimage)
                .error(R.drawable.product_img_noimage)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (img.get() == null) {
                            return;
                        }

                        if (data.productSct == null || !data.productSct.equalsIgnoreCase(G.LIVING)) {
                            img.get().setSelected(Utils.resizedImageView(img.get()));
                        } else {
                            img.get().setSelected(true);
                        }
                        img.get().setImageDrawable(resource);
                    }
                });
        binding.tvHomeRecommendBrandCenter.setText(data.brndNm);
        binding.tvHomeRecommendTitleCenter.setText(data.productNm);
        String price = Utils.gettingPriceText(context, data.nmlPrc, data.realSalePrc, true, false, "#999999", "#212121");
        binding.tvHomeRecommendPriceCenter.setText(price);

        if (data.soldOutYn != null
                && data.soldOutYn.equals("Y")) {
            binding.flRecommendBrandChildSoldOutCenter.setVisibility(View.VISIBLE);
        } else {
            binding.flRecommendBrandChildSoldOutCenter.setVisibility(View.GONE);
            if (data.saleYn != null && data.saleYn.equalsIgnoreCase("Y")) {
                binding.tvRecommendBrandChildSaleCenter.setVisibility(View.VISIBLE);
            } else {
                binding.tvRecommendBrandChildSaleCenter.setVisibility(View.GONE);
            }
        }

        binding.llRecommendBrandCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickCenterItem(pos, 1);
            }
        });
    }

    private void settingLeftView(RowHomeRecommendBrandBinding binding, final int pos) {
        final MainHomeTabData.ChildItem data = list.get(pos).child.get(0);
      //  Glide.with(context).load(data.imgPath).into(binding.nivRecommendBrandChildLeft);
        final WeakReference<NetworkImageView> img = new WeakReference<NetworkImageView>(binding.nivRecommendBrandChildLeft);

        MGlide.load(context, data.imgPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                .sizeMultiplier(0.1f).placeholder(R.drawable.product_img_noimage)
                .error(R.drawable.product_img_noimage)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (img.get() == null) {
                            return;
                        }

                        if (data.productSct == null || !data.productSct.equalsIgnoreCase(G.LIVING)) {
                            img.get().setSelected(Utils.resizedImageView(img.get()));
                        } else {
                            img.get().setSelected(true);
                        }
                        img.get().setImageDrawable(resource);
                    }
                });
        binding.tvHomeRecommendBrandLeft.setText(data.brndNm);
        binding.tvHomeRecommendTitleLeft.setText(data.productNm);
        String price = Utils.gettingPriceText(context, data.nmlPrc, data.realSalePrc, true, false, "#999999", "#212121");
        binding.tvHomeRecommendPriceLeft.setText(price);

        if (data.soldOutYn != null
                && data.soldOutYn.equals("Y")) {
            binding.flRecommendBrandChildSoldOutLeft.setVisibility(View.VISIBLE);
        } else {
            binding.flRecommendBrandChildSoldOutLeft.setVisibility(View.GONE);
            if (data.saleYn != null && data.saleYn.equalsIgnoreCase("Y")) {
                binding.tvRecommendBrandChildSaleLeft.setVisibility(View.VISIBLE);
            } else {
                binding.tvRecommendBrandChildSaleLeft.setVisibility(View.GONE);
            }
        }

        binding.llRecommendBrandLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickLeftItem(pos, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // public RowHomeRecommendBrandBinding binding;

   public class Holder extends RecyclerView.ViewHolder{

        public RowHomeRecommendBrandBinding binding;

       public Holder(View itemView) {
           super(itemView);
           binding = DataBindingUtil.bind(itemView);

       }
   }


}
