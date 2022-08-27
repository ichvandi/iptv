package com.vandoc.iptv.data.model.response


import com.google.gson.annotations.SerializedName

data class SubdivisionResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("idCountry")
    val idCountry: String,
    @SerializedName("name")
    val name: String
)