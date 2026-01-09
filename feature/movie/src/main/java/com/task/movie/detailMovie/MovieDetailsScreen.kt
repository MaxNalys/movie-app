package com.task.movie.detailMovie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import androidx.compose.ui.unit.dp

import com.task.ui.cards.MovieDetailCard

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.load(movieId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {

            MovieDetailsUiState.Loading -> {
                SimpleLoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MovieDetailsUiState.Success -> {

                val genreNames = state.genres.map { it.name }

                MovieDetailCard(
                    posterPath = state.movie.posterPath.orEmpty(),
                    title = state.movie.title,
                    releaseDate = state.movie.releaseDate,
                    overview = state.movie.overview,
                    rating = state.movie.voteAverage.toFloat(),
                    voteCount = state.movie.voteCount,
                    genres = genreNames,
                    onCardClick = {} // або {}

                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(MovieDetailsEvent.OnBackClick)
                        onBack()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }


            is MovieDetailsUiState.Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = state.message ?: "Unknown error"
                )
            }
        }
    }
}
