package com.task.movie.movieList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.designsystem.component.SimpleLoadingIndicator
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.ui.cards.MovieCard

@Composable
fun MoviesListScreen(
    type: MovieListType,
    onMovieClick: (Int) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is MoviesUiState.Loading -> SimpleLoadingIndicator(modifier = Modifier.align(Alignment.Center))
            is MoviesUiState.Success -> {
                val state = uiState as MoviesUiState.Success
                val movies = if (type == MovieListType.TRENDING) state.trending else state.popular

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies) { movie ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieCard(
                                posterPath = movie.posterPath.orEmpty(),
                                title = movie.title,
                                year = movie.releaseDate,
                                rating = movie.voteAverage.toFloat(),
                                onMovieClick = { onMovieClick(movie.id) }
                            )
                        }
                    }
                }
            }
            is MoviesUiState.Error -> {
                val state = uiState as MoviesUiState.Error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message ?: "Unknown error")
                }
            }
        }
    }
}