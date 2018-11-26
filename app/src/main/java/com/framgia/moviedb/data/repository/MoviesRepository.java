package com.framgia.moviedb.data.repository;

import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;

public class MoviesRepository implements MoviesDataSource {
    private static MoviesRepository sInstance;
    private MoviesDataSource mDataSource;
    private MoviesRepository(MoviesDataSource moviesDataSource) {
        mDataSource = moviesDataSource;
    }

    public static MoviesRepository getInstance(MoviesDataSource moviesDataSource) {
        if (sInstance == null) {
            sInstance = new MoviesRepository(moviesDataSource);
        }
        return sInstance;
    }

    @Override public void getMovies(LoadMoviesCallback loadMoviesCallback) {
        mDataSource.getMovies(loadMoviesCallback);
    }

    @Override public void getMovie(String movieId, GetMovieCallback getMovieCallback) {

    }
}
