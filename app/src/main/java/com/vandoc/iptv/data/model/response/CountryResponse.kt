package com.vandoc.iptv.data.model.response


import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("idLanguage")
    val idLanguage: String,
    @SerializedName("idRegion")
    val idRegion: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("flag")
    val flag: String
)