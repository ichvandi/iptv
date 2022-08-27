package com.vandoc.iptv.data.model.response


import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)