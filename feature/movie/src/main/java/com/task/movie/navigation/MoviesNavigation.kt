package com.task.movie.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.task.movie.movieList.MovieListType
import com.task.movie.movieList.MoviesListScreen
import com.task.movie.movieList.MoviesScreen
import com.task.movie.detailMovie.MovieDetailsScreen
import kotlinx.serialization.Serializable


@Serializable
data object MoviesRoute

@Serializable
data class MoviesListRoute(
    val type: MovieListType
)

@Serializable
data class MovieDetailsRoute(
    val movieId: Int
)


fun NavController.navigateToMovies(navOptions: NavOptions? = null) =
    navigate(route = MoviesRoute, navOptions)

fun NavController.navigateToMoviesList(
    type: MovieListType,
    navOptions: NavOptions? = null
) = navigate(
    route = MoviesListRoute(type),
    navOptions = navOptions
)

fun NavController.navigateToMovieDetails(
    movieId: Int,
    navOptions: NavOptions? = null
) = navigate(
    route = MovieDetailsRoute(movieId),
    navOptions = navOptions
)


fun NavGraphBuilder.composableMoviesRoute(
    navController: NavController
) {
    composable<MoviesRoute> {
        MoviesScreen(
            onMovieClick = { movie ->
                navController.navigateToMovieDetails(movie.id)
            },
            onSeeAllClick = { type ->
                navController.navigateToMoviesList(type)
            }
        )
    }

    composable<MoviesListRoute> { backStackEntry ->
        val type = backStackEntry.arguments?.getSerializable("type") as? MovieListType
            ?: MovieListType.TRENDING

        MoviesListScreen(
            type = type,
            onMovieClick = { movieId ->
                navController.navigateToMovieDetails(movieId)
            }
        )
    }

    composable<MovieDetailsRoute> { backStackEntry ->
        val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable

        MovieDetailsScreen(
            movieId = movieId,
            onBack = navController::popBackStack
        )
    }
}