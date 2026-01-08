package com.task.data.mapper

import com.task.data.dataSource.tmdbApi.model.GenreDto
import com.task.model.Genre

fun GenreDto.toDomain(): Genre =
    Genre(
        id = id,
        name = name
    )