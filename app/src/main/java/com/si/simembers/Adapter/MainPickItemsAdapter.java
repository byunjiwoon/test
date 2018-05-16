package com.si.simembers.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MainPickItemsAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
