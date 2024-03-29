package com.bsav.themoviedb.data.movie

import com.bsav.themoviedb.domain.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getMovieById(id: Int): Flow<Movie>
    fun getPopularMovies(): Flow<List<Movie>>
    fun getTopRatedMovies(): Flow<List<Movie>>
}
