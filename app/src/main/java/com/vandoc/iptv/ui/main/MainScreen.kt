package com.vandoc.iptv.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.vandoc.iptv.ui.NavGraphs
import com.vandoc.iptv.ui.components.ConnectionSnackBar

/**
 * @author Achmad Ichsan
 * @version MainScreen, v 0.1 11/07/22 17.59 by Achmad Ichsan
 */

@Composable
fun MainScreen() {
    Column(modifier = Modifier.fillMaxHeight()) {
        DestinationsNavHost(navGraph = NavGraphs.root, modifier = Modifier.weight(1f))
        ConnectionSnackBar()
    }
}