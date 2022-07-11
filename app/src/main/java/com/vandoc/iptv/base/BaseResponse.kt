package com.vandoc.iptv.base

import com.google.gson.annotations.SerializedName

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:24.
 */
data class BaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("has_next")
    val hasNext: Boolean,
    @SerializedName("total_row")
    val totalRow: Int
)
