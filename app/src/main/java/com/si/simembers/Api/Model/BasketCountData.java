package com.si.simembers.Api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by byun on 2018-03-29.
 */

public class BasketCountData {

    @SerializedName("cartCount")
    public int cartCount;
    @SerializedName("result_code")
    public String resultCode;

}
