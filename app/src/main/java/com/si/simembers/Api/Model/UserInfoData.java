package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by byun on 2018-03-29.
 */

public class UserInfoData {

    @SerializedName("appUserInfo")
    public AppUserInfo userInfo;

    public class AppUserInfo {
        @SerializedName("mbrNm")
        public String mbrNm;
        @SerializedName("empTp")
        public String empTp;
        @SerializedName("empYn")
        public String empYn;
        @SerializedName("ageGroup")
        public String ageGroup;
        @SerializedName("loginType")
        public String loginType;
        @SerializedName("certTp")
        public String certTp;
        @SerializedName("genTp")
        public String genTp;
        @SerializedName("intgMbrGrade")
        public String intgMbrGrade;
        @SerializedName("intgPushRcvYn")
        public String intgPushRcvYn;
    }
}
