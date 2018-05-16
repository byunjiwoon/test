package com.si.simembers.Base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.bumptech.glide.Glide;

import com.si.simembers.Common.G;
import com.si.simembers.Module.CookiesManager;


/**
 * Created by dwpark on 2017. 7. 10..
 */

public class BaseApplication extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("byun", "BaseApplication: Application:Application:");

        //BaseApplication.getContext() = this;

        context = this;

        onFacebookSdkInitialized();
        onIgawSdkInitialized();
        onCriteoLaunchEvent();
    }

    public static Context getContext() {

        return context;
    }

    /**
     * @author dwpark
     */
    private void onFacebookSdkInitialized() {

    }

    /**
     * @author dwpark
     */
    private void onIgawSdkInitialized() {

    }

    private void onCriteoLaunchEvent() {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Glide.get(context).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Glide.get(context).trimMemory(level);
    }
}
