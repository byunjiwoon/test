package com.si.simembers.View;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;

import com.si.simembers.R;

/**
 * Created by byun on 2018-04-07.
 */

public class Plan extends View {

    private View  v;

    public Plan(Context context, LayoutInflater inflater, NestedScrollView.OnScrollChangeListener scrollChangeListener) {
        super(context);


        v = inflater.inflate(R.layout.view_menu_plan, null, false);


    }

    public View getView() {
        return v;
    }




}
