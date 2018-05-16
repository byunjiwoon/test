package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dwpark on 2017. 6. 22..
 */

public class MainPickTabData {
    @SerializedName("mdPick")
    public List<Tab> pick;

    public class Tab {
        @SerializedName("tabNm")
        public String tabNm;
        @SerializedName("tabItem")
        public List<PickItem> tabItem;
    }

    public class PickItem {
        @SerializedName("productSct")
        public String productSct;
        @SerializedName("brndNm")
        public String brndNm;
        @SerializedName("empPrc")
        public int empPrc;
        @SerializedName("imgPath")
        public String imgPath;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("index")
        public int index;
        @SerializedName("mdOriPnt")
        public String mdOriPnt;
        @SerializedName("nmlPrc")
        public int nmlPrc;
        @SerializedName("productNm")
        public String productNm;
        @SerializedName("productTp")
        public String productTp;
        @SerializedName("promYn")
        public String promYn;
        @SerializedName("rank")
        public int rank;
        @SerializedName("realSalePrc")
        public int realSalePrc;
        @SerializedName("saleYn")
        public String saleYn;
        @SerializedName("npmInfo")
        public String npmInfo;
    }
}
