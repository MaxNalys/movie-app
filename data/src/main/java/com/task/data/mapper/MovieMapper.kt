package com.task.data.mapper

import com.task.data.dataSource.tmdbApi.model.MovieDto
import com.task.model.Movie

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genreIds = genreIds,
        popularity = popularity,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )