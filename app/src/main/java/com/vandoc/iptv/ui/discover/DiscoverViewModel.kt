package com.vandoc.iptv.ui.discover

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.data.IPTVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 16:39.
 */
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: IPTVRepository
) : BaseComposeViewModel<DiscoverAction, DiscoverState, DiscoverEvent>(DiscoverState()) {

    override fun handleAction(action: DiscoverAction) {
        when (action) {
            is DiscoverAction.Discover -> getChannels(action.query)
        }
    }

    private fun getChannels(query: Map<String, String>) = viewModelScope.launch {
//        repository.searchChannels(query).collect { response ->
//            when (response) {
//                is Resource.Loading -> setState {
//                    copy(isLoading = false)
//                }
//                is Resource.Success -> setState {
//                    copy(
//                        isLoading = false,
//                        channels = response.data.orEmpty()
//                    )
//                }
//                is Resource.Error.Unknown -> setState {
//                    copy(
//                        isLoading = false,
//                        errorMessage = response.message
//                    )
//                }
//                else -> Unit
//            }
//        }
    }

}