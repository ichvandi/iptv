package com.vandoc.iptv.ui.components

import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.vandoc.iptv.util.DnsResolver
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author Ichvandi
 * Created on 10/07/2022 at 08:46.
 */
@Composable
fun Player(
    streamUrl: String,
    onStreamSuccess: (() -> Unit)? = null,
    onStreamFailed: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val chuckerInterceptor = remember {
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    val okHttpClient = remember {
        OkHttpClient.Builder()
            .apply {
                dns(DnsResolver(this.build()))
            }
            .addInterceptor(chuckerInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    val dataSourceFactory = remember { OkHttpDataSource.Factory(okHttpClient) }
    val mediaSourceFactory = remember { DefaultMediaSourceFactory(dataSourceFactory) }

    val mediaItem = MediaItem.Builder()
        .setUri(streamUrl)
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .build()

    val mediaSource = mediaSourceFactory.createMediaSource(mediaItem)

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setLivePlaybackSpeedControl(
                DefaultLivePlaybackSpeedControl.Builder()
                    .setFallbackMaxPlaybackSpeed(1f)
                    .setFallbackMinPlaybackSpeed(1f)
                    .setMaxLiveOffsetErrorMsForUnitSpeed(1)
                    .build()
            )
            .build()
            .apply {
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        when (playbackState) {
                            ExoPlayer.STATE_IDLE -> onStreamFailed?.invoke()
                            ExoPlayer.STATE_READY -> onStreamSuccess?.invoke()
                            else -> Unit
                        }
                    }
                })
                setMediaSource(mediaSource)
                playWhenReady = true
                seekTo(0, 0)
                prepare()
            }
    }

    LocalLifecycleOwner.current.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            if (!exoPlayer.isPlaying) {
                exoPlayer.seekToNext()
                exoPlayer.play()
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            exoPlayer.pause()
            super.onStop(owner)
        }
    })

    DisposableEffect(
        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    setBackgroundColor(Color.BLACK)
                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    useController = false
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = modifier
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}