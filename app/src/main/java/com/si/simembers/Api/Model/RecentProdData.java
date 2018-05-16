package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentProdData {

    @SerializedName("recentlyProductsList")
    public List<RecentProd> prodList;

    public class RecentProd {
        @SerializedName("index")
        public int index;
        @SerializedName("productNo")
        public String productNo;
        @SerializedName("productNm")
        public String productNm;
        @SerializedName("brndNm")
        public String brndNm;
        @SerializedName("nmlPrc")
        public int nmlPrc;
        @SerializedName("soldOutYn")
        public String soldOutYn;
        @SerializedName("realSalePrc")
        public int realSalePrc;
        @SerializedName("imgPath")
        public String imgPath;
        @SerializedName("empPrc")
        public int empPrc;
        @SerializedName("benefitSalePrc")
        public int benefitSalePrc;
        @SerializedName("benefitSaleYn")
        public String benefitSaleYn;
        @SerializedName("empBenefitSaleYn")
        public String empBenefitSaleYn;
        @SerializedName("imgUrlMovePath")
        public String imgUrlMovePath;
        @SerializedName("productSct")
        public String productSct;
    }

}
