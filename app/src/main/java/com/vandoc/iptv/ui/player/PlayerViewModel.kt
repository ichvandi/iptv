package com.vandoc.iptv.ui.player

import com.vandoc.iptv.base.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 07/06/2022 at 19:56.
 */
@HiltViewModel
class PlayerViewModel @Inject constructor() :
    BaseComposeViewModel<PlayerAction, Unit, PlayerEvent>(Unit) {

    override fun handleAction(action: PlayerAction) {
        when (action) {
            is PlayerAction.StreamFailed -> handleStreamFailed(action.channelId)
            is PlayerAction.StreamSuccess -> handleStreamSuccess(action.channelId)
        }
    }

    private fun handleStreamFailed(channelId: String) {
        Timber.e("Stream Failed: $channelId")
    }

    private fun handleStreamSuccess(channelId: String) {
        Timber.e("Stream Success: $channelId")
    }

}