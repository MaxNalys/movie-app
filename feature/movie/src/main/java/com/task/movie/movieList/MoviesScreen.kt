package com.task.movie.movieList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.ui.cards.MovieCard

@Composable
fun MoviesScreen(
    onMovieClick: (com.task.model.Movie) -> Unit,
    onSeeAllClick: (MovieListType) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is MoviesUiState.Loading -> SimpleLoadingIndicator()

            is MoviesUiState.Success -> {
                val state = uiState as MoviesUiState.Success
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    MoviesSection(
                        title = "Trending",
                        movies = state.trending,
                        onMovieClick = onMovieClick,
                        onSeeAllClick = { onSeeAllClick(MovieListType.TRENDING) },
                        loadNextPage = { viewModel.loadNextPage(MovieListType.TRENDING) }
                    )

                    Spacer(Modifier.height(24.dp))

                    MoviesSection(
                        title = "Popular",
                        movies = state.popular,
                        onMovieClick = onMovieClick,
                        onSeeAllClick = { onSeeAllClick(MovieListType.POPULAR) },
                        loadNextPage = { viewModel.loadNextPage(MovieListType.POPULAR) }
                    )
                }
            }

            is MoviesUiState.Error -> {
                val state = uiState as MoviesUiState.Error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = state.message ?: "Unknown error")
                }
            }
        }
    }
}

@Composable
fun MoviesSection(
    title: String,
    movies: List<com.task.model.Movie>,
    onMovieClick: (com.task.model.Movie) -> Unit,
    onSeeAllClick: () -> Unit,
    loadNextPage: () -> Unit = {}
) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            TextButton(onClick = onSeeAllClick) { Text("See all") }
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(movies) { movie ->
                MovieCard(
                    posterPath = movie.posterPath.orEmpty(),
                    title = movie.title,
                    year = movie.releaseDate,
                    rating = movie.voteAverage.toFloat(),
                    onMovieClick = { onMovieClick(movie) }
                )
            }

            item {
                LaunchedEffect(movies.size) {
                    loadNextPage()
                }
            }
        }
    }
}

