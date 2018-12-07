package com.framgia.moviedb.data.source;

import com.framgia.moviedb.data.model.Person;

public interface PersonDataSource {
    interface GetPersonDetailCallback {
        void onDataLoaded(Person person);

        void onDataNotAvailable(Exception e);
    }

    void getPersonDetail(int id, GetPersonDetailCallback callback);
}
