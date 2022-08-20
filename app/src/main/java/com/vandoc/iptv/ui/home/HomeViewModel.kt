package com.vandoc.iptv.ui.home

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.AppDispatcher
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.IPTVRepository
import com.vandoc.iptv.data.model.local.Section
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import com.vandoc.iptv.util.getNetworkDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:31.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDispatcher: AppDispatcher,
    private val repository: IPTVRepository
) : BaseComposeViewModel<HomeAction, HomeState, HomeEvent>(HomeState()) {

    init {
        viewModelScope.launch(appDispatcher.main) {
            val networkResponse = withContext(appDispatcher.io) { getNetworkDetails() }
            val locale = Locale.getDefault()
            setAction(
                HomeAction.GetChannels(
                    sections = listOf(
                        "Stream by country ${networkResponse.country}",
                        "Stream by language ${locale.displayLanguage}",
                        "Stream by category Animation"
                    ),
                    queries = listOf(
                        SearchChannelsRequest(country = networkResponse.countryIso),
                        SearchChannelsRequest(language = locale.isO3Language),
                        SearchChannelsRequest(category = "animation")
                    )
                )
            )
        }
    }

    override fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.GetChannels -> getSectionChannels(action.sections, action.queries)
        }
    }

    private fun getSectionChannels(
        sections: List<String>,
        queries: List<SearchChannelsRequest>
    ) = viewModelScope.launch {
        val sectionChannels = mutableListOf<Section>()
        val responses = queries.map {
            async(appDispatcher.io) { repository.searchChannels(it) }
        }.awaitAll()

        responses.forEachIndexed { index, response ->
            response.collect { resource ->
                setState { copy(isLoading = resource is Resource.Loading) }
                if (resource is Resource.Success) {
                    sectionChannels.add(
                        Section(
                            name = sections[index],
                            channels = resource.data?.channels.orEmpty()
                        )
                    )
                }
            }
        }

        setState { copy(sections = Pair(sectionChannels, queries)) }
    }

}