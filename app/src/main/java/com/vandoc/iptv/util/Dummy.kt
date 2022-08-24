package com.vandoc.iptv.util

import com.vandoc.iptv.data.model.local.Channel
import com.vandoc.iptv.data.model.local.ChannelMini

/**
 * @author Ichvandi
 * Created on 10/07/2022 at 08:43.
 */
val DUMMY_CHANNELS = List(2) {
    ChannelMini(
        id = "$it",
        name = "Channel $it",
        isNsfw = it == 0,
        logo = "https://www.lyngsat.com/logo/tv/ii/insert_id.png",
        country = "Country $it",
        flag = "https://flagicons.lipis.dev/flags/1x1/id.svg",
        categories = listOf("Animation", "Comedy")
    )
}

const val DUMMY_SECTION = "Stream all channels"

val DUMMY_CHANNELS = List(2) {
    Channel(
        id = "$it",
        name = "Channel $it",
        nativeName = "Channel Native $it",
        network = "Network $it",
        launched = "2022-01-24",
        closed = null,
        isNsfw = it == 0,
        replacedBy = null,
        website = "https://google.com/",
        logo = "https://www.lyngsat.com/logo/tv/ii/insert_id.png",
        region = "Region $it",
        country = "Country $it",
        flag = "https://raw.githubusercontent.com/emcrisostomo/flags/master/png/256/SG.png",
        subdivision = "Subdivision $it",
        broadcastAreas = Channel.BroadcastAreas(
            region = listOf("A", "B", "C"),
            country = listOf("A", "B", "C"),
            subdivision = listOf("A", "B", "C"),
        ),
        categories = listOf("Cat 1", "Cat 2", "Cat 3"),
        languages = listOf("Lang 1", "Lang 2", "Lang 3"),
        streams = listOf(
            Channel.Stream(
                url = "",
                status = "online",
                width = 1920,
                height = 1080,
                resolution = "FHD",
            ),
            Channel.Stream(
                url = "",
                status = "timeout",
                width = 1920,
                height = 1080,
                resolution = "FHD",
            ),
            Channel.Stream(
                url = "",
                status = "online",
                width = 1280,
                height = 720,
                resolution = "HD",
            )
        ),
        guides = listOf(
            Channel.Guide(
                url = "",
                website = "",
                language = "English"
            ),
            Channel.Guide(
                url = "",
                website = "",
                language = "English"
            ),
            Channel.Guide(
                url = "",
                website = "",
                language = "English"
            )
        )
    )
}
