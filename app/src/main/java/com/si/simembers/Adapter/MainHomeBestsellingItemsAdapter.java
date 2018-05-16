package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.R;
import com.si.simembers.databinding.PageHomeBestsellingItemsBinding;

import java.util.List;

/**
 * Created by byun on 2018-04-02.
 */

public class MainHomeBestsellingItemsAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<MainHomeTabData.BestSelling> list;
    private RecyclerView[] rvDetailItem;
    private MainHomeBestSellingItemsDetailAdapter mainHomeBestSellingItemsDetailAdapter;
    private PageHomeBestsellingItemsBinding b;
    private onMainHomeBestsellingItemsCallback callback;
    private int page;



    public MainHomeBestsellingItemsAdapter(Context context, LayoutInflater inflater, List<MainHomeTabData.BestSelling> list,
                                           onMainHomeBestsellingItemsCallback callback) {

        this.inflater = inflater;
        this.context = context;
        this.list = list;
        this.callback = callback;

        page = list == null ? 0 : list.size();
        rvDetailItem = new RecyclerView[page];
    }

    public void refresh(List<MainHomeTabData.BestSelling> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {



        final int page = position;
        b = DataBindingUtil.inflate(inflater, R.layout.page_home_bestselling_items, container, false);
        rvDetailItem[position] = b.rvHomeBestSellingItems;
        List<MainHomeTabData.BestSellingItems> itemsList = getMappingItems(position);


        mainHomeBestSellingItemsDetailAdapter = new MainHomeBestSellingItemsDetailAdapter(context, itemsList,
                new MainHomeBestSellingItemsDetailAdapter.onMainHomeBestSellingDetailCallback() {
            @Override
            public void onClickItem(int pos) {
                callback.onClickItem(page, pos);
            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvDetailItem[position].setLayoutManager(llm);
        rvDetailItem[position].setAdapter(mainHomeBestSellingItemsDetailAdapter);
        rvDetailItem[position].setNestedScrollingEnabled(false);

        container.addView(b.getRoot());

       /* View view = inflater.inflate(R.layout.page_home_bestselling_items, null);

        container.addView(view);

        return view;*/
        return b.getRoot();


    }

    public RecyclerView getHomeBestSellingItemsRecyclerView(int page){

        if(getCount()>0)
            return rvDetailItem[page];

        else
            return  null;
    }

    private List<MainHomeTabData.BestSellingItems> getMappingItems(int position) {

        List<MainHomeTabData.BestSellingItems> itemsList  = list.get(position).tab;
        return itemsList;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public interface onMainHomeBestsellingItemsCallback{
        void onClickItem(int pageType, int itemPos);
    }
}
