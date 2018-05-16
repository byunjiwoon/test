package com.si.simembers.View;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;

import com.si.simembers.Api.Api;
import com.si.simembers.Api.ApiCallBack;
import com.si.simembers.R;
import com.si.simembers.databinding.ViewMenuMdpickBinding;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by byun on 2018-04-07.
 */

public class Pick extends View implements ApiCallBack.RetrofitCallback {

    private View v;
    public ViewMenuMdpickBinding b;



    private Context context;
    private int myPosition = 0;

    public Pick(Context context, LayoutInflater inflater, NestedScrollView.OnScrollChangeListener scrollChangeListener)
    {
        super(context);
        this.context = context;
        v = inflater.inflate(R.layout.view_menu_mdpick, null, false);
        b = ViewMenuMdpickBinding.bind(v);

    }

    public View getView() {
        return v;
    }


    @Override
    public void onSucc(Response response, String url) throws Exception {

    }

    @Override
    public void onFail(Response response, String url) throws Exception {

    }

    @Override
    public void onError(Call call, Throwable t, String url) throws Exception {

    }
}
