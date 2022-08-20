package com.vandoc.iptv.data.model.response


import com.google.gson.annotations.SerializedName

data class ChannelPagingResponse(
    @SerializedName("channels")
    val channels: List<ChannelMiniResponse>,
    @SerializedName("next_cursor")
    val nextCursor: String?
)

data class ChannelMiniResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("isNsfw")
    val isNsfw: Boolean,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("category")
    val categories: List<String>
)
