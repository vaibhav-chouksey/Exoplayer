package com.example.exoplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

//for state hosting
//hum checks ko ko rakhte h root level pr or fir yha bs pass krte h and handles it by lambda function

@Composable
fun PlayerUi(
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    isBuffering: Boolean,
    isSeeking: Boolean,
    onPlayPauseClick: () -> Unit,
    onSeekBarPositionChange: (Long) -> Unit,
    onSeekBarPositionChangeFinished: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if(isBuffering) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier
                    .size(20.dp)
            )
        } else {
            IconButton(
                onClick = onPlayPauseClick,
                modifier = Modifier
                    .size(100.dp)
            ) {
                Icon(
                    imageVector = if(isPlaying) {
                        ImageVector.vectorResource(R.drawable.baseline_pause_24)
                    } else {
                        ImageVector.vectorResource(R.drawable.baseline_play_arrow_24)
                    },
                    contentDescription = if(isPlaying) "Pause" else "Play",
                    modifier = Modifier
                        .size(48.dp),
                    tint = Color.White
                )
            }
        }


    }
}


