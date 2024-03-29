package com.vandoc.iptv.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import com.vandoc.iptv.R
import com.vandoc.iptv.data.model.local.Channel
import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.ui.theme.IPTVTheme
import com.vandoc.iptv.util.DUMMY_CHANNELS
import me.onebone.toolbar.CollapsingToolbarScope
import java.util.*

/**
 * @author Ichvandi
 * Created on 24/08/2022 at 13:59.
 */
@Composable
fun CollapsingToolbarScope.DetailToolbar(
    channel: ChannelMini,
    textSize: Float,
    imageSize: Float,
    columnPadding: Float,
    onNavigateUp: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .background(
                if (androidx.compose.material.MaterialTheme.colors.isLight)
                    androidx.compose.material.MaterialTheme.colors.primary
                else
                    Color(0xFF232323)
            )
            .fillMaxWidth()
            .height(100.dp)
            .pin()
    )

    IconButton(onClick = { onNavigateUp?.invoke() }, modifier = Modifier.pin()) {
        Icon(
            imageVector = Icons.Outlined.ChevronLeft,
            tint = Color.White,
            contentDescription = ""
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .road(Alignment.CenterStart, Alignment.CenterStart)
            .padding(start = 60.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImage(
            imageModel = channel.logo,
            contentScale = ContentScale.Fit,
            placeHolder = painterResource(id = R.drawable.ic_launcher_background),
            error = ImageBitmap.imageResource(id = R.drawable.no_image),
            modifier = Modifier
                .size(imageSize.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 68.dp)
                .padding(
                    start = 16.dp,
                    top = columnPadding.dp,
                    bottom = columnPadding.dp,
                    end = 16.dp
                ),
            verticalArrangement = if (channel.isNsfw) Arrangement.SpaceBetween else Arrangement.Center
        ) {
            Text(
                text = channel.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontSize = textSize.sp
            )

            if (channel.isNsfw) {
                Image(
                    painter = painterResource(id = R.drawable.nsfw),
                    contentDescription = "",
                    modifier = Modifier
                        .width(32.dp)
                )
            }
        }
    }
}

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StreamTab(
    streams: List<Channel.Stream>,
    onStreamClicked: ((Channel.Stream) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(streams) { index, stream ->
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                onClick = { onStreamClicked?.invoke(stream) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column {
                        Text(
                            text = "Stream ${index + 1}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stream.status,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (stream.status == "online") Color.Green else Color.Red
                        )
                    }

                    if (stream.resolution != "Unknown") {
                        Text(
                            text = stream.resolution,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GuideTab(
    guides: List<Channel.Guide>,
    onGuideClicked: ((Channel.Guide) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(guides) { guide ->
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                onClick = { onGuideClicked?.invoke(guide) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = guide.website.split(".").firstOrNull()?.replaceFirstChar {
                            if (it.isLowerCase())
                                it.titlecase(Locale.ROOT)
                            else
                                it.toString()
                        }.orEmpty(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = guide.language,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
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
                            color = Color.White,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailTabPreview() {
    IPTVTheme {
        DetailTab(channel = DUMMY_CHANNELS.first())
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StreamTabPreview() {
    IPTVTheme {
        StreamTab(streams = DUMMY_CHANNELS.first().streams)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GuideTabPreview() {
    IPTVTheme {
        GuideTab(guides = DUMMY_CHANNELS.first().guides)
    }
}
