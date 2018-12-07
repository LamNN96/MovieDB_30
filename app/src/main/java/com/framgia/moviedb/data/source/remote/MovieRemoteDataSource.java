package com.framgia.moviedb.data.source.remote;

import com.framgia.moviedb.data.repository.MovieDetailAsyncTask;
import com.framgia.moviedb.data.repository.MoviesContentAsyncTask;
import com.framgia.moviedb.data.repository.RelatedMovieByPersonAsyncTask;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.utils.APIUtils;
import com.framgia.moviedb.utils.Constants;

public class MovieRemoteDataSource implements MoviesDataSource {
    public static final int FIST_PAGE_NUMBER = 1;
    private static MovieRemoteDataSource sMovieRemoteDataSource;

    private MovieRemoteDataSource() {
    }

    public static MovieRemoteDataSource getInstance() {
        if (sMovieRemoteDataSource == null) {
            sMovieRemoteDataSource = new MovieRemoteDataSource();
        }
        return sMovieRemoteDataSource;
    }

    @Override
    public void getMovies(LoadMoviesCallback loadMoviesCallback) {
        String[] apis = new String[Constants.GROUP_NAMES.length];
        for (int i = 0; i < Constants.GROUP_NAMES.length; i++) {
            apis[i] = APIUtils.getApiUrl(Constants.GROUP_NAMES[i], FIST_PAGE_NUMBER);
        }
        new MoviesContentAsyncTask(loadMoviesCallback).execute(apis);
    }

    @Override
    public void getMovieByPersonId(int id, int page, GetMovieByPersonIdCallback callback) {
        new RelatedMovieByPersonAsyncTask(callback).execute(id, page);
    }

    @Override
    public void getMovie(String movieId, GetMovieCallback getMovieCallback) {
        new MovieDetailAsyncTask(getMovieCallback).execute(movieId);
    }
}
