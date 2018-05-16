package com.si.simembers.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.si.simembers.Common.G;
import com.si.simembers.Common.Utils;
import com.si.simembers.Module.CookiesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by byun on 2018-03-28.
 */

public class ApiCallBack extends AsyncTask<Call<?>, Void, Void> {

    private RetrofitCallback callback;
    private boolean isIgnoreLoadingDialog;
    private Call<?> call;
    private String url;



    public ApiCallBack(Context context, RetrofitCallback callback) {
        Log.d("pppdw", "response-" + context.getClass().getSimpleName());
        apiCallBackInit(context, callback);
    }


    public ApiCallBack(Context context, RetrofitCallback callback, boolean isIgnoreLoadingDialog) {
        apiCallBackInit(context, callback);
    }


    private void apiCallBackInit(Context context, RetrofitCallback callback) {
        this.callback = callback;


    }
    @Override
    protected synchronized Void doInBackground(Call<?>... calls) {
        call = calls[0];
        if (call != null
                && call.request() != null
                && call.request().url() != null) {
            url = call.request().url().toString();
        }
        callback(call);
        return null;
    }

    public synchronized <T> void callback(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (callback == null) {
                    return;
                }

                if (response.isSuccessful()) {

                    if(response.body().toString().isEmpty()) {

                        Log.e("DWORKS","body is empty : " + G.MY_JSESSION_MO_ID);
                        Utils.saveJSession(response.headers().get("Set-Cookie"));

                        Log.e("DWORKS","response all cookie : " + response.headers().get("Set-Cookie"));
                        Log.e("DWORKS","response cookie : " + CookiesManager.getCookieForName("JSESSIONID_MO"));


                        try {
                            callback.onFail(response, url);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        try {
                            Log.e("DWORKS","request success : " + G.MY_JSESSION_MO_ID);
                            Utils.saveJSession(response.headers().get("Set-Cookie"));

                            Log.e("DWORKS","response all cookie : " + response.headers().get("Set-Cookie"));

                            Log.e("DWORKS","response cookie : " + CookiesManager.getCookieForName("JSESSIONID_MO"));


                            callback.onSucc(response, url);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    try {
                        callback.onFail(response, url);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (callback == null) {
                    return;
                }

                try {
                    callback.onError(call, t, url);
                } catch (Exception e) {
                }
            }
        });
    }
    public interface RetrofitCallback {
        void onSucc(Response response, String url) throws Exception;

        void onFail(Response response, String url) throws Exception;

        void onError(Call call, Throwable t, String url) throws Exception;
    }
}
