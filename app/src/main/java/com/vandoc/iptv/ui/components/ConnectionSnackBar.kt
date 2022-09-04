package com.vandoc.iptv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vandoc.iptv.util.NavigatorBarChangeColorManager
import com.vandoc.iptv.util.monitorNetwork

/**
 * @author Achmad Ichsan
 * @version ConnectionSnackBar, v 0.2 03/09/22 21.00 by Ichvandi
 */

@Composable
fun ConnectionSnackBar() {
    val context = LocalContext.current
    val connectionFlow = context.monitorNetwork()
    val connectionState = connectionFlow?.collectAsState(initial = true)?.value

    if (connectionState != true) {
        ConnectionSnackBarUI(text = "Connection Lost")
    }
    NavigatorBarChangeColorManager(if (connectionState != true) android.graphics.Color.RED else null)
}

@Composable
private fun ConnectionSnackBarUI(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(Color.Red)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}