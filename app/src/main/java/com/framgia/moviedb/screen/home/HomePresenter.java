package com.framgia.moviedb.screen.home;

import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.source.MoviesDataSource;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private MoviesRepository mMoviesRepository;

    public HomePresenter(HomeContract.View view, MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
        this.mView = view;
    }

    @Override

    public void loadMovies() {
        mMoviesRepository.getMovies(new MoviesDataSource.LoadMoviesCallback() {
            @Override public void onMoviesLoaded(List<GroupMovie> groupMovies, List<Movie> movies) {
                mView.showMovies(groupMovies);
                mView.showViewFlipper(movies);
            }

            @Override public void onDataNotAvailable(Exception e) {
                mView.showMoviesError(e);
            }
        });
    }
}
