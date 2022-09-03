package com.vandoc.iptv.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vandoc.iptv.R
import com.vandoc.iptv.ui.components.*
import com.vandoc.iptv.ui.destinations.DetailScreenDestination
import com.vandoc.iptv.util.TOOLBAR_HEIGHT_IN_DP
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
        skipHalfExpanded = true
    )

    LaunchedEffect(filterSheetState) {
        snapshotFlow { filterSheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                selectedLanguageIndex = currentLanguageIndex
                selectedCategoryIndex = currentCategoryIndex
                selectedRegionIndex = currentRegionIndex
                selectedCountryIndex = currentCountryIndex
                selectedSubdivisionIndex = currentSubdivisionIndex
            }
        }
    }

    var searchTitle by rememberSaveable { mutableStateOf("") }
    var selectedSearch by rememberSaveable { mutableStateOf<Any?>(null) }

    val searchFilterState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    LaunchedEffect(searchFilterState) {
        snapshotFlow { searchFilterState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                selectedSearch = null
            }
        }
    }

    LaunchedEffect(query) {
        if (viewModel.uiState.request == null) {
            viewModel.setAction(SearchAction.Search(query))
        }
    }

    SearchFilterModalBottomSheet(
        title = searchTitle,
        items = viewModel.uiState.searchFilters,
        selectedItem = selectedSearch,
        sheetState = searchFilterState,
        coroutineScope = scope,
        shouldEnableBackHandler = searchFilterState.isVisible,
        onTextChanged = { viewModel.setAction(SearchAction.SearchFilter(it, searchTitle)) },
        onItemSelected = { selectedSearch = if (it != selectedSearch) it else null },
        onBackClicked = { selectedSearch = null },
        onSelectClicked = {
            viewModel.setAction(SearchAction.SearchSelect(selectedSearch, searchTitle))

            when (searchTitle) {
                "Languages" -> selectedLanguageIndex = 0
                "Categories" -> selectedCategoryIndex = 0
                "Regions" -> selectedRegionIndex = 0
                "Countries" -> selectedCountryIndex = 0
                "Subdivisions" -> selectedSubdivisionIndex = 0
            }

            selectedSearch = null
            searchTitle = ""
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
            shouldEnableBackHandler = filterSheetState.isVisible && !searchFilterState.isVisible,
            onLanguageClicked = { index, _ ->
                selectedLanguageIndex =
                    if (selectedLanguageIndex == index) null else index
            },
            onViewAllLanguageClicked = {
                scope.launch {
                    searchTitle = "Languages"
                    selectedLanguageIndex?.let {
                        selectedSearch = viewModel.uiState.languageFilter[it]
                    }
                    viewModel.setAction(SearchAction.SearchFilter("", searchTitle))
                    searchFilterState.show()
                }
            },
            onCategoryClicked = { index, _ ->
                selectedCategoryIndex =
                    if (selectedCategoryIndex == index) null else index
            },
            onViewAllCategoryClicked = {
                scope.launch {
                    searchTitle = "Categories"
                    selectedCategoryIndex?.let {
                        selectedSearch = viewModel.uiState.categoryFilter[it]
                    }
                    viewModel.setAction(SearchAction.SearchFilter("", searchTitle))
                    searchFilterState.show()
                }
            },
            onRegionClicked = { index, _ ->
                selectedRegionIndex =
                    if (selectedRegionIndex == index) null else index
            },
            onViewAllRegionClicked = {
                scope.launch {
                    searchTitle = "Regions"
                    selectedRegionIndex?.let {
                        selectedSearch = viewModel.uiState.regionFilter[it]
                    }
                    viewModel.setAction(SearchAction.SearchFilter("", searchTitle))
                    searchFilterState.show()
                }
            },
            onCountryClicked = { index, _ ->
                selectedCountryIndex =
                    if (selectedCountryIndex == index) null else index
            },
            onViewAllCountryClicked = {
                scope.launch {
                    searchTitle = "Countries"
                    selectedCountryIndex?.let {
                        selectedSearch = viewModel.uiState.countryFilter[it]
                    }
                    viewModel.setAction(SearchAction.SearchFilter("", searchTitle))
                    searchFilterState.show()
                }
            },
            onSubdivisionClicked = { index, _ ->
                selectedSubdivisionIndex =
                    if (selectedSubdivisionIndex == index) null else index
            },
            onViewAllSubdivisionClicked = {
                scope.launch {
                    searchTitle = "Subdivisions"
                    selectedSubdivisionIndex?.let {
                        selectedSearch = viewModel.uiState.subdivisionFilter[it]
                    }
                    viewModel.setAction(SearchAction.SearchFilter("", searchTitle))
                    searchFilterState.show()
                }
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

                viewModel.setAction(
                    SearchAction.Filter(
                        currentLanguageIndex,
                        currentCategoryIndex,
                        currentRegionIndex,
                        currentCountryIndex,
                        currentSubdivisionIndex
                    )
                )
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
