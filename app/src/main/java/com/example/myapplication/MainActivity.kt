package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.util.ConnectivityObserver
import com.example.myapplication.util.NetworkConnectivityObserver
import com.example.myapplication.viewmodel.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                NetworkStatusHandler(networkViewModel = networkViewModel)
                MainContent()
            }
        }
    }
}

@Composable
fun NetworkStatusHandler(networkViewModel: NetworkViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val networkListener = NetworkConnectivityObserver(context)
        networkListener.observe().collect { status ->
            val isNetworkAvailable = status == ConnectivityObserver.Status.Available
            networkViewModel.updateNetworkStatus(isNetworkAvailable)
            networkViewModel.showNetworkStatus()
        }
    }

    // Observe the readBackOnline Flow
    LaunchedEffect(Unit) {
        networkViewModel.readBackOnline.collect { backOnline ->
            networkViewModel.backOnline = backOnline
        }
    }
}


@Composable
fun MainContent() {
    Scaffold {
        // Your composable content here
        Task(it)
    }
}

@Composable
fun Task(paddingValues: PaddingValues) {
    // Your existing Task Composable implementation
    Column(modifier = Modifier.padding(paddingValues)) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .background(Color.Red)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "This is an aligned white bold italic text with 24 sp",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    color = Color.White,
                    fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Blue)
                    .fillMaxSize()
            ) {
            }
        }
        Box(
            modifier = Modifier.weight(4f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Black)
                        .weight(1f, true)
                        .fillMaxHeight()
                ) {}
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(2.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)
                ) {
                    items(12) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(4 / 5.5f)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(getRandomColor()),
                            contentAlignment = Alignment.Center
                        ) {

                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
            }
        }
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .weight(.1f)
                    .background(Color.Cyan)
                    .fillMaxHeight()
            ) {
                Text(text = "")
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(Color.Red, BlendMode.Color)
                )
            }
        }
    }
}

fun getRandomColor(): Color {
    val colors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan,
        Color.Magenta, Color.Gray, Color.DarkGray, Color.LightGray,
        Color.Black, Color.White, Color(0xFFFFA500)
    )
    return colors[Random.nextInt(colors.size)]
}
