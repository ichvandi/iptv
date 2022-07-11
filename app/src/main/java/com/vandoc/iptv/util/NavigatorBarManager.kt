package com.vandoc.iptv.util

import android.animation.ObjectAnimator
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vandoc.iptv.R

/**
 * @author Achmad Ichsan
 * @version NavigatorBarManager, v 0.1 11/07/22 22.53 by Achmad Ichsan
 */

class NavigatorBarManager(private val activity: Activity) {
    private val formerColor = activity.window.navigationBarColor

    fun changeColor(id: Int?) {
        activity.run {
            window.navigationBarColor =
                id?.let { getColor(it) } ?: run { formerColor }
        }
    }
}