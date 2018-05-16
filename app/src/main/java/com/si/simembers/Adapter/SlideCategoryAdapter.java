package com.si.simembers.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.si.simembers.Api.Model.RecentProdData;
import com.si.simembers.Api.Model.SlideMenuData;
import com.si.simembers.Common.G;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.databinding.FooterMenuDrawerBinding;
import com.si.simembers.databinding.RowMenuDrawerBinding;

import java.util.ArrayList;

public class SlideCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private int TYPE_CONTENT = 0;
    private int TYPE_FOOTER = 1;
    private Context context;
    private MenuDrawerListener listener;
    private MenuDrawerFooterListener footerCallback;
    private SlideMenuData slideMenuData;

    public SlideCategoryAdapter(Context context, SlideMenuData slideMenuData, MenuDrawerListener listener) {
        this.context = context;
        this.slideMenuData =slideMenuData;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public void setFooterCallbackListener(MenuDrawerFooterListener footerCallback) {
        this.footerCallback = footerCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_FOOTER == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_menu_drawer, parent, false);
            return new FooterHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_drawer, parent, false);
            return new ContentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == getItemCount() - 1){ //footer

            FooterMenuDrawerBinding b = ((FooterHolder) holder).b;


            if (G.USER_LOGIN) {
                b.footer.btnFooterLogInOut.setText(context.getString(R.string.menu_footer_logout));
            } else {
                b.footer.btnFooterLogInOut.setText(context.getString(R.string.menu_footer_login));
            }

            RecentProdData recentProdData = SiApplication.get().getRecentProdData();

           if (recentProdData == null || recentProdData.prodList == null) {
             recentProdData = new RecentProdData();
             recentProdData.prodList = new ArrayList<>();
            }

            MenuRecentProdAdapter recentProdAdapter = new MenuRecentProdAdapter(recentProdData, new MenuRecentProdAdapter.onRecentProdCallback() {
                @Override
                public void onClickItem(int pos) {

                    if(footerCallback != null){
                        String url =  SiApplication.get().getRecentProdData().prodList.get(pos).imgUrlMovePath;
                        footerCallback.onClickRecentProd(url);

                    }
                }
            });
            b.rvMenuRecentProduct.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            b.rvMenuRecentProduct.setAdapter(recentProdAdapter);



            if (recentProdData != null && recentProdData.prodList != null && recentProdData.prodList.size() > 0) {
                b.rvMenuRecentProduct.setVisibility(View.VISIBLE);
                b.txtRecentProdNoData.setVisibility(View.GONE);
            } else {
                b.rvMenuRecentProduct.setVisibility(View.GONE);
                b.txtRecentProdNoData.setVisibility(View.VISIBLE);
            }

            return; //footer 처리 끝
        }

        RowMenuDrawerBinding binding = ((ContentHolder)holder).b;


        final int pos = position;
        SlideMenuData.MenuCategory menu = slideMenuData.category.get(pos);

        binding.category.llCategoryRoot.setVisibility(View.VISIBLE);//category만 활성화
        binding.brand.llBrandRoot.setVisibility(View.GONE); // brand 안보이게


        if (pos > 0) {
            binding.category.vTopDivide.setVisibility(View.GONE);
        } else {
            binding.category.vTopDivide.setVisibility(View.VISIBLE);
        }

        binding.category.btnCategoryTitle.setText(menu.dspCatNm);

        LinearLayout virtual = null;

        int tabSize = 0;
        if(menu.categoryList.size() % 3 == 0)
            tabSize =menu.categoryList.size();
        else
            tabSize =  menu.categoryList.size() + (menu.categoryList.size() % 3 == 1 ?  2 : 1);

        binding.category.llCategoryTabs.removeAllViews();

        for(int i = 0; i<tabSize; i++)
        {
            final int j = i;
            if(i % 3 == 0){
                virtual = new LinearLayout(context);
                virtual.setOrientation(LinearLayout.HORIZONTAL);
                binding.category.llCategoryTabs.addView(virtual);
            }
             View v = LayoutInflater.from(context).inflate(R.layout.btn_menu_category_child, virtual, false);
             v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

            Button btn = (Button)v.findViewById(R.id.btnMenuCategoryChild);

             if(i < menu.categoryList.size())
             {
                 btn.setText(menu.categoryList.get(i).dspCatNm);
                 btn.setTag(menu.categoryList.get(i).dspCatNo);
                 btn.setOnClickListener(new View.OnClickListener(){
                     @Override
                     public void onClick(View v) {

                         listener.onClickItem(pos, j);

                     }
                 });

             }
             virtual.addView(v);

        }

        binding.category.btnCategoryTitle.setTag(binding.category.llCategoryTabs);

    }

    @Override
    public int getItemCount() {
        return slideMenuData != null && slideMenuData.category != null ? slideMenuData.category.size() + 1 : 0;
        //맨 마지막 item은 footer이기 때문 + 1
    }



    public interface MenuDrawerListener {
        void onClickCategory(int pos);

        void onClickItem(int category, int item);
    }

    public interface MenuDrawerFooterListener {
        void onClickRecentProd(String url);

        void onClickCsCenter();

        void onClickLoginOut();

        void onClickSetting();

        void onClickStore();

        void onTouchRecentView(int action);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (getItemCount() - 1)) { //맨 마지막 item은 footer
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FooterMenuDrawerBinding b;

        public FooterHolder(View itemView) {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
            b.footer.btnFooterLogInOut.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == b.footer.btnFooterLogInOut){
                footerCallback.onClickLoginOut();
            }
        }
    }

    private class ContentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RowMenuDrawerBinding b;

        public ContentHolder(View itemView)
        {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
            b.category.btnCategoryTitle.setOnClickListener(this);

        }

        @Override
        public void onClick(final View view) {

            listener.onClickCategory(getLayoutPosition());
            view.setSelected(!view.isSelected());

            LinearLayout tabs = (LinearLayout) view.getTag();
            float fromY;
            float toY;
            if(tabs.getVisibility() == View.VISIBLE){
                fromY = 1f; // 켜져있었으면 1에서 0으로 줄이기
                toY = 0f;
            }else{
                fromY = 0f;
                toY = 1f;
            }

            LinearLayoutVerticalScaleAnimation animation = new LinearLayoutVerticalScaleAnimation(fromY, toY, tabs);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setEnabled(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tabs.startAnimation(animation);

        }
    }

    private class LinearLayoutVerticalScaleAnimation extends ScaleAnimation{

        //확대 애니메이션

        private final LinearLayout view;
        private final LinearLayout.LayoutParams layoutParams;

        private final float beginY;
        private final float endY;
        private final int originalBottomMargin;

        private int expandedHeight;
        private boolean marginsInitialized = false;
        private int marginBottomBegin;
        private int marginBottomEnd;

        private ViewTreeObserver.OnPreDrawListener preDrawListener;

       LinearLayoutVerticalScaleAnimation(float beginY, float endY, LinearLayout linearLayout){
           super(1f, 1f, beginY, endY);
           setDuration(300);
           this.view = linearLayout;
           this.layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
           this.beginY = beginY;
           this.endY = endY;
           this.originalBottomMargin = layoutParams.bottomMargin;

           if(view.getHeight() != 0){
               expandedHeight = view.getHeight();
               initializeMargins();
           }
       }

        private void initializeMargins() {
            final int beginHeight = (int) (expandedHeight * beginY);
            final int endHeight = (int) (expandedHeight * endY);

            marginBottomBegin = beginHeight + originalBottomMargin - expandedHeight;
            marginBottomEnd = endHeight + originalBottomMargin - expandedHeight;
            marginsInitialized = true;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);


            if (!marginsInitialized && preDrawListener == null) {
                // To avoid glitches, don't draw until we've initialized everything.
                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (view.getHeight() != 0) {
                            expandedHeight = view.getHeight();
                            initializeMargins();
                            adjustViewBounds(0f);
                            view.getViewTreeObserver().removeOnPreDrawListener(this);
                        }
                        return false;
                    }
                };

                view.getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }

            if (interpolatedTime < 1.0f && view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }

            if (marginsInitialized) {
                if (interpolatedTime < 1.0f) {
                    adjustViewBounds(interpolatedTime);
                } else if (endY <= 0f && view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }

        }

        private void adjustViewBounds(float interpolatedTime) {
            if (endY == 1) {
                layoutParams.bottomMargin = marginBottomBegin / 2 + (int) ((marginBottomEnd - marginBottomBegin) * interpolatedTime);
            } else {
                layoutParams.bottomMargin = marginBottomBegin + (int) ((marginBottomEnd - marginBottomBegin) * interpolatedTime);
            }
            view.getParent().requestLayout();
        }
    }






}
