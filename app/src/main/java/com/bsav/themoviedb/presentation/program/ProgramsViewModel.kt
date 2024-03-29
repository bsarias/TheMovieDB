package com.bsav.themoviedb.presentation.program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsav.themoviedb.domain.movie.mapper.MovieMapper
import com.bsav.themoviedb.domain.movie.usecases.GetPopularMovies
import com.bsav.themoviedb.domain.movie.usecases.GetTopRatedMovies
import com.bsav.themoviedb.domain.program.Program
import com.bsav.themoviedb.domain.program.ProgramType
import com.bsav.themoviedb.domain.tvshow.mapper.TvShowMapper
import com.bsav.themoviedb.domain.tvshow.usecases.GetPopularTvShows
import com.bsav.themoviedb.domain.tvshow.usecases.GetTopRatedTvShows
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ProgramsViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMovies,
    private val getTopRatedMoviesUseCase: GetTopRatedMovies,
    private val getPopularTvShowsUseCase: GetPopularTvShows,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShows,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TvShowMapper,
) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<Program>>()
    val popularMovies: LiveData<List<Program>> get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<Program>>()
    val topRatedMovies: LiveData<List<Program>> get() = _topRatedMovies

    private val _popularTvShows = MutableLiveData<List<Program>>()
    val popularTvShows: LiveData<List<Program>> get() = _popularTvShows

    private val _topRatedTvShows = MutableLiveData<List<Program>>()
    val topRatedTvShows: LiveData<List<Program>> get() = _topRatedTvShows

    fun getMovies() {
        viewModelScope.launch {
            awaitAll(
                async { getPopularMovies() },
                async { getTopRatedMovies() },
                async { getPopularTvShows() },
                async { getTopRatedTvShows() },
            )
        }
    }

    private suspend fun getPopularMovies() {
        getPopularMoviesUseCase().map {
            it.map { movie ->
                movieMapper.movieToProgram(movie, ProgramType.Movie.Popular)
            }
        }.collect {
            _popularMovies.value = it
        }
    }

    private suspend fun getTopRatedMovies() {
        getTopRatedMoviesUseCase().map {
            it.map { movie ->
                movieMapper.movieToProgram(movie, ProgramType.Movie.TopRated)
            }
        }.collect {
            _topRatedMovies.value = it
        }
    }

    private suspend fun getPopularTvShows() {
        getPopularTvShowsUseCase().map {
            it.map { tvShow ->
                tvShowMapper.tvShowToProgram(tvShow, ProgramType.TvShow.Popular)
            }
        }.collect {
            _popularTvShows.value = it
        }
    }

    private suspend fun getTopRatedTvShows() {
        getTopRatedTvShowsUseCase().map {
            it.map { tvShow ->
                tvShowMapper.tvShowToProgram(tvShow, ProgramType.TvShow.TopRated)
            }
        }.collect {
            _topRatedTvShows.value = it
        }
    }
}


