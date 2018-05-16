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
import com.si.simembers.databinding.ViewMenuOutletBinding;

/**
 * Created by byun on 2018-03-19.
 */

public class OutletFragment extends Fragment {

    public static OutletFragment outletFragment;

    public static OutletFragment newInstance(){

        if(outletFragment == null){
            outletFragment  = new OutletFragment();
            return outletFragment;
        }
        return outletFragment;
    }

    ViewMenuOutletBinding Binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Binding = DataBindingUtil.inflate(inflater, R.layout.view_menu_outlet, container, false);
        return  Binding.getRoot();

    }
}

