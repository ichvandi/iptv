package com.vandoc.iptv.ui.search

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.AppDispatcher
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.IPTVRepository
import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 19:05.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appDispatcher: AppDispatcher,
    private val repository: IPTVRepository
) : BaseComposeViewModel<SearchAction, SearchState, SearchEvent>(SearchState()) {

    init {
        getFilterData()
    }

    override fun handleAction(action: SearchAction) {
        when (action) {
            is SearchAction.Search -> searchChannels(action.query.replace("\n", "").trim())
        }
    }

    private fun searchChannels(query: String) = viewModelScope.launch {
        repository.searchChannels(
            SearchChannelsRequest(
                name = query,
                limit = 25
            )
        ).collect { response ->
            when (response) {
                is Resource.Loading -> setState {
                    copy(isLoading = true)
                }
                is Resource.Success -> {
                    setState {
                        copy(
                            isLoading = false,
                            channels = response.data?.channels.orEmpty()
                        )
                    }
                }
                is Resource.Error.Unknown -> {
                    setState {
                        copy(
                            isLoading = false,
                            errorMessage = response.message
                        )
                    }
                }
                else -> Unit
            }
        }
    }

    private fun getFilterData() = viewModelScope.launch {
        val languagesDefer = async(appDispatcher.io) {
            repository.getLanguages().toList()
                .filterIsInstance<Resource.Success<List<Language>>>()
                .firstOrNull()
                ?.data
                .orEmpty()
        }
        val categoriesDefer = async(appDispatcher.io) {
            repository.getCategories().toList()
                .filterIsInstance<Resource.Success<List<Category>>>()
                .firstOrNull()
                ?.data
                .orEmpty()
        }
        val regionsDefer = async(appDispatcher.io) {
            repository.getRegions().toList()
                .filterIsInstance<Resource.Success<List<Region>>>()
                .firstOrNull()
                ?.data
                .orEmpty()
        }
        val countriesDefer = async(appDispatcher.io) {
            repository.getCountries().toList()
                .filterIsInstance<Resource.Success<List<Country>>>()
                .firstOrNull()
                ?.data
                .orEmpty()
        }
        val subdivisionsDefer = async(appDispatcher.io) {
            repository.getSubdivisions().toList()
                .filterIsInstance<Resource.Success<List<Subdivision>>>()
                .firstOrNull()
                ?.data
                .orEmpty()
        }

        val languages = languagesDefer.await()
        val categories = categoriesDefer.await()
        val regions = regionsDefer.await()
        val countries = countriesDefer.await()
        val subdivisions = subdivisionsDefer.await()

        setState {
            copy(
                languageFilter = languages,
                categoryFilter = categories,
                regionFilter = regions,
                countryFilter = countries,
                subdivisionFilter = subdivisions
            )
        }
    }

}