package com.vandoc.iptv.ui.discover

import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.data.model.request.SearchChannelsRequest

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 16:39.
 */
sealed class DiscoverAction {
    data class Discover(val query: SearchChannelsRequest) : DiscoverAction()
}

data class DiscoverState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val channels: List<ChannelMini> = emptyList()
)

sealed class DiscoverEvent {
    data class ShowToast(val message: String) : DiscoverEvent()
}
