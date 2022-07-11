package com.vandoc.iptv.ui.player

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.vandoc.iptv.ui.components.Player
import com.vandoc.iptv.util.LockScreenOrientation

/**
 * @author Ichvandi
 * Created on 02/07/2022 at 20:02.
 */
@RootNavGraph
@Destination
@Composable
fun PlayerScreen(
    streamUrls: Array<String>,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    Player(
        streamUrls = streamUrls,
        onStreamSuccess = { viewModel.setAction(PlayerAction.StreamSuccess("")) },
        onStreamFailed = { viewModel.setAction(PlayerAction.StreamFailed("")) }
    )
}
