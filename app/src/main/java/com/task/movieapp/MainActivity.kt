package com.task.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.task.designsystem.theme.MovieAppTheme
import com.task.movieapp.navigation.MovieAppNavHost
import com.task.movieapp.ui.rememberMovieAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberMovieAppState()

            CompositionLocalProvider {
                MovieAppTheme {
                    MovieAppNavHost(appState = appState)
                }
            }
        }
    }
}