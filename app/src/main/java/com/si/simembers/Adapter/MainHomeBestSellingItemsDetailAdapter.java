package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Common.Utils;
import com.si.simembers.R;
import com.si.simembers.databinding.RowHomeBestsellingItemBinding;

import java.util.List;

/**
 * Created by byun on 2018-04-02.
 */

public class MainHomeBestSellingItemsDetailAdapter extends RecyclerView.Adapter<MainHomeBestSellingItemsDetailAdapter.Holder> {

    private Context context;
    private List<MainHomeTabData.BestSellingItems> list;
    private onMainHomeBestSellingDetailCallback callback;

    public MainHomeBestSellingItemsDetailAdapter(Context context, List<MainHomeTabData.BestSellingItems> list, onMainHomeBestSellingDetailCallback callback) {
        this.context = context;
        this.list = list;
        this.callback =callback;
    }



    public interface onMainHomeBestSellingDetailCallback {
        void onClickItem(int pos);
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_bestselling_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final int pos = position;
        final MainHomeTabData.BestSellingItems data = list.get(pos);

        if (position > 0) {
            holder.binding.vDivide.setVisibility(View.VISIBLE);
        } else {
            holder.binding.vDivide.setVisibility(View.GONE);
        }

        holder.binding.tvHomeBestsellingRank.setText(String.valueOf(data.rank));
        holder.binding.tvHomeBestsellingBrand.setText(data.brndNm);
        holder.binding.tvHomeBestsellingTitle.setText(data.productNm);

       String price = Utils.gettingPriceText(context, data.nmlPrc, data.realSalePrc, true, true, "#999999", "#212121");
        holder.binding.tvHomeBestsellingPrice.setText(price);

        if (data.soldOutYn != null
                && data.soldOutYn.equals("Y")) {
            holder.binding.flHomeBestSellingSoldOut.setVisibility(View.VISIBLE);
        } else {
            holder.binding.flHomeBestSellingSoldOut.setVisibility(View.GONE);
            if (data.saleYn != null && data.saleYn.equalsIgnoreCase("Y")) {
                holder.binding.tvHomeBestSellingSale.setVisibility(View.VISIBLE);
            } else {
                holder.binding.tvHomeBestSellingSale.setVisibility(View.GONE);
            }
        }

        Glide.with(context).load(data.imgPath).into(holder.binding.ivHomeBestsellingItem);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public RowHomeBestsellingItemBinding binding;

        public Holder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
