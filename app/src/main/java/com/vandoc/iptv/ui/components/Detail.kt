package com.vandoc.iptv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.vandoc.iptv.R
import com.vandoc.iptv.data.model.local.Channel

/**
 * @author Ichvandi
 * Created on 24/08/2022 at 13:59.
 */
@Composable
fun DetailTab(channel: Channel, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            SectionTitle(
                title = "Name",
                content = channel.name,
                titleIcon = Icons.Outlined.TextFields
            )
        }
        item {
            SectionTitle(
                title = "Native Name",
                content = channel.nativeName,
                titleIcon = Icons.Outlined.TextFields
            )
        }
        item {
            SectionTitle(
                title = "Network",
                content = channel.network,
                titleIcon = Icons.Outlined.Hub
            )
        }
        item {
            SectionTitle(
                title = "Launch Date",
                content = channel.launched,
                titleIcon = Icons.Outlined.CalendarMonth
            )
        }
        item {
            SectionTitle(
                title = "Closed Date",
                content = channel.closed,
                titleIcon = Icons.Outlined.CalendarMonth
            )
        }
        item {
            SectionTitle(
                title = "Replaced By",
                content = channel.replacedBy,
                titleIcon = Icons.Outlined.LiveTv
            )
        }
        item {
            SectionTitle(
                title = "Region",
                content = channel.region,
                titleIcon = Icons.Outlined.Public
            )
        }
        item {
            SectionTitle(
                title = "Country",
                content = channel.country,
                titleIcon = Icons.Outlined.Public,
                contentIcon = channel.flag
            )
        }
        item {
            SectionTitle(
                title = "Subdivision",
                content = channel.subdivision,
                titleIcon = Icons.Outlined.Public
            )
        }
        item {
            SectionList(
                title = "Categories",
                contents = channel.categories,
                titleIcon = Icons.Outlined.Category
            )
        }
        item {
            SectionList(
                title = "Broadcast Areas",
                contents = channel.broadcastAreas.region + channel.broadcastAreas.country + channel.broadcastAreas.subdivision,
                titleIcon = Icons.Outlined.CellTower
            )
        }
        item {
            SectionList(
                title = "Languages",
                contents = channel.languages,
                titleIcon = Icons.Outlined.Translate
            )
        }
        item {
            SectionButton(
                title = "Website",
                content = channel.website,
                titleIcon = Icons.Outlined.Language
            )
        }
    }
}

@Composable
fun SectionTitle(
    title: String,
    content: String?,
    titleIcon: ImageVector? = null,
    contentIcon: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
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
                style = MaterialTheme.typography.titleSmall,
                modifier = if (titleIcon != null) Modifier.padding(start = 8.dp) else Modifier
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            contentIcon?.let {
                CoilImage(
                    imageModel = it,
                    contentScale = ContentScale.Crop,
                    placeHolder = painterResource(id = R.drawable.ic_launcher_background),
                    error = ImageBitmap.imageResource(id = R.drawable.no_image),
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                text = if (content.isNullOrEmpty()) "N/A" else content,
                style = MaterialTheme.typography.titleMedium,
                modifier = if (contentIcon != null) Modifier.padding(start = 8.dp) else Modifier
            )
        }
    }
}

@Composable
fun SectionList(
    title: String,
    contents: List<String>?,
    titleIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            titleIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                modifier = if (titleIcon != null) Modifier.padding(start = 8.dp) else Modifier
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            if (contents.isNullOrEmpty()) {
                Text(
                    text = "N/A",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                LazyRow(contentPadding = PaddingValues(horizontal = 12.dp)) {
                    items(contents) { content ->
                        Text(
                            text = content,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .background(
                                    color = if (androidx.compose.material.MaterialTheme.colors.isLight)
                                        androidx.compose.material.MaterialTheme.colors.primary
                                    else
                                        Color(0xFF232323),
                                    shape = CircleShape
                                )
                                .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionButton(
    title: String,
    content: String?,
    titleIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
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
                style = MaterialTheme.typography.titleSmall,
                modifier = if (titleIcon != null) Modifier.padding(start = 8.dp) else Modifier
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            if (content.isNullOrEmpty()) {
                Text(
                    text = "N/A",
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Button(onClick = { uriHandler.openUri(content) }, shape = CircleShape) {
                    Text(text = "Go to website")
                }
            }
        }
    }
}
