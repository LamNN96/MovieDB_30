package com.framgia.moviedb.data.repository;

import com.framgia.moviedb.data.source.PersonDataSource;

public class PersonRepository implements PersonDataSource {

    private static PersonRepository sInstance;
    private PersonDataSource mDataSource;

    private PersonRepository(PersonDataSource dataSource) {
        mDataSource = dataSource;
    }

    public static PersonRepository getInstance(PersonDataSource personDataSource) {
        if (sInstance == null) {
            sInstance = new PersonRepository(personDataSource);
        }
        return sInstance;
    }

    @Override
    public void getPersonDetail(int id, GetPersonDetailCallback callback) {
        mDataSource.getPersonDetail(id, callback);
    }
}
