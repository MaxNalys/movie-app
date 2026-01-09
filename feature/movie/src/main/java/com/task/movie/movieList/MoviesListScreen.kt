package com.task.movie.movieList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.designsystem.component.SimpleLoadingIndicator
import com.task.ui.cards.MovieCard

@Composable
fun MoviesListScreen(
    type: MovieListType,
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {

        // ⬅️ BACK BUTTON (завжди зверху)
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface

            )
        }

        when (uiState) {

            is MoviesUiState.Loading -> {
                SimpleLoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MoviesUiState.Success -> {
                val state = uiState as MoviesUiState.Success
                val movies =
                    if (type == MovieListType.TRENDING) state.trending else state.popular

                // 🔥 Пагінація
                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    }.collect { lastVisibleIndex ->
                        if (lastVisibleIndex != null && lastVisibleIndex >= movies.size - 3) {
                            viewModel.loadNextPage(type)
                        }
                    }
                }

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(
                        top = 100.dp, // ⬅️ щоб список не заліз під кнопку
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = movies,
                        key = { it.id }
                    ) { movie ->
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

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SimpleLoadingIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
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