package com.framgia.moviedb.screen.person;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.Person;
import com.framgia.moviedb.data.repository.MoviesRepository;
import com.framgia.moviedb.data.repository.PersonRepository;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.data.source.PersonDataSource;

import java.util.List;

public class PersonPresenter implements PersonContract.Presenter {

    private PersonContract.View mView;
    private PersonRepository mRepository;
    private MoviesRepository mMovieRepository;

    public PersonPresenter(PersonContract.View view, PersonRepository repository,
                           MoviesRepository moviesRepository) {
        mView = view;
        mMovieRepository = moviesRepository;
        mRepository = repository;
    }

    @Override
    public void getPersonInfo(int id) {
        mRepository.getPersonDetail(id, new PersonDataSource.GetPersonDetailCallback() {
            @Override
            public void onDataLoaded(Person person) {
                mView.showInfo(person);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showInfoError(e);
            }
        });
    }

    @Override
    public void getMovieByPersonId(int id, int page) {
        mMovieRepository.getMovieByPersonId(id, page, new MoviesDataSource.GetMovieByPersonIdCallback() {
            @Override
            public void onDataLoaded(List<Movie> movies) {
                mView.showRelatedMovie(movies);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mView.showInfoError(e);
            }
        });
    }
}
