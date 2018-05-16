package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byun on 2018-03-29.
 */



public class TabInfoData {
    @SerializedName("tabInfo")
    public List<Tabs> tabsInfo = new ArrayList<>();

    public class Tabs {
        @SerializedName("index")
        public int index;
        @SerializedName("txt")
        public String title;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("dspCatNo")
        public String dspCatNo;
        @SerializedName("scheme")
        public String scheme;
    }
}
