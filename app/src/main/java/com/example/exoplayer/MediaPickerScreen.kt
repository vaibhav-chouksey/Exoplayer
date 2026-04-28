package com.example.exoplayer

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.RetainedEffect
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer


import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.media3.ui.compose.ContentFrame


@Composable
fun MediaPickerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
// Using retain to persist player instance across configuration changes
    val player = retain { //new lib from google to hold value after state change
        ExoPlayer
            .Builder(context.applicationContext) //to prevent mem leaks we bind it to application context
            .build()
    }
    // abh user leave krega screen to exoplayer release krne k liye
    RetainedEffect(player ) {//static
        onRetire { player.release() }
    } //called when nav to diff screen



// Release the player when the screen is disposed
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            player.setMediaItem(
                MediaItem.fromUri(uri)
            )
            player.prepare()
            player.play()
        }
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
    ) {
    Button(
        onClick = {
            videoPickerLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                )
            )
        }
    ) {
        Text("Pick video")
    }
        //all states of the video player
        var isPlaying by retain {
            mutableStateOf(false)
        }
        var currentPosition by retain {
            mutableLongStateOf(0L)
        }
        var duration by retain {
            mutableLongStateOf(0L)
        }
        var isSeeking by retain {
            mutableStateOf(false)
        }
        var isBuffering by retain {
            mutableStateOf(false)
        }
        var isPlayerUiVisible by retain {
            mutableStateOf(false)
        }

        //linking the player  instance with the component we use content frames

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        ContentFrame( //put it in  a container composable like box
            player = player,
            modifier = Modifier
                .fillMaxSize()

        )


    }

}
}