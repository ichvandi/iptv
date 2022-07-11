package com.vandoc.iptv.ui.discover

import com.vandoc.iptv.data.model.Channel

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 16:39.
 */
sealed class DiscoverAction {
    data class Discover(val query: Map<String, String>) : DiscoverAction()
}

data class DiscoverState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val channels: List<Channel> = emptyList()
)

sealed class DiscoverEvent {
    data class ShowToast(val message: String) : DiscoverEvent()
}
