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
import com.si.simembers.databinding.ViewMenuPlanBinding;

/**
 * Created by byun on 2018-03-19.
 */

public class PlanFragment extends Fragment {


    public static PlanFragment planFragment;

    public static PlanFragment newInstance(){

        if(planFragment == null){
            planFragment  = new PlanFragment();
            return planFragment;
        }
        return planFragment;
    }

    ViewMenuPlanBinding Binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Binding = DataBindingUtil.inflate(inflater, R.layout.view_menu_plan, container, false);
        return  Binding.getRoot();

    }
}
