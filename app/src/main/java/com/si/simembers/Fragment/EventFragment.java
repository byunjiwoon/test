package com.si.simembers.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.simembers.R;
import com.si.simembers.databinding.ViewMenuEventBinding;
import com.si.simembers.databinding.ViewMenuHomeBinding;

/**
 * Created by byun on 2018-03-19.
 */

public class EventFragment extends Fragment {


    public static EventFragment eventFragment;

    public static EventFragment newInstance(){

        if(eventFragment == null){

            eventFragment  = new EventFragment();
            return eventFragment;
        }

        return eventFragment;
    }


    ViewMenuEventBinding Binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Binding = DataBindingUtil.inflate(inflater, R.layout.view_menu_event, container, false);
        return  Binding.getRoot();

    }
}
