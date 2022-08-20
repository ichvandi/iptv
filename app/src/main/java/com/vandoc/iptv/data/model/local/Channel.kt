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
