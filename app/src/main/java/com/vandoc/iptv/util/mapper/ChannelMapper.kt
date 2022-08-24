package com.vandoc.iptv.util.mapper

import com.vandoc.iptv.data.model.local.Channel
import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.data.model.local.ChannelPaging
import com.vandoc.iptv.data.model.response.ChannelMiniResponse
import com.vandoc.iptv.data.model.response.ChannelPagingResponse
import com.vandoc.iptv.data.model.response.ChannelResponse

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

fun ChannelResponse.toLocalModel(): Channel = Channel(
    id = id,
    name = name,
    nativeName = nativeName,
    network = network,
    launched = launched,
    closed = closed,
    isNsfw = isNsfw,
    replacedBy = replacedBy,
    website = website,
    logo = logo,
    region = region,
    country = country,
    flag = flag,
    subdivision = subdivision,
    broadcastAreas = Channel.BroadcastAreas(
        region = broadcastAreas.region,
        country = broadcastAreas.country,
        subdivision = broadcastAreas.subdivision,
    ),
    categories = categories,
    languages = languages,
    streams = streams.map { stream ->
        Channel.Stream(
            url = stream.url,
            status = stream.status,
            width = stream.width,
            height = stream.height,
            resolution = stream.resolution,
        )
    },
    guides = guides.map { guide ->
        Channel.Guide(
            url = guide.url,
            website = guide.website,
            language = guide.language
        )
    }
)
