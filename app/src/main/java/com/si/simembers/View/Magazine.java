package com.si.simembers.View;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;

import com.si.simembers.R;
import com.si.simembers.databinding.ViewMenuEventBinding;

/**
 * Created by byun on 2018-04-07.
 */

public class Magazine extends View {

    private View v;
    public ViewMenuEventBinding b;

    public Magazine(Context context, LayoutInflater inflater, NestedScrollView.OnScrollChangeListener scCallback) {
        super(context);

        v =  inflater.inflate(R.layout.view_menu_magazine, null, false);
}

    public View getView() {

        return v;
    }
}
