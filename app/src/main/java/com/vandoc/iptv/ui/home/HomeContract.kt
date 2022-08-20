package com.vandoc.iptv.ui.home

import com.vandoc.iptv.data.model.local.ChannelMini

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:31.
 */
sealed class HomeAction {
    data class GetChannels(val query: Map<String, String>) : HomeAction()
}

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val channels: List<ChannelMini> = emptyList()
)

sealed class HomeEvent {
    data class ShowToast(val message: String) : HomeEvent()
}
