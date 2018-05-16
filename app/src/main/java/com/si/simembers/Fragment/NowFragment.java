package com.si.simembers.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.simembers.R;
import com.si.simembers.databinding.ViewMenuHomeBinding;
import com.si.simembers.databinding.ViewMenuNowBinding;

/**
 * Created by byun on 2018-03-19.
 */

public class NowFragment extends Fragment {

    public static NowFragment nowFragment;

    public static NowFragment newInstance(){

        if(nowFragment == null){
            nowFragment  = new NowFragment();
            return nowFragment;
        }
        return nowFragment;
    }
    ViewMenuNowBinding Binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Binding = DataBindingUtil.inflate(inflater, R.layout.view_menu_now, container, false);
        return  Binding.getRoot();

    }
}
