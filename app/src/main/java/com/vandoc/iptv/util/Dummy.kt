package com.vandoc.iptv.util

import com.vandoc.iptv.data.model.Category
import com.vandoc.iptv.data.model.Channel

/**
 * @author Ichvandi
 * Created on 10/07/2022 at 08:43.
 */
val DUMMY_CHANNELS = List(2) {
    Channel(
        "$it",
        "Channel $it",
        "Native Channel Name",
        "",
        "US",
        "",
        "",
        emptyList(),
        emptyList(),
        listOf(Category("1", "Genre 1"), Category("2", "Genre 2")),
        it == 0,
        "",
        "",
        "",
        "",
        "",
        emptyList(),
        if (it == 0) "online" else "error",
        "",
        "",
        "",
    )
}

const val DUMMY_SECTION = "Stream all channels"
