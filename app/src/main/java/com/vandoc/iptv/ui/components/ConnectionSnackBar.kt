package com.vandoc.iptv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vandoc.iptv.util.ConnectionFlow

/**
 * @author Achmad Ichsan
 * @version ConnectionSnackBar, v 0.1 11/07/22 17.55 by Achmad Ichsan
 */

@Composable
fun ConnectionSnackBar() {
    val context = LocalContext.current
    val connectionFlow = remember { ConnectionFlow(context) }
    val connectionState = connectionFlow.connectionState.collectAsState(initial = true).value

    if (!connectionState) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Red)
        ) {
            Text(
                text = "Connection lost",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}