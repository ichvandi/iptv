package com.vandoc.iptv.ui.search

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.AppDispatcher
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.IPTVRepository
import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.request.SearchChannelsRequest
import com.vandoc.iptv.util.moveToFirst
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
            is SearchAction.Filter -> filterChannels(
                action.languageIndex,
                action.categoryIndex,
                action.regionIndex,
                action.countryIndex,
                action.subdivisionIndex
            )
            is SearchAction.SearchFilter -> when (action.type) {
                "Languages" -> searchLanguages(action.query)
                "Categories" -> searchCategories(action.query)
                "Regions" -> searchRegions(action.query)
                "Countries" -> searchCountries(action.query)
                "Subdivisions" -> searchSubdivisions(action.query)
            }
            is SearchAction.SearchSelect -> {
                if (action.item == null) return
                when (action.type) {
                    "Languages" -> setState {
                        copy(languageFilter = languageFilter.moveToFirst(action.item as Language))
                    }
                    "Categories" -> setState {
                        copy(categoryFilter = categoryFilter.moveToFirst(action.item as Category))
                    }
                    "Regions" -> setState {
                        copy(regionFilter = regionFilter.moveToFirst(action.item as Region))
                    }
                    "Countries" -> setState {
                        copy(countryFilter = countryFilter.moveToFirst(action.item as Country))
                    }
                    "Subdivisions" -> setState {
                        copy(subdivisionFilter = subdivisionFilter.moveToFirst(action.item as Subdivision))
                    }
                }
            }
        }
    }

    private fun searchChannels(query: String) = viewModelScope.launch {
        val request = SearchChannelsRequest(
            name = query,
            limit = 25
        )

        setState { copy(request = request) }

        repository.searchChannels(request).collect { response ->
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

    private fun filterChannels(
        languageIndex: Int?,
        categoryIndex: Int?,
        regionIndex: Int?,
        countryIndex: Int?,
        subdivisionIndex: Int?
    ) = viewModelScope.launch(appDispatcher.main) {
        val request = uiState.request?.copy(
            language = if (languageIndex != null) uiState.languageFilter[languageIndex].id else null,
            category = if (categoryIndex != null) uiState.categoryFilter[categoryIndex].id else null,
            region = if (regionIndex != null) uiState.regionFilter[regionIndex].id else null,
            country = if (countryIndex != null) uiState.countryFilter[countryIndex].id else null,
            subdivision = if (subdivisionIndex != null) uiState.subdivisionFilter[subdivisionIndex].id else null
        ) ?: return@launch

        repository.searchChannels(request).collect { response ->
            setState { copy(isLoading = response == Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(channels = response.data?.channels.orEmpty()) }
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

    private fun searchLanguages(query: String) = viewModelScope.launch(appDispatcher.main) {
        repository.searchLanguages(query).collect { response ->
            setState { copy(isLoading = response is Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(searchFilters = response.data.orEmpty()) }
            }
        }
    }

    private fun searchCategories(query: String) = viewModelScope.launch(appDispatcher.main) {
        repository.searchCategories(query).collect { response ->
            setState { copy(isLoading = response is Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(searchFilters = response.data.orEmpty()) }
            }
        }
    }

    private fun searchRegions(query: String) = viewModelScope.launch(appDispatcher.main) {
        repository.searchRegions(query).collect { response ->
            setState { copy(isLoading = response is Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(searchFilters = response.data.orEmpty()) }
            }
        }
    }

    private fun searchCountries(query: String) = viewModelScope.launch(appDispatcher.main) {
        repository.searchCountries(query).collect { response ->
            setState { copy(isLoading = response is Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(searchFilters = response.data.orEmpty()) }
            }
        }
    }

    private fun searchSubdivisions(query: String) = viewModelScope.launch(appDispatcher.main) {
        repository.searchSubdivisions(query).collect { response ->
            setState { copy(isLoading = response is Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(searchFilters = response.data.orEmpty()) }
            }
        }
    }

}