package com.vandoc.iptv.ui.home

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.IPTVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 10:31.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IPTVRepository
) : BaseComposeViewModel<HomeAction, HomeState, HomeEvent>(HomeState()) {

    init {
        setAction(
            HomeAction.GetChannels(
                query = mapOf(
                    "page" to "1",
                    "size" to "10",
                    "has_url" to "true",
                    "msgpack" to "0"
                )
            )
        )
    }

    override fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.GetChannels -> getChannels(action.query)
        }
    }

    private fun getChannels(query: Map<String, String>) = viewModelScope.launch {
        repository.getChannels(query).collect { response ->
            when (response) {
                is Resource.Loading -> {
                    setState {
                        copy(isLoading = true)
                    }
                }
                is Resource.Success -> {
                    setState {
                        copy(
                            isLoading = false,
                            channels = response.data.orEmpty()
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

}