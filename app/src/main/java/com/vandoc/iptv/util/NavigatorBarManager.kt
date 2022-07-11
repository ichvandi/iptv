package com.vandoc.iptv.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext

/**
 * @author Achmad Ichsan
 * @version NavigatorBarManager, v 0.1 11/07/22 22.53 by Achmad Ichsan
 */
@Composable
fun NavigatorBarChangeColorManager(id: Int?) {
    val context = LocalContext.current
    val activity = remember { context.findActivity() }
    val formerColor = rememberSaveable { activity?.window?.navigationBarColor ?: 0 }

    activity?.run {
        window.navigationBarColor =
            id?.let { getColor(it) } ?: run { formerColor }
    }
}