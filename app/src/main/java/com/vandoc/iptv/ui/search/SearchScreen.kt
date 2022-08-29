package com.vandoc.iptv.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.vandoc.iptv.ui.components.SectionFilter
import com.vandoc.iptv.ui.destinations.DetailScreenDestination
import com.vandoc.iptv.util.TOOLBAR_HEIGHT_IN_DP
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.material3.MaterialTheme as MaterialTheme3

/**
 * @author Ichvandi
 * Created on 09/07/2022 at 17:07.
 */
@OptIn(ExperimentalMaterialApi::class)
@RootNavGraph
@Destination
@Composable
fun SearchScreen(
    query: String,
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var shouldShowFAB by rememberSaveable { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val sheetState2 = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    var selectedLanguageIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedCategoryIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedRegionIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedCountryIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedSubdivisionIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    val hasFilter by remember {
        derivedStateOf {
            selectedLanguageIndex != null ||
                    selectedCategoryIndex != null ||
                    selectedRegionIndex != null ||
                    selectedCountryIndex != null ||
                    selectedSubdivisionIndex != null
        }
    }

    LaunchedEffect(query) {
        if (viewModel.uiState.request == null) {
            viewModel.setAction(SearchAction.Search(query))
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState2,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .size(width = 52.dp, height = 6.dp)
                    .background(
                        color = if (MaterialTheme.colors.isLight) Color(0xFF232323) else MaterialTheme.colors.surface,
                        shape = CircleShape
                    )
            )
            Text(
                text = "Search",
                style = MaterialTheme3.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetElevation = 8.dp,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContent = {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 8.dp)
                        .size(width = 52.dp, height = 6.dp)
                        .background(
                            color = if (MaterialTheme.colors.isLight) Color(0xFF232323) else MaterialTheme.colors.surface,
                            shape = CircleShape
                        )
                )
                Text(
                    text = "Filter",
                    style = MaterialTheme3.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        SectionFilter(
                            title = "Language",
                            contents = viewModel.uiState.languageFilter.take(10).map { it.name },
                            titleIcon = Icons.Outlined.Translate,
                            selectedItem = { index, _ -> selectedLanguageIndex == index },
                            onItemClicked = { index, _ ->
                                selectedLanguageIndex =
                                    if (selectedLanguageIndex == index) null else index
                            },
                            onViewAllClicked = {}
                        )
                    }
                    item {
                        SectionFilter(
                            title = "Category",
                            contents = viewModel.uiState.categoryFilter.take(10).map { it.name },
                            titleIcon = Icons.Outlined.Category,
                            selectedItem = { index, _ -> selectedCategoryIndex == index },
                            onItemClicked = { index, _ ->
                                selectedCategoryIndex =
                                    if (selectedCategoryIndex == index) null else index
                            },
                            onViewAllClicked = {}
                        )
                    }
                    item {
                        SectionFilter(
                            title = "Region",
                            contents = viewModel.uiState.regionFilter.take(10).map { it.name },
                            titleIcon = Icons.Outlined.Public,
                            selectedItem = { index, _ -> selectedRegionIndex == index },
                            onItemClicked = { index, _ ->
                                selectedRegionIndex =
                                    if (selectedRegionIndex == index) null else index
                            },
                            onViewAllClicked = {}
                        )
                    }
                    item {
                        SectionFilter(
                            title = "Country",
                            contents = viewModel.uiState.countryFilter.take(10).map { it.name },
                            titleIcon = Icons.Outlined.Public,
                            selectedItem = { index, _ -> selectedCountryIndex == index },
                            onItemClicked = { index, _ ->
                                selectedCountryIndex =
                                    if (selectedCountryIndex == index) null else index
                            },
                            onViewAllClicked = {}
                        )
                    }
                    item {
                        SectionFilter(
                            title = "Subdivision",
                            contents = viewModel.uiState.subdivisionFilter.take(10).map { it.name },
                            titleIcon = Icons.Outlined.Public,
                            selectedItem = { index, _ -> selectedSubdivisionIndex == index },
                            onItemClicked = { index, _ ->
                                selectedSubdivisionIndex =
                                    if (selectedSubdivisionIndex == index) null else index
                            },
                            onViewAllClicked = {}
                        )
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.setAction(
                                SearchAction.Filter(
                                    selectedLanguageIndex,
                                    selectedCategoryIndex,
                                    selectedRegionIndex,
                                    selectedCountryIndex,
                                    selectedSubdivisionIndex
                                )
                            )
                            sheetState.hide()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = hasFilter
                ) {
                    Text(text = "Filter")
                }
            }
        ) {
            Scaffold(
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = shouldShowFAB,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it + it / 2 })
                    ) {
                        FloatingActionButton(onClick = {
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                } else {
                                    sheetState.show()
                                }
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.FilterAlt, contentDescription = null)
                        }
                    }
                }
            ) {
                CoordinatorLayout(
                    toolbarHeight = TOOLBAR_HEIGHT_IN_DP,
                    toolbar = { toolbarOffsetHeightPx ->
                        shouldShowFAB = toolbarOffsetHeightPx == 0f || toolbarOffsetHeightPx > -100

                        TopAppBar(
                            title = {
                                SearchBar(
                                    initialValue = query,
                                    hint = "Search channel name",
                                    onSearch = { query ->
                                        viewModel.setAction(SearchAction.Search(query))
                                    },
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
                        if (viewModel.uiState.isLoading) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }

                        if (viewModel.uiState.channels.isNotEmpty()) {
                            GridChannel(
                                channels = viewModel.uiState.channels,
                                columns = GridCells.Fixed(2),
                                onItemClicked = { channel ->
                                    navigator.navigate(DetailScreenDestination(channel))
                                },
                                contentPadding = PaddingValues(
                                    start = 8.dp,
                                    top = TOOLBAR_HEIGHT_IN_DP + 8.dp,
                                    end = 8.dp,
                                    bottom = 8.dp
                                )
                            )
                        }
                    },
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}
