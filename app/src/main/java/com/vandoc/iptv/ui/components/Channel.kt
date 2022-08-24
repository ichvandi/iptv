package com.vandoc.iptv.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import com.vandoc.iptv.R
import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.ui.theme.IPTVTheme
import com.vandoc.iptv.util.DUMMY_CHANNELS_MINI

/**
 * @author Ichvandi
 * Created on 27/06/2022 at 20:54.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemChannel(
    channel: ChannelMini,
    onItemClicked: (ChannelMini) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        onClick = { onItemClicked(channel) },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp)
        ) {
            // Logo
            CoilImage(
                imageModel = channel.logo,
                contentScale = ContentScale.Fit,
                placeHolder = painterResource(id = R.drawable.ic_launcher_background),
                error = ImageBitmap.imageResource(id = R.drawable.no_image),
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            // Title
            Text(
                text = channel.name,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )

            // Genres
            Text(
                text = channel.categories.joinToString { it },
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Country Logo
                    CoilImage(
                        imageModel = channel.flag,
                        contentScale = ContentScale.Crop,
                        placeHolder = painterResource(id = R.drawable.ic_launcher_background),
                        error = ImageBitmap.imageResource(id = R.drawable.no_image),
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = channel.country,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .wrapContentWidth()
                    )
                }

                // NSFW
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
}

@Composable
fun ListChannel(
    channels: List<ChannelMini>,
    onItemClicked: (ChannelMini) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(channels) { channel ->
            ItemChannel(
                channel = channel,
                onItemClicked = onItemClicked,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun GridChannel(
    channels: List<ChannelMini>,
    columns: GridCells,
    onItemClicked: (ChannelMini) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = columns,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(channels) { channel ->
            ItemChannel(
                channel = channel,
                onItemClicked = onItemClicked,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemChannelPreview() {
    IPTVTheme {
        ItemChannel(DUMMY_CHANNELS_MINI.first())
    }
}

@Preview
@Composable
fun ListChannelPreview() {
    ListChannel(channels = DUMMY_CHANNELS_MINI)
}
