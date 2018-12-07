package com.framgia.moviedb.data.source.remote;

import com.framgia.moviedb.data.repository.PersonDetailAsyncTask;
import com.framgia.moviedb.data.repository.RelatedMovieByPersonAsyncTask;
import com.framgia.moviedb.data.source.PersonDataSource;

public class PersonRemoteDataSource implements PersonDataSource {
    public static PersonRemoteDataSource sDataSource;

    private PersonRemoteDataSource() {
    }

    public static PersonRemoteDataSource getInstance() {
        if (sDataSource == null) {
            sDataSource = new PersonRemoteDataSource();
        }
        return sDataSource;
    }

    @Override
    public void getPersonDetail(int id, GetPersonDetailCallback callback) {
        new PersonDetailAsyncTask(callback).execute(id);
    }
}
