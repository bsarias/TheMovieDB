package com.bsav.themoviedb.framework.network.services

import com.bsav.themoviedb.framework.network.API_KEY
import com.bsav.themoviedb.framework.network.models.movie.PageMovieNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(ENDPOINT_POPULAR)
    suspend fun getPopularMovies(
        @Query(PAGE) page: Int = FIRST_PAGE,
        @Query(KEY) apiKey: String = API_KEY
    ): PageMovieNetwork

    @GET(ENDPOINT_TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query(PAGE) page: Int = FIRST_PAGE,
        @Query(KEY) apiKey: String = API_KEY
    ): PageMovieNetwork

}

private const val ENDPOINT_POPULAR = "movie/popular"
private const val ENDPOINT_TOP_RATED = "movie/top_rated"
private const val PAGE = "page"
private const val KEY = "api_key"
private const val FIRST_PAGE = 1

