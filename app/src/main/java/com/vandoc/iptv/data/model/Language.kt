package com.vandoc.iptv.data.model

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("code")
    val id: String,
    @SerializedName("name")
    val name: String
)