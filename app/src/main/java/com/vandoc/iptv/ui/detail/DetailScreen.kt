package com.vandoc.iptv.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.ui.components.*
import com.vandoc.iptv.util.DUMMY_CHANNELS
import com.vandoc.iptv.util.getDynamicSize
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * @author Ichvandi
 * Created on 20/08/2022 at 21:24.
 */
@RootNavGraph
@Destination
@Composable
fun DetailScreen(
    channel: ChannelMini,
    viewModel: DetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val tabItems = remember {
        listOf(
            TabItem(
                title = "Detail",
                icon = Icons.Outlined.Info,
                selectedIcon = Icons.Filled.Info
            ),
            TabItem(
                title = "Stream",
                icon = Icons.Outlined.LiveTv,
                selectedIcon = Icons.Filled.LiveTv
            ),
            TabItem(
                title = "Guide",
                icon = Icons.Outlined.Description,
                selectedIcon = Icons.Filled.Description
            )
        )
    }
    val toolbarState = rememberCollapsingToolbarScaffoldState()
    var tabItem by rememberSaveable { mutableStateOf(tabItems.first().title) }

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = toolbarState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            DetailToolbar(
                channel = channel,
                textSize = toolbarState.getDynamicSize(16, 24),
                imageSize = toolbarState.getDynamicSize(46, 68),
                columnPadding = toolbarState.getDynamicSize(16, 8),
                onNavigateUp = { navigator.navigateUp() }
            )
        }
    ) {
        Column {
            TabLayout(tabs = tabItems, onTabSelected = { _, item -> tabItem = item.title })
            when (tabItem) {
                "Detail" -> DetailTab(channel = DUMMY_CHANNELS.first())
                "Stream" -> StreamTab(streams = DUMMY_CHANNELS.first().streams)
                "Guide" -> GuideTab(guides = DUMMY_CHANNELS.first().guides)
            }
        }
    }
}
