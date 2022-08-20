package com.vandoc.iptv.ui.discover

import com.vandoc.iptv.data.model.request.SearchChannelsRequest

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 16:53.
 */
data class DiscoverArgument(
    val section: String,
    val query: SearchChannelsRequest
)