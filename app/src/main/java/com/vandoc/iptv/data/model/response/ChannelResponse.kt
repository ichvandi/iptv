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

data class ChannelResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nativeName")
    val nativeName: String?,
    @SerializedName("network")
    val network: String?,
    @SerializedName("launched")
    val launched: String?,
    @SerializedName("closed")
    val closed: String?,
    @SerializedName("isNsfw")
    val isNsfw: Boolean,
    @SerializedName("replacedBy")
    val replacedBy: String?,
    @SerializedName("website")
    val website: String?,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("subdivision")
    val subdivision: String?,
    @SerializedName("broadcastAreas")
    val broadcastAreas: BroadcastAreas,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("languages")
    val languages: List<String>,
    @SerializedName("streams")
    val streams: List<Stream>,
    @SerializedName("guides")
    val guides: List<Guide>
) {
    data class BroadcastAreas(
        @SerializedName("region")
        val region: List<String>,
        @SerializedName("country")
        val country: List<String>,
        @SerializedName("subdivision")
        val subdivision: List<String>
    )

    data class Stream(
        @SerializedName("url")
        val url: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("width")
        val width: Int,
        @SerializedName("height")
        val height: Int,
        @SerializedName("resolution")
        val resolution: String
    )

    data class Guide(
        @SerializedName("url")
        val url: String,
        @SerializedName("website")
        val website: String,
        @SerializedName("language")
        val language: String
    )
}
