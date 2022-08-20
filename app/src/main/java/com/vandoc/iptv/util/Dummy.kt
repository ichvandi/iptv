package com.vandoc.iptv.util

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
