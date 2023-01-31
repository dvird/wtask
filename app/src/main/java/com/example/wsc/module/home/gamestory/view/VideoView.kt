package com.example.wsc.module.home.gamestory.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoView(
    videoUri: String, modifier: Modifier = Modifier,
    onEnded: (() -> Unit)?
) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(videoUri)
                .build()
            exoPlayer.playWhenReady = false
            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.addListener(object : Player.Listener{
//                override fun onPlaybackStateChanged(state: Int) {
//                    if (state == ExoPlayer.STATE_ENDED) {
//                        onEnded?.invoke()
//                    }
//                }
//            })
            exoPlayer.prepare()
        }

    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = {
                StyledPlayerView(context).apply {
                    controllerAutoShow = false
                    controllerHideOnTouch = true
                    player = exoPlayer
                }
            })
    ) {
        val observer = LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.stop()
                }
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.play()
                }
                else -> {}
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycle.removeObserver(observer)
        }
    }
}