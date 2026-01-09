package com.task.movie.detailMovie

sealed class MovieDetailsEvent {
    data object OnBackClick : MovieDetailsEvent()
}