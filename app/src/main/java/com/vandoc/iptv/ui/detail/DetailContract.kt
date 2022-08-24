package com.vandoc.iptv.ui.detail

import com.vandoc.iptv.data.model.local.Channel

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 16:39.
 */
sealed class DetailAction {
    data class GetDetail(val channelId: String) : DetailAction()
}

data class DetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val channel: Channel? = null
)
