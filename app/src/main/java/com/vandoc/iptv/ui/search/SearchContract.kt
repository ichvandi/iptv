package com.vandoc.iptv.ui.search

import com.vandoc.iptv.data.model.local.ChannelMini

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 19:06.
 */
sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
}

data class SearchState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val channels: List<ChannelMini> = emptyList()
)

sealed class SearchEvent {
    data class ShowToast(val message: String) : SearchEvent()
}
