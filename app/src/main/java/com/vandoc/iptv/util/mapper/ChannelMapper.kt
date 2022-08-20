package com.vandoc.iptv.util.mapper

import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.data.model.local.ChannelPaging
import com.vandoc.iptv.data.model.response.ChannelMiniResponse
import com.vandoc.iptv.data.model.response.ChannelPagingResponse

/**
 * @author Ichvandi
 * Created on 19/08/2022 at 21:31.
 */
fun ChannelPagingResponse.toLocalModel(): ChannelPaging = ChannelPaging(
    channels = channels.map { it.toLocalModel() },
    nextCursor = nextCursor
)

fun ChannelMiniResponse.toLocalModel(): ChannelMini = ChannelMini(
    id = id,
    name = name,
    isNsfw = isNsfw,
    logo = logo,
    country = country,
    flag = flag,
    categories = categories
)
