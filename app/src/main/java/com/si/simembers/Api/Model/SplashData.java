package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dwpark on 2017. 7. 10..
 */

public class SplashData {
    @SerializedName("result_code")
    public String hashCheck;
    @SerializedName("version_chk_yn")
    public String versionChkYn;
    @SerializedName("popup_show_yn")
    public String popupShowYn;
    @SerializedName("introImg")
    public List<Splash> splash;

    public class Splash {
        @SerializedName("dt")
        public String dt;
        @SerializedName("imageUrl")
        public String imageUrl;
    }
}
