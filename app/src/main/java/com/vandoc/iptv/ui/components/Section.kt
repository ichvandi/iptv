package com.vandoc.iptv.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vandoc.iptv.data.model.local.ChannelMini
import com.vandoc.iptv.ui.theme.IPTVTheme
import com.vandoc.iptv.util.DUMMY_CHANNELS_MINI
import com.vandoc.iptv.util.DUMMY_SECTION

/**
 * @author Ichvandi
 * Created on 10/07/2022 at 08:41.
 */
@Composable
fun Section(
    section: String,
    onViewAllClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = section,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                .weight(1f)
        )
        TextButton(onClick = { onViewAllClicked(section) }) {
            Text(text = "View All", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SectionChannels(
    section: String,
    channels: List<ChannelMini>,
    onViewAllClicked: (String) -> Unit = {},
    onItemClicked: (ChannelMini) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Section(
            section = section,
            onViewAllClicked = onViewAllClicked,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        ListChannel(
            channels = channels,
            onItemClicked = onItemClicked,
            contentPadding = PaddingValues(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
fun SectionPreview() {
    IPTVTheme {
        Section(section = DUMMY_SECTION)
    }
}

@Preview
@Composable
fun SectionChannelsPreview() {
    IPTVTheme {
        SectionChannels(section = DUMMY_SECTION, channels = DUMMY_CHANNELS_MINI)
    }
}
