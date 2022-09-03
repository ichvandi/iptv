package com.vandoc.iptv.ui.components

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.ui.theme.IPTVTheme
import com.vandoc.iptv.util.debounce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme as MaterialTheme3

/**
 * @author Ichvandi
 * Created on 27/08/2022 at 10:38.
 */
@Composable
fun SectionFilter(
    title: String,
    contents: List<String>,
    titleIcon: ImageVector? = null,
    selectedItem: ((Int, String) -> Boolean)? = null,
    onItemClicked: ((Int, String) -> Unit)? = null,
    onViewAllClicked: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                titleIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme3.typography.titleMedium,
                    modifier = if (titleIcon != null) Modifier.padding(start = 8.dp) else Modifier
                )
            }
            TextButton(
                onClick = { onViewAllClicked?.invoke(title) },
                modifier = Modifier
                    .defaultMinSize(1.dp, 1.dp)
                    .height(30.dp),
                contentPadding = PaddingValues()
            ) {
                Text(text = "View All", style = MaterialTheme3.typography.bodyMedium)
            }
        }
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            itemsIndexed(contents) { index, content ->
                Text(
                    text = content,
                    style = MaterialTheme3.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .background(
                            color = if (MaterialTheme.colors.isLight)
                                MaterialTheme.colors.primary
                            else
                                Color(0xFF232323),
                            shape = CircleShape
                        )
                        .let {
                            if (selectedItem?.invoke(index, content) == true)
                                it.border(1.dp, Color.White, CircleShape)
                            else
                                it
                        }
                        .clip(CircleShape)
                        .clickable { onItemClicked?.invoke(index, content) }
                        .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterModalBottomSheet(
    modifier: Modifier = Modifier,
    languages: Pair<List<Language>, Int?>,
    onLanguageClicked: ((Int, Language) -> Unit)? = null,
    onViewAllLanguageClicked: (() -> Unit)? = null,
    categories: Pair<List<Category>, Int?>,
    onCategoryClicked: ((Int, Category) -> Unit)? = null,
    onViewAllCategoryClicked: (() -> Unit)? = null,
    regions: Pair<List<Region>, Int?>,
    onRegionClicked: ((Int, Region) -> Unit)? = null,
    onViewAllRegionClicked: (() -> Unit)? = null,
    countries: Pair<List<Country>, Int?>,
    onCountryClicked: ((Int, Country) -> Unit)? = null,
    onViewAllCountryClicked: (() -> Unit)? = null,
    subdivisions: Pair<List<Subdivision>, Int?>,
    onSubdivisionClicked: ((Int, Subdivision) -> Unit)? = null,
    onViewAllSubdivisionClicked: (() -> Unit)? = null,
    hasFilter: Boolean,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    shouldEnableBackHandler: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
    onFilterClicked: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    BackHandler(enabled = shouldEnableBackHandler) {
        coroutineScope.launch {
            sheetState.hide()
            onBackClicked?.invoke()
        }
    }

    BaseModalBottomSheet(
        title = "Filter",
        sheetState = sheetState,
        content = content,
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                SectionFilter(
                    title = "Language",
                    contents = languages.first.map { it.name },
                    titleIcon = Icons.Outlined.Translate,
                    selectedItem = { index, _ -> languages.second == index },
                    onItemClicked = { index, _ ->
                        onLanguageClicked?.invoke(index, languages.first[index])
                    },
                    onViewAllClicked = { onViewAllLanguageClicked?.invoke() }
                )
            }
            item {
                SectionFilter(
                    title = "Category",
                    contents = categories.first.map { it.name },
                    titleIcon = Icons.Outlined.Category,
                    selectedItem = { index, _ -> categories.second == index },
                    onItemClicked = { index, _ ->
                        onCategoryClicked?.invoke(index, categories.first[index])
                    },
                    onViewAllClicked = { onViewAllCategoryClicked?.invoke() }
                )
            }
            item {
                SectionFilter(
                    title = "Region",
                    contents = regions.first.map { it.name },
                    titleIcon = Icons.Outlined.Public,
                    selectedItem = { index, _ -> regions.second == index },
                    onItemClicked = { index, _ ->
                        onRegionClicked?.invoke(index, regions.first[index])
                    },
                    onViewAllClicked = { onViewAllRegionClicked?.invoke() }
                )
            }
            item {
                SectionFilter(
                    title = "Country",
                    contents = countries.first.map { it.name },
                    titleIcon = Icons.Outlined.Public,
                    selectedItem = { index, _ -> countries.second == index },
                    onItemClicked = { index, _ ->
                        onCountryClicked?.invoke(index, countries.first[index])
                    },
                    onViewAllClicked = { onViewAllCountryClicked?.invoke() }
                )
            }
            item {
                SectionFilter(
                    title = "Subdivision",
                    contents = subdivisions.first.map { it.name },
                    titleIcon = Icons.Outlined.Public,
                    selectedItem = { index, _ -> subdivisions.second == index },
                    onItemClicked = { index, _ ->
                        onSubdivisionClicked?.invoke(index, subdivisions.first[index])
                    },
                    onViewAllClicked = { onViewAllSubdivisionClicked?.invoke() }
                )
            }
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    sheetState.hide()
                    onFilterClicked?.invoke()
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchFilterModalBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    items: List<Any>,
    selectedItem: Any?,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    shouldEnableBackHandler: Boolean = false,
    onTextChanged: ((String) -> Unit)? = null,
    onItemSelected: ((Any) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    onSelectClicked: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val debounceSearch = debounce<String>(500, coroutineScope) { onTextChanged?.invoke(it) }
    val focusManager = LocalFocusManager.current

    BackHandler(enabled = shouldEnableBackHandler) {
        coroutineScope.launch {
            sheetState.hide()
            onBackClicked?.invoke()
        }
    }

    BaseModalBottomSheet(
        title = title,
        sheetState = sheetState,
        content = content,
        modifier = modifier
    ) {
        SearchBar(
            hint = "Search",
            onTextChanged = {
                debounceSearch(it)
            },
            onSearch = {
                focusManager.clearFocus()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            itemsIndexed(items) { index, item ->
                val name = when (item) {
                    is Language -> item.name
                    is Category -> item.name
                    is Region -> item.name
                    is Country -> item.name
                    is Subdivision -> item.name
                    else -> ""
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme3.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemSelected?.invoke(item) }
                            .padding(vertical = 8.dp)
                            .weight(1f)
                    )
                    if (selectedItem == item) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (index < items.lastIndex) {
                    Divider(
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    sheetState.hide()
                    onSelectClicked?.invoke()
                }
            },
            enabled = selectedItem != null,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Select")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun tes() {
    IPTVTheme {
        Column {
            SectionFilter(
                title = "Language",
                contents = listOf("Arab", "Indonesian", "English"),
                titleIcon = Icons.Outlined.Home,
                selectedItem = { index, item -> index == 1 }
            )
        }
    }
}
