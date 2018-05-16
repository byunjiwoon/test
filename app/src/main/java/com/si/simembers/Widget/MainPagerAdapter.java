package com.si.simembers.Widget;

import android.animation.Keyframe;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.si.simembers.Api.Model.TabInfoData;
import com.si.simembers.Common.Schemes;
import com.si.simembers.Fragment.EditorFragment;
import com.si.simembers.Fragment.EventFragment;
import com.si.simembers.Fragment.HomeFragment;
import com.si.simembers.Fragment.MagazineFragment;
import com.si.simembers.Fragment.NowFragment;
import com.si.simembers.Fragment.OutletFragment;
import com.si.simembers.Fragment.PlanFragment;
import com.si.simembers.R;
import com.si.simembers.SiApplication;
import com.si.simembers.View.Event;
import com.si.simembers.View.Home;
import com.si.simembers.View.Magazine;
import com.si.simembers.View.Now;
import com.si.simembers.View.Outlet;
import com.si.simembers.View.Pick;
import com.si.simembers.View.Plan;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by byun on 2018-03-19.
 */

public class MainPagerAdapter extends PagerAdapter {//많은 fragment를 다룰 때

    public static final int INFINTE = 1000;
    private LayoutInflater inflater;

    //SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    private Context context;

    private TabInfoData tabData;



    private WeakReference<Home> home;
    private WeakReference<Event> event;
    private WeakReference<Magazine> magazine;
    private WeakReference<Now> now;
    private WeakReference<Outlet> outlet;
    private WeakReference<Pick> pick;
    private WeakReference<Plan> plan;
    private WeakReference<View> childView = null;
    private WeakReference<View> view = null;

    private NestedScrollView.OnScrollChangeListener scCallback;

    public MainPagerAdapter(Context context, LayoutInflater inflater, NestedScrollView.OnScrollChangeListener scCallback) {

        this.inflater = inflater;
        this.context = context;
        this.tabData = SiApplication.get().getTabInfoData();
        this.scCallback = scCallback;

        createInstance();

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        view = getCustomView(tabData.tabsInfo.get(getRealPos(position)).scheme);
        container.addView(view.get());
        return view.get();

    }

    private synchronized WeakReference<View> getCustomView(String scheme) {
        Uri query = Uri.parse(scheme);
        final String tab = query.getQueryParameter(Schemes.QUERY_TAB);

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tab.equalsIgnoreCase(Schemes.HOME)) {
                    if (home.get() == null) {
                        home = new WeakReference<>(new Home(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(home.get().getView());
                } else if (tab.equalsIgnoreCase(Schemes.PICK)) {
                    if (pick.get() == null) {
                        pick = new WeakReference<>(new Pick(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(pick.get().getView());
                } else if (tab.equalsIgnoreCase(Schemes.EVENT)) {
                    if (event.get() == null) {
                        event = new WeakReference<>(new Event(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(event.get().getView());
                } else if (tab.equalsIgnoreCase(Schemes.PLAN)) {
                    if (plan.get() == null) {
                        plan = new WeakReference<>(new Plan(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(plan.get().getView());
                } else if (tab.equalsIgnoreCase(Schemes.OUTLET)) {
                    if (outlet.get() == null) {
                        outlet = new WeakReference<>(new Outlet(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(outlet.get().getView());
                } else if (tab.equalsIgnoreCase(Schemes.NOW)) {
                    if (now.get() == null) {
                        now = new WeakReference<>(new Now(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(now.get().getView());
                } else if (tab.equalsIgnoreCase(Schemes.MAGAZINE)) {
                    if (magazine.get() == null) {
                        magazine = new WeakReference<>(new Magazine(context, inflater, scCallback));
                    }
                    childView = new WeakReference<>(magazine.get().getView());
                } else {
                    // error
                    ((Activity) context).finish();
                }
            }
        });

        return childView;
    }



    private void createInstance() {
        home = new WeakReference<>(new Home(context, inflater, scCallback));
        event = new WeakReference<>(new Event(context, inflater, scCallback));
        magazine = new WeakReference<>(new Magazine(context, inflater, scCallback));
        now = new WeakReference<>(new Now(context, inflater, scCallback));
        outlet = new WeakReference<>(new Outlet(context, inflater, scCallback));
        pick = new WeakReference<>(new Pick(context, inflater, scCallback));
        plan = new WeakReference<>(new Plan(context, inflater, scCallback));
    }

       /* fragmentList.add(new HomeFragment());
        fragmentList.add(new EditorFragment());
        fragmentList.add(new EventFragment());
        fragmentList.add(new PlanFragment());
        fragmentList.add(new OutletFragment());
        fragmentList.add(new NowFragment());
        fragmentList.add(new MagazineFragment());*/


    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    public int getCenterPos() {
        return getCount() / 2;
    }

    public int getRealPos(int pos){

        return pos % tabData.tabsInfo.size();

    }
    @Override
    public int getCount() {
        return tabData.tabsInfo.size() * INFINTE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public Home getHome() {
        return home.get();
    }
}