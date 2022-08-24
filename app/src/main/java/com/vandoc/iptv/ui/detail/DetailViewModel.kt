package com.vandoc.iptv.ui.detail

import androidx.lifecycle.viewModelScope
import com.vandoc.iptv.base.BaseComposeViewModel
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.IPTVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 24/08/2022 at 13:55.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: IPTVRepository
) : BaseComposeViewModel<DetailAction, DetailState, Unit>(DetailState()) {

    override fun handleAction(action: DetailAction) {
        when (action) {
            is DetailAction.GetDetail -> getChannelDetail(action.channelId)
        }
    }

    private fun getChannelDetail(channelId: String) = viewModelScope.launch {
        repository.getChannelDetail(channelId).collect { response ->
            setState { copy(isLoading = response == Resource.Loading) }
            if (response is Resource.Success) {
                setState { copy(channel = response.data) }
            }
        }
    }

}