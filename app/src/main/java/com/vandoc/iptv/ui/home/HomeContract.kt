package com.vandoc.iptv.ui.home

import com.vandoc.iptv.data.model.local.Section
import com.vandoc.iptv.data.model.request.SearchChannelsRequest

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:31.
 */
sealed class HomeAction {
    data class GetChannels(val sections: List<String>, val queries: List<SearchChannelsRequest>) :
        HomeAction()
}

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val sections: Pair<List<Section>, List<SearchChannelsRequest>> = Pair(emptyList(), emptyList())
)

sealed class HomeEvent {
    data class ShowToast(val message: String) : HomeEvent()
}
