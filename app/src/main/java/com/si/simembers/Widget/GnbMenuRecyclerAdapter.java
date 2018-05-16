package com.si.simembers.Widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.R;

/**
 * Created by byun on 2018-03-16.
 */

public class GnbMenuRecyclerAdapter extends RecyclerView.Adapter<GnbMenuRecyclerAdapter.ItemHolder> {

    TabInfoData tabInfoData;
    public boolean isAnimState;

    public interface onMainTopAdapterCallBack{
        void onClickTab(int position);
    }

    private onMainTopAdapterCallBack callBack;
    private Context context;

    private Animation animTabSelect;
    public int selected = HOME;



    public static final int HOME = 0;
    public static final int CHOICE = 1;
    public static final int EVENTE = 2;
    public static final int PLAN = 3;
    public static final int OUTLET = 4;
    public static final int NOW = 5;
    public static final int MAGAZINE = 6;


    public GnbMenuRecyclerAdapter(TabInfoData tabInfoData, onMainTopAdapterCallBack callBack, Context context) {
        this.tabInfoData = tabInfoData;
        if(tabInfoData == null)
        {
            Toast.makeText(context, "TabInfoData : null ", Toast.LENGTH_SHORT).show();
        }
        this.callBack = callBack;

        animTabSelect = AnimationUtils.loadAnimation(context, R.anim.anim_tab_indicator);

    }

    @Override

    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_top_tab,parent,false);

        ItemHolder holder = new ItemHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {

        final int pos = position % tabInfoData.tabsInfo.size();

        onSelect(holder, pos, selected);// 밑줄 초기화.

        TabInfoData.Tabs data = tabInfoData.tabsInfo.get(pos);
        holder.text.setText(data.title);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected = position % tabInfoData.tabsInfo.size();
                isAnimState = false;
                notifyDataSetChanged();


                callBack.onClickTab(position);

            }
        });

    }


    public class ItemHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public View line;

        public ItemHolder(View itemView) {
            super(itemView);

            text = (TextView)itemView.findViewById(R.id.tvTopTab);
            line = (View)itemView.findViewById(R.id.vTopTabIndicator);

        }
    }




    @Override
    public int getItemCount() {
        return 1000*tabInfoData.tabsInfo.size();
    }


    public void onSelect(ItemHolder holder, int pos, int selected) {

        if(pos == selected) {

            holder.line.setVisibility(View.VISIBLE);

            if(!isAnimState){
                holder.line.startAnimation(animTabSelect);
                isAnimState = true;
            }


        }
        else
            holder.line.setVisibility(View.INVISIBLE);
    }

    public int getRealPos(int position){
        return position % tabInfoData.tabsInfo.size();
    }

    public int getCenterPos() {
        if (getItemCount() > 0) {
            return getItemCount() / 2;
        } else {
            return 0;
        }
    }







}


