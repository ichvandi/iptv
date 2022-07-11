package com.vandoc.iptv.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.vandoc.iptv.ui.NavGraphs
import com.vandoc.iptv.ui.theme.IPTVTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Ichvandi
 * Created on 26/06/2022 at 21:36.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchRemoteConfig()
        setContent {
            IPTVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   MainScreen()
                }
            }
        }
    }
}
