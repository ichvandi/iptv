package com.vandoc.iptv.ui.search

import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.request.SearchChannelsRequest

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 19:06.
 */
sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
    data class Filter(
        val languageIndex: Int?,
        val categoryIndex: Int?,
        val regionIndex: Int?,
        val countryIndex: Int?,
        val subdivisionIndex: Int?
    ) : SearchAction()

    data class SearchFilter(val query: String, val type: String) : SearchAction()
    data class SearchSelect(val item: Any?, val type: String) : SearchAction()
}

data class SearchState(
    val request: SearchChannelsRequest? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val channels: List<ChannelMini> = emptyList(),
    val languageFilter: List<Language> = emptyList(),
    val categoryFilter: List<Category> = emptyList(),
    val regionFilter: List<Region> = emptyList(),
    val countryFilter: List<Country> = emptyList(),
    val subdivisionFilter: List<Subdivision> = emptyList(),
    val searchFilters: List<Any> = emptyList()
)

sealed class SearchEvent {
    data class ShowToast(val message: String) : SearchEvent()
}
