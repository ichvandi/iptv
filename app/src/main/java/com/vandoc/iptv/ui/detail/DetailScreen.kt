package com.vandoc.iptv.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage
import com.vandoc.iptv.R
import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.ui.components.*
import com.vandoc.iptv.util.DUMMY_CHANNELS
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import androidx.compose.material3.MaterialTheme as MaterialTheme3

/**
 * @author Ichvandi
 * Created on 20/08/2022 at 21:24.
 */
@RootNavGraph
@Destination
@Composable
fun DetailScreen(
    channel: ChannelMini,
    viewModel: DetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val tabItems = remember {
        listOf(
            TabItem(
                title = "Detail",
                icon = Icons.Outlined.Info,
                selectedIcon = Icons.Filled.Info
            ),
            TabItem(
                title = "Stream",
                icon = Icons.Outlined.LiveTv,
                selectedIcon = Icons.Filled.LiveTv
            ),
            TabItem(
                title = "Guide",
                icon = Icons.Outlined.Description,
                selectedIcon = Icons.Filled.Description
            )
        )
    }
    val toolbarState = rememberCollapsingToolbarScaffoldState()
    var tabItem by rememberSaveable { mutableStateOf(tabItems.first().title) }

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = toolbarState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            val textSize = (16 + (24 - 16) * toolbarState.toolbarState.progress).sp
            Box(
                modifier = Modifier
                    .background(
                        if (MaterialTheme.colors.isLight)
                            MaterialTheme.colors.primary
                        else
                            Color(0xFF232323)
                    )
                    .fillMaxWidth()
                    .height(100.dp)
                    .pin()
            )

            IconButton(onClick = { navigator.navigateUp() }, modifier = Modifier.pin()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_chevron_left_24),
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
                        .size(68.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 68.dp)
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
                    verticalArrangement = if (channel.isNsfw) Arrangement.SpaceBetween else Arrangement.Center
                ) {
                    Text(
                        text = channel.name,
                        style = MaterialTheme3.typography.titleMedium,
                        color = Color.White,
                        fontSize = textSize
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
    ) {
        Column {
            TabLayout(tabs = tabItems, onTabSelected = { _, item -> tabItem = item.title })
            when (tabItem) {
                "Detail" -> DetailTab(channel = DUMMY_CHANNELS.first())
                "Stream" -> StreamTab(streams = DUMMY_CHANNELS.first().streams)
                "Guide" -> GuideTab(guides = DUMMY_CHANNELS.first().guides)
            }
        }
    }
}
