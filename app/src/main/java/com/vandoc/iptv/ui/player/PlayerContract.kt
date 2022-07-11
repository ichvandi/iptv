package com.vandoc.iptv.ui.player

/**
 * @author Ichvandi
 * Created on 07/06/2022 at 19:57.
 */

sealed class PlayerAction {
    data class StreamSuccess(val channelId: String) : PlayerAction()
    data class StreamFailed(val channelId: String) : PlayerAction()
}

sealed class PlayerState

sealed class PlayerEvent {
    data class ShowToast(val message: String) : PlayerEvent()
}
