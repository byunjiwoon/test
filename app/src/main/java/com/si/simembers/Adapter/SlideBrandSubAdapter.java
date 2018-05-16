package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Common.G;
import com.si.simembers.R;
import com.si.simembers.databinding.FooterMenuDrawerBinding;
import com.si.simembers.databinding.RowMenuDrawerBinding;
import com.si.simembers.databinding.RowMenuDrawerBrandBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlideBrandSubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEADER = 0;
    private final int TYPE_CONTENT = 1;
    private final int TYPE_FOOTER = 2;
    public static final int BRAND_FASHION = 0;
    public static final int BRAND_BEAUTY = 1;
    public static final int BRAND_LIVING = 2;

    private final String TITLE = "title";
    private final String KEY = "key";
    private final String CHILD = "child";
    private final String CHILD_URL = "childUrl";

    Context context;
    SlideMenuData slideMenuData;
    private boolean isShowHeader = false;

    private SubFooterListener footerCallback;
    private SubListener callback;
    private int brandType;
    private int size;
    ArrayList<Map> infos = new ArrayList<>();


    public void refresh(SlideMenuData slideMenuData, int brandType)
    {
        this.slideMenuData = slideMenuData;
        this.brandType = brandType;
        this.infos =getInfos(brandType);
        this.size = infos.size();
        this.notifyDataSetChanged();
    }

    public interface SubFooterListener{

        void onClickRecentProd(String url);

        void onClickCsCenter();

        void onClickLoginOut();

        void onClickSetting();

        void onClickStore();

        void onTouchRecentView(int action);
    }

    public interface SubListener{

        void onClickFav(int pos, boolean isSelected);

        void onClickBody(int pos);

        void onClickChildBrand(String url);

        void onNeedUserLogin();

    }

    public SlideBrandSubAdapter(Context context, SlideMenuData slideMenuData, int brandType,
                                SubFooterListener footerCallback, SubListener callback ) {

        this.context = context;
        this.slideMenuData = slideMenuData;
        this.brandType = brandType;
        this.footerCallback = footerCallback;
        this.callback = callback;

        infos = getInfos(brandType);
        size = infos.size();

    }

    @Override
    public int getItemViewType(int pos) {

        if (pos == 0 && isShowHeader) return TYPE_HEADER;
        else if (pos == getItemCount() - 1) return TYPE_FOOTER;
        else return  TYPE_CONTENT;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_drawer_brand, parent, false);
                return new HeaderHolder(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_menu_drawer, parent, false);
                return new FooterHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_drawer, parent, false);
                return new ContentHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(isShowHeader && position == 0){
            RowMenuDrawerBrandBinding b = ((HeaderHolder)holder).b;
            b.btnBrandTitle.setText(context.getString(R.string.menu_header_brand_fav));
            b.btnBrandTitle.setCompoundDrawables(null, null, null, null);
            b.llBrandRoot.setVisibility(View.VISIBLE);
            return;

        }

        else if (position == getItemCount() - 1){

            FooterMenuDrawerBinding b = ((FooterHolder) holder).b;
            if(G.USER_LOGIN)
                b.footer.btnFooterLogInOut.setText("로그아웃");
            else
                b.footer.btnFooterLogInOut.setText("로그인");

            //최근 상품데이터 리사이클러뷰~

            return;

        }

        final int pos = (isShowHeader)? position - 1 : position;

        RowMenuDrawerBinding b = ((ContentHolder)holder).b;
        b.brand.btBrandFav.setSelected(false);

        //myBrand는 selected (true)

        if(G.USER_LOGIN && slideMenuData.menuBrand.myBrand != null && slideMenuData.menuBrand.myBrand.size() > 0){
            String brandCode = (infos.get(pos).get(KEY) != null) ? infos.get(pos).get(KEY).toString() : "";
            for(String s : slideMenuData.menuBrand.myBrand){
                if(s!=null && s.equalsIgnoreCase(brandCode)){
                    b.brand.btBrandFav.setSelected(true);
                }
            }
        }


        if(infos.get(pos).get(CHILD) != null){
            String[] childNames = infos.get(pos).get(CHILD).toString().split(",");
            String[] childUrls =infos.get(pos).get(CHILD_URL).toString().split(",");
            b.brand.llBrandChild.setVisibility(View.VISIBLE);
            b.brand.llBrandChild.removeAllViews();

            for(int i = 0;  i < childNames.length; i++){

                String childName = childNames[i];
                String childUrl = childUrls[i];
                RowMenuDrawerBrandBinding rowBrnd = DataBindingUtil.bind(LayoutInflater.from(context).inflate(R.layout.row_menu_drawer_brand, null));
                rowBrnd.btnBrandTitle.setText(Html.fromHtml(childName));
                rowBrnd.btnBrandTitle.setPadding(
                        (int)rowBrnd.btnBrandTitle.getPaddingLeft()*2
                        ,rowBrnd.btnBrandTitle.getPaddingTop()
                        ,rowBrnd.btnBrandTitle.getPaddingBottom()
                        ,rowBrnd.btnBrandTitle.getPaddingRight()
                );
                rowBrnd.btnBrandTitle.setBackgroundResource(0);
                rowBrnd.btnBrandTitle.setCompoundDrawables(null,null,null,null);
                rowBrnd.llBrandRoot.setVisibility(View.VISIBLE);

                if(pos < size - 1) // 마지막 item을 제외하고는 child 선은 무조건 다 회색
                    rowBrnd.vDivide.setBackgroundColor(0XFFededed);
                else
                    if(i == childNames.length - 1){ //마지막 item의 마지막 child 선은 진한 검은색
                    rowBrnd.vDivide.setBackgroundColor(0XFF222222);
                }

                b.brand.llBrandChild.addView(rowBrnd.getRoot());
            }

        }
        else {
            b.brand.llBrandChild.setVisibility(View.GONE);
        }

        b.brand.llBrandRoot.setVisibility(View.VISIBLE);
        b.category.llCategoryRoot.setVisibility(View.GONE);

        String title = infos.get(pos).get(TITLE) == null ? "null" : infos.get(pos).get(TITLE).toString();
        b.brand.btnBrandTitle.setText(Html.fromHtml(title));
        b.brand.btnBrandTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(brandType == BRAND_LIVING){
            b.brand.btBrandFav.setVisibility(View.GONE);
        }
        else
        {
            b.brand.btBrandFav.setVisibility(View.VISIBLE);
            b.brand.btBrandFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(G.USER_LOGIN)
                    callback.onClickFav(pos, !v.isSelected());

                    else
                        callback.onNeedUserLogin();
                }
            });
        }
        if(pos < size - 1)
        {
            b.brand.vDivide.setBackgroundColor(0XFFededed);
        }
        else {
            if(infos.get(pos).get(CHILD) == null) //child가 없으면서 마지막 item인 경우
                b.brand.vDivide.setBackgroundColor(0XFF222222);
        }




    }

    public boolean isHeaderFav() {
        if (!G.USER_LOGIN) {
            return false;
        }

        for (int i = 0; i < infos.size(); i++) {
            String brndHallCd = (infos.get(i).get(KEY) != null) ? infos.get(i).get(KEY).toString() : "";
            for (String s : slideMenuData.menuBrand.myBrand) {
                if (s != null && s.equalsIgnoreCase(brndHallCd)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        int itemCount = size + 1; // footer
        if(isShowHeader)
            itemCount = itemCount + 1; // 헤더

        return itemCount;


    }

    private class HeaderHolder extends RecyclerView.ViewHolder{

        RowMenuDrawerBrandBinding b;

        public HeaderHolder(View itemView) {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder{

        FooterMenuDrawerBinding b;

        public FooterHolder(View itemView) {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
        }
    }
    private class ContentHolder extends RecyclerView.ViewHolder{

        RowMenuDrawerBinding b;

        public ContentHolder(View itemView) {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
        }
    }

    private ArrayList<Map> getInfos(int brandType){

        infos.clear();
        ArrayList<Map> temp = new ArrayList<>();

        if(slideMenuData == null || slideMenuData.menuBrand == null)
            return temp;


        if(brandType == BRAND_BEAUTY && slideMenuData.menuBrand.beauty !=null){

            for(int i = 0; i< slideMenuData.menuBrand.beauty.size(); i++)
            {
                Map<String,String> map = new HashMap<>();
                map.put(TITLE, slideMenuData.menuBrand.beauty.get(i).brndHallNm);
                map.put(KEY, slideMenuData.menuBrand.beauty.get(i).brndHallCd);

                if(slideMenuData.menuBrand.beauty.get(i).brandhallList != null &&
                        slideMenuData.menuBrand.beauty.get(i).brandhallList.size() > 0){

                    String child = null;
                    String childUrl = null;
                    for(int j = 0; j < slideMenuData.menuBrand.beauty.get(i).brandhallList.size(); j++){
                        child = (child == null) ? slideMenuData.menuBrand.beauty.get(i).brandhallList.get(j).brndHallNm :
                                child + "," + slideMenuData.menuBrand.beauty.get(i).brandhallList.get(j).brndHallNm;

                        childUrl = (childUrl == null) ? slideMenuData.menuBrand.beauty.get(i).brandhallList.get(j).urlMove :
                                childUrl + "," + slideMenuData.menuBrand.beauty.get(i).brandhallList.get(j).urlMove;
                    }
                    map.put(CHILD, child);
                    map.put(CHILD_URL, childUrl);
                }

                temp.add(map);


            }



        }else if(brandType == BRAND_FASHION && slideMenuData.menuBrand.fashion != null){
            for(int i = 0; i < slideMenuData.menuBrand.fashion.size(); i++)
            {
                Map<String, String> map = new HashMap<>();
                map.put(TITLE, slideMenuData.menuBrand.fashion.get(i).brndHallNm);
                map.put(KEY, slideMenuData.menuBrand.fashion.get(i).brndHallCd);

                if (slideMenuData.menuBrand.fashion.get(i).brandhallList != null
                        && slideMenuData.menuBrand.fashion.get(i).brandhallList.size() > 0) {
                    String child = null;
                    String childUrl = null;
                    for (int j = 0; j < slideMenuData.menuBrand.fashion.get(i).brandhallList.size(); j++) {
                        child = (child == null) ? slideMenuData.menuBrand.fashion.get(i).brandhallList.get(j).brndHallNm :
                                child + "," + slideMenuData.menuBrand.fashion.get(i).brandhallList.get(j).brndHallNm;
                        childUrl = (childUrl == null) ? slideMenuData.menuBrand.fashion.get(i).brandhallList.get(j).urlMove :
                                childUrl + "," + slideMenuData.menuBrand.fashion.get(i).brandhallList.get(j).urlMove;
                    }
                    map.put(CHILD, child);
                    map.put(CHILD_URL, childUrl);
                }

                temp.add(map);

            }


        }else if (brandType ==BRAND_LIVING && slideMenuData.menuBrand.living != null){

            for(int i=0; i<slideMenuData.menuBrand.living.size(); i++)
            {
                Map<String, String> map = new HashMap<>();
                map.put(TITLE, slideMenuData.menuBrand.living.get(i).brndHallNm);
                map.put(KEY, slideMenuData.menuBrand.living.get(i).brndHallCd);

                if(slideMenuData.menuBrand.living.get(i).brandhallList != null
                        && slideMenuData.menuBrand.living.get(i).brandhallList.size() > 0){

                    String child = null;
                    String childUrl = null;
                    for (int j = 0; j < slideMenuData.menuBrand.living.get(i).brandhallList.size(); j++){

                        child = (child == null) ? slideMenuData.menuBrand.living.get(i).brandhallList.get(j).brndHallNm :
                                child + "," +slideMenuData.menuBrand.living.get(i).brandhallList.get(j).brndHallNm;

                        childUrl = (childUrl == null) ? slideMenuData.menuBrand.living.get(i).brandhallList.get(j).urlMove :
                                childUrl + "," + slideMenuData.menuBrand.living.get(i).brandhallList.get(j).urlMove;


                    }
                    map.put(CHILD,child);
                    map.put(CHILD_URL, childUrl);

                }

                temp.add(map);
            }

        }

        return temp;

    }

}
