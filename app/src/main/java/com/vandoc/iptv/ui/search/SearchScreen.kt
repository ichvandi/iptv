package com.vandoc.iptv.ui.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.vandoc.iptv.ui.components.SearchBar
import com.vandoc.iptv.ui.destinations.DetailScreenDestination
import com.vandoc.iptv.util.TOOLBAR_HEIGHT_IN_DP
import kotlin.math.roundToInt

/**
 * @author Ichvandi
 * Created on 09/07/2022 at 17:07.
 */
@RootNavGraph
@Destination
@Composable
fun SearchScreen(
    query: String,
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var queryState by rememberSaveable { mutableStateOf(query) }

    LaunchedEffect(queryState) {
        viewModel.setAction(SearchAction.Search(queryState))
    }

    val uiState = viewModel.uiState
    CoordinatorLayout(
        toolbarHeight = TOOLBAR_HEIGHT_IN_DP,
        toolbar = { toolbarOffsetHeightPx ->
            TopAppBar(
                title = {
                    SearchBar(
                        initialValue = queryState,
                        hint = "Search channel name",
                        onSearch = { queryState = it },
                        modifier = Modifier.fillMaxWidth()
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
                    onItemClicked = { navigator.navigate(DetailScreenDestination(it)) },
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
