package com.si.simembers.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.si.simembers.Activity.SplashActivity;
import com.si.simembers.Api.Model.MainHomeTabData;
import com.si.simembers.Module.MGlide;
import com.si.simembers.R;
import com.si.simembers.SiApplication;

import java.util.List;

/**
 * Created by byun on 2018-03-21.
 */

public class ImageBannerFragment extends Fragment {


    ImageView bannerImage;

    public interface BannerCallBack{

        void setBanner();

    }

    private BannerCallBack bannerCallBack;


    public static ImageBannerFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("pos", position);
        ImageBannerFragment fragment = new ImageBannerFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.page_home_normal_banner, container, false);
        bannerImage = rootView.findViewById(R.id.ivBanner);

       return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int flag = getArguments().getInt("pos");

        MainHomeTabData mainHomeTabData = SiApplication.get().getHomeTabData();
        List<MainHomeTabData.NormalBanner> bannerList = mainHomeTabData.normalBanner;

        MainHomeTabData.NormalBanner data = bannerList.get(flag);
        String imgpath= data.imgPath;

        Glide.with(this).load(imgpath).into(bannerImage);



        /*switch (flag){
            case 0: bannerImage.setImageResource(R.drawable.banner1);break;
            case 1: bannerImage.setImageResource(R.drawable.banner2);break;
            case 2: bannerImage.setImageResource(R.drawable.banner3);break;
            case 3: bannerImage.setImageResource(R.drawable.banner4);break;
            case 4: bannerImage.setImageResource(R.drawable.banner5);break;
            case 5: bannerImage.setImageResource(R.drawable.banner6); break;
            case 6: bannerImage.setImageResource(R.drawable.banner7);break;
            case 7: bannerImage.setImageResource(R.drawable.banner8);break;
            case 8: bannerImage.setImageResource(R.drawable.banner9);break;
            case 9: bannerImage.setImageResource(R.drawable.banner10);break;
        }*/
    }
}
























