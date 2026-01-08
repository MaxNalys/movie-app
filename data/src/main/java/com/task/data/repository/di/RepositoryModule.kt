package com.task.data.repository.di

import com.task.data.repository.DefaultMovieRepository
import com.task.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(
        impl: DefaultMovieRepository
    ): MovieRepository
}