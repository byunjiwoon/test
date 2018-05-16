package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byun on 2018-03-29.
 */

public class SlideMenuData {
    @SerializedName("menuCategory")
    public List<MenuCategory> category = new ArrayList<>();

    public class MenuCategory {
        @SerializedName("categoryList")
        public List<MenuCategory> categoryList;
        @SerializedName("dspCatNm")
        public String dspCatNm;
        @SerializedName("dspCatNo")
        public String dspCatNo;
        @SerializedName("index")
        public int index;
        @SerializedName("moUrlMove")
        public String moUrlMove;
    }

    @SerializedName("menuBrand")
    public MenuBrand menuBrand;

    public class MenuBrand {
        @SerializedName("fashion")
        public List<MenuBrandChild> fashion;
        @SerializedName("beauty")
        public List<MenuBrandChild> beauty;
        @SerializedName("living")
        public List<MenuBrandChild> living;
        @SerializedName("myBrand")
        public List<String> myBrand;
    }

    public class MenuBrandChild {
        @SerializedName("brandhallList")
        public List<MenuBrandChild> brandhallList;
        @SerializedName("brndDspCatNo")
        public String brndDspCatNo;
        @SerializedName("brndHallCd")
        public String brndHallCd;
        @SerializedName("brndHallNm")
        public String brndHallNm;
        @SerializedName("index")
        public int index;
        @SerializedName("urlMove")
        public String urlMove;
    }
}
