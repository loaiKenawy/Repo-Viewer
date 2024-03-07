package com.vf.task.repoviewer.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vf.task.repoviewer.ui.theme.Alto
import com.vf.task.repoviewer.ui.theme.BlackPearl
import com.vf.task.repoviewer.ui.theme.DarkWineBerry
import com.vf.task.repoviewer.ui.theme.Ebony
import com.vf.task.repoviewer.ui.theme.Martinique
import com.vf.task.repoviewer.ui.theme.SharkGray
import com.vf.task.repoviewer.ui.theme.WineBerry

@Composable
fun DrawLine(color: Color, height: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(color)
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver", "UnnecessaryComposedModifier")
fun Modifier.cardsBackground(): Modifier = composed {
    Modifier
        .border(
            width = 2.dp,
            color = SharkGray,
            shape = RoundedCornerShape(8.dp)
        )
        .background(
            Color.White.copy(alpha = 0.25f), RoundedCornerShape(8.dp)
        )
}


@SuppressLint("ModifierFactoryUnreferencedReceiver", "UnnecessaryComposedModifier")
fun Modifier.mainGradientBackground(): Modifier = composed {
    Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    BlackPearl,
                    Ebony,
                    Martinique,
                    DarkWineBerry,
                    WineBerry
                )
            )
        )
}


@Composable
fun CounterShape(counterValue: String, counterName: String, icon: ImageVector) {
    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(

                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = counterValue,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = counterName,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = SharkGray
        )

    }
}


@Composable
fun ProgressBarConfig() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .mainGradientBackground(),
        contentAlignment = Alignment.CenterStart
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize(),
            strokeWidth = 5.dp,
            color = Color.White,

        )
    }
}

@Composable
fun ErrorCard() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .mainGradientBackground()
            .padding(10.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Column() {
            Icon(
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                imageVector = Icons.Default.SignalWifiConnectedNoInternet4,
                contentDescription = "Check your connection",
                tint = Color.White.copy(0.7f)
            )

            Text( modifier = Modifier.fillMaxWidth(),
                color = Alto,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                text = "Check your connection")
        }


    }
}


@Preview
@Composable
fun Preview() {
    ProgressBarConfig()
}


