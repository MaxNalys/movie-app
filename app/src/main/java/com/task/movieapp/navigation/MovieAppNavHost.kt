package com.task.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.task.movie.navigation.MoviesRoute
import com.task.movie.navigation.composableMoviesRoute
import com.task.movieapp.ui.MovieAppState


@Composable
fun MovieAppNavHost(
    appState: MovieAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = MoviesRoute,
        modifier = modifier
    ) {
        composableMoviesRoute(appState.navController)
    }
}