package com.vandoc.iptv.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vandoc.iptv.ui.theme.IPTVTheme
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
