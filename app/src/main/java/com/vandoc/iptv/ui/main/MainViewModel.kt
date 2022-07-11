package com.vandoc.iptv.ui.main

import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Ichvandi
 * Created on 24/06/2022 at 21:42.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel() {

    fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate()
    }

}