package com.vandoc.iptv.data.model.local


data class ChannelPaging(
    val channels: List<ChannelMini>,
    val nextCursor: String?
)

data class ChannelMini(
    val id: String,
    val name: String,
    val isNsfw: Boolean,
    val logo: String,
    val country: String,
    val flag: String,
    val categories: List<String>
)

data class Channel(
    val id: String,
    val name: String,
    val nativeName: String?,
    val network: String?,
    val launched: String?,
    val closed: String?,
    val isNsfw: Boolean,
    val replacedBy: String?,
    val website: String?,
    val logo: String,
    val region: String,
    val country: String,
    val flag: String,
    val subdivision: String?,
    val broadcastAreas: BroadcastAreas,
    val categories: List<String>,
    val languages: List<String>,
    val streams: List<Stream>,
    val guides: List<Guide>
) {
    data class BroadcastAreas(
        val region: List<String>,
        val country: List<String>,
        val subdivision: List<String>
    )

    data class Stream(
        val url: String,
        val status: String,
        val width: Int,
        val height: Int,
        val resolution: String
    )

    data class Guide(
        val url: String,
        val website: String,
        val language: String
    )
}
