package com.si.simembers.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.simembers.R;
import com.si.simembers.Widget.HomeBannerPagerAdapter;
import com.si.simembers.databinding.ViewMenuHomeBinding;
import com.si.simembers.databinding.ViewMenuMagazineBinding;

/**
 * Created by byun on 2018-03-19.
 */

public class MagazineFragment extends Fragment {

    public static MagazineFragment magazineFragment;



    public static MagazineFragment newInstance(){

        if(magazineFragment == null){

            magazineFragment  = new MagazineFragment();


            return magazineFragment;
        }

        return magazineFragment;
    }


    ViewMenuMagazineBinding Binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Binding = DataBindingUtil.inflate(inflater, R.layout.view_menu_magazine, container, false);
        return  Binding.getRoot();

    }
}
