package com.vandoc.iptv.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vandoc.iptv.R
import com.vandoc.iptv.ui.components.CoordinatorLayout
import com.vandoc.iptv.ui.components.FilterModalBottomSheet
import com.vandoc.iptv.ui.components.GridChannel
import com.vandoc.iptv.ui.components.SearchBar
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

    var selectedLanguageIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentLanguageIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    var selectedCategoryIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentCategoryIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    var selectedRegionIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentRegionIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    var selectedCountryIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentCountryIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    var selectedSubdivisionIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentSubdivisionIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    val hasFilter by remember {
        derivedStateOf {
            selectedLanguageIndex != null ||
                    selectedCategoryIndex != null ||
                    selectedRegionIndex != null ||
                    selectedCountryIndex != null ||
                    selectedSubdivisionIndex != null
        }
    }

    val filterSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = {
            selectedLanguageIndex = currentLanguageIndex
            selectedCategoryIndex = currentCategoryIndex
            selectedRegionIndex = currentRegionIndex
            selectedCountryIndex = currentCountryIndex
            selectedSubdivisionIndex = currentSubdivisionIndex
            true
        }
    }

    LaunchedEffect(query) {
        if (viewModel.uiState.request == null) {
            viewModel.setAction(SearchAction.Search(query))
        }
    }

    ModalBottomSheetLayout(
        sheetState = searchFilterState,
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
            SearchBar(
                hint = "Search",
                onSearch = {
                    viewModel.setAction(SearchAction.SearchFilter(it, "language"))
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                itemsIndexed(viewModel.uiState.languageFilter) { index, item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme3.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    if (index < viewModel.uiState.languageFilter.lastIndex) {
                        Divider(
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Select")
            }
        }
    ) {
        FilterModalBottomSheet(
            languages = Pair(viewModel.uiState.languageFilter, selectedLanguageIndex),
            categories = Pair(viewModel.uiState.categoryFilter, selectedCategoryIndex),
            regions = Pair(viewModel.uiState.regionFilter, selectedRegionIndex),
            countries = Pair(viewModel.uiState.countryFilter, selectedCountryIndex),
            subdivisions = Pair(viewModel.uiState.subdivisionFilter, selectedSubdivisionIndex),
            hasFilter = hasFilter,
            sheetState = filterSheetState,
            coroutineScope = scope,
            onLanguageClicked = { index, _ ->
                selectedLanguageIndex =
                    if (selectedLanguageIndex == index) null else index
            },
            onCategoryClicked = { index, _ ->
                selectedCategoryIndex =
                    if (selectedCategoryIndex == index) null else index
            },
            onRegionClicked = { index, _ ->
                selectedRegionIndex =
                    if (selectedRegionIndex == index) null else index
            },
            onCountryClicked = { index, _ ->
                selectedCountryIndex =
                    if (selectedCountryIndex == index) null else index
            },
            onSubdivisionClicked = { index, _ ->
                selectedSubdivisionIndex =
                    if (selectedSubdivisionIndex == index) null else index
            },
            onBackClicked = {
                selectedLanguageIndex = currentLanguageIndex
                selectedCategoryIndex = currentCategoryIndex
                selectedRegionIndex = currentRegionIndex
                selectedCountryIndex = currentCountryIndex
                selectedSubdivisionIndex = currentSubdivisionIndex
            },
            onFilterClicked = {
                currentLanguageIndex = selectedLanguageIndex
                currentCategoryIndex = selectedCategoryIndex
                currentRegionIndex = selectedRegionIndex
                currentCountryIndex = selectedCountryIndex
                currentSubdivisionIndex = selectedSubdivisionIndex
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
                                if (filterSheetState.isVisible) {
                                    filterSheetState.hide()
                                } else {
                                    filterSheetState.show()
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
