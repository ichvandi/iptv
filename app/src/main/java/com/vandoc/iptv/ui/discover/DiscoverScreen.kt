package com.vandoc.iptv.ui.discover

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vandoc.iptv.R
import com.vandoc.iptv.ui.components.CoordinatorLayout
import com.vandoc.iptv.ui.components.GridChannel
import com.vandoc.iptv.ui.destinations.PlayerScreenDestination
import com.vandoc.iptv.util.TOOLBAR_HEIGHT_IN_DP
import kotlin.math.roundToInt
import androidx.compose.material3.MaterialTheme as MaterialTheme3

/**
 * @author Ichvandi
 * Created on 10/07/2022 at 15:21.
 */
@RootNavGraph
@Destination(navArgsDelegate = DiscoverArgument::class)
@Composable
fun DiscoverScreen(
    argument: DiscoverArgument,
    navigator: DestinationsNavigator,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    LaunchedEffect(argument) {
        viewModel.setAction(
            DiscoverAction.Discover(
                query = mapOf(
                    "page" to argument.page,
                    "size" to argument.size,
                    "has_url" to argument.hasUrl,
                    "msgpack" to argument.msgPack
                )
            )
        )
    }

    val uiState = viewModel.uiState

    CoordinatorLayout(
        toolbarHeight = TOOLBAR_HEIGHT_IN_DP,
        toolbar = { toolbarOffsetHeightPx ->
            TopAppBar(
                title = {
                    Text(
                        text = argument.section,
                        style = MaterialTheme3.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_chevron_left_24),
                            contentDescription = ""
                        )
                    }
                },
                modifier = Modifier
                    .height(TOOLBAR_HEIGHT_IN_DP)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = toolbarOffsetHeightPx.roundToInt()
                        )
                    }
            )
        },
        content = {
            if (uiState.channels.isNotEmpty()) {
                GridChannel(
                    channels = uiState.channels,
                    columns = GridCells.Fixed(2),
                    onItemClicked = { channel ->
                        navigator.navigate(
                            PlayerScreenDestination(
//                                channel.url.orEmpty().toTypedArray()
                                emptyArray()
                            )
                        )
                    },
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        top = TOOLBAR_HEIGHT_IN_DP + 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                )
            }
        }
    )
}
