package com.vandoc.iptv.ui.search

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.data.IPTVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 19:05.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IPTVRepository
) : BaseComposeViewModel<SearchAction, SearchState, SearchEvent>(SearchState()) {

    override fun handleAction(action: SearchAction) {
        when (action) {
            is SearchAction.Search -> searchChannels(action.query)
        }
    }

    private fun searchChannels(query: String) = viewModelScope.launch {
//        repository.searchChannels(
//            mapOf(
//                "name" to query,
//                "page" to "1",
//                "size" to "1000",
//                "has_url" to "true",
//                "msgpack" to "0"
//            )
//        ).collect { response ->
//            when (response) {
//                is Resource.Loading -> setState {
//                    copy(isLoading = true)
//                }
//                is Resource.Success -> {
//                    setState {
//                        copy(
//                            isLoading = false,
//                            channels = response.data.orEmpty()
//                        )
//                    }
//                }
//                is Resource.Error.Unknown -> {
//                    setState {
//                        copy(
//                            isLoading = false,
//                            errorMessage = response.message
//                        )
//                    }
//                }
//                else -> Unit
//            }
//        }
    }

}