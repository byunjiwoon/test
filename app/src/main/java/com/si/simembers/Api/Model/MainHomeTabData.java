package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byun on 2018-03-29.
 */

public class MainHomeTabData {

    @SerializedName("normalBanner")
    public List<NormalBanner> normalBanner = new ArrayList();

    public class NormalBanner {
        @SerializedName("index")
        public int index;
        @SerializedName("imgPath")
        public String imgPath;
        @SerializedName("txt")
        public String txt;
        @SerializedName("subTxt")
        public String subTxt;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("spsNo")
        public String spsNo;
    }

    @SerializedName("staffBanner")
    public List<StaffBanner> staffBanner = new ArrayList();

    public class StaffBanner {
        @SerializedName("index")
        public int index;
        @SerializedName("imgPath")
        public String imgPath;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("txt")
        public String txt;
        @SerializedName("spsNo")
        public String spsNo;
    }

    @SerializedName("bestSelling")
    public List<BestSelling> bestSelling;

    public class BestSelling {
        @SerializedName("tab")
        public List<BestSellingItems> tab;
        @SerializedName("moreBestItemUrl")
        public String moreBestItemUrl;
    }

    public class BestSellingItems {
        @SerializedName("soldOutYn")
        public String soldOutYn;
        @SerializedName("brndNm")
        public String brndNm;
        @SerializedName("realSalePrc")
        public int realSalePrc;
        @SerializedName("imgPath")
        public String imgPath;
        @SerializedName("index")
        public int index;
        @SerializedName("nmlPrc")
        public int nmlPrc;
        @SerializedName("rank")
        public int rank;
        @SerializedName("empPrc")
        public int empPrc;
        @SerializedName("productNm")
        public String productNm;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("productSct")
        public String productSct;
        @SerializedName("saleYn")
        public String saleYn;
    }

    @SerializedName("myBrandCnt")
    public int myBrandCnt;
    @SerializedName("recommendBrandCnt")
    public int recommendBrandCnt;
    @SerializedName("recommendBrand")
    public List<BrandInfo> recommendBrand;
    @SerializedName("myBrand")
    public List<BrandInfo> myBrand;

    public class BrandInfo {
        @SerializedName("index")
        public int index;
        @SerializedName("imgPath")
        public String bnrImgPath;
        @SerializedName("childItem")
        public List<ChildItem> child;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
    }

    public class ChildItem {
        @SerializedName("soldOutYn")
        public String soldOutYn;
        @SerializedName("productSct")
        public String productSct;
        @SerializedName("brndNm")
        public String brndNm;
        @SerializedName("realSalePrc")
        public int realSalePrc;
        @SerializedName("imgPath")
        public String imgPath;
        @SerializedName("index")
        public int index;
        @SerializedName("nmlPrc")
        public int nmlPrc;
        @SerializedName("empPrc")
        public int empPrc;
        @SerializedName("productNm")
        public String productNm;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("saleYn")
        public String saleYn;
    }

}
