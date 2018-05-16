package com.si.simembers.View;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;

import com.si.simembers.R;

/**
 * Created by byun on 2018-04-07.
 */

public class Outlet extends View{


    private View v;

    public Outlet(Context context, LayoutInflater inflater, NestedScrollView.OnScrollChangeListener scrollChangeListener) {
        super(context);

        v = inflater.inflate(R.layout.view_menu_outlet, null, false);
    }

    public View getView() {
        return v;
    }
}
