package com.vandoc.iptv.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vandoc.iptv.ui.components.SearchBar
import com.vandoc.iptv.ui.components.SectionChannels
import com.vandoc.iptv.ui.destinations.DiscoverScreenDestination
import com.vandoc.iptv.ui.destinations.PlayerScreenDestination
import com.vandoc.iptv.ui.destinations.SearchScreenDestination
import com.vandoc.iptv.ui.discover.DiscoverArgument
import com.vandoc.iptv.util.observeWithLifecycle

/**
 * @author Ichvandi
 * Created on 28/06/2022 at 21:31.
 */
@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState

    viewModel.event.observeWithLifecycle {
        when (it) {
            is HomeEvent.ShowToast -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        SearchBar(
            hint = "Search channel name",
            onSearch = {
                navigator.navigate(SearchScreenDestination(it))
            },
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )

        if (uiState.channels.isNotEmpty()) {
            SectionChannels(
                section = "Stream all channels",
                channels = uiState.channels,
                onItemClicked = {
                    navigator.navigate(PlayerScreenDestination(it.url.orEmpty().toTypedArray()))
                },
                onViewAllClicked = {
                    navigator.navigate(
                        DiscoverScreenDestination(
                            DiscoverArgument(
                                section = it,
                                page = "1",
                                size = "1000",
                                hasUrl = "true",
                                msgPack = "0"
                            )
                        )
                    )
                }
            )
        }
    }
}
