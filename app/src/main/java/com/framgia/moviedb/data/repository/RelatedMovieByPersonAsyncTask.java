package com.framgia.moviedb.data.repository;

import android.os.AsyncTask;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.data.source.PersonDataSource;
import com.framgia.moviedb.utils.APIUtils;
import com.framgia.moviedb.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RelatedMovieByPersonAsyncTask extends AsyncTask<Integer, Void, List<Movie>> {
    public static final int FIRST_ARGUMENT_POSITION = 0;
    public static final int SECOND_ARGUMENT_POSITION = 1;
    private MoviesDataSource.GetMovieByPersonIdCallback mCallback;
    private Exception mException;

    public RelatedMovieByPersonAsyncTask(MoviesDataSource.GetMovieByPersonIdCallback callback) {
        mCallback = callback;
    }

    @Override
    protected List<Movie> doInBackground(Integer... integers) {
        int id = integers[FIRST_ARGUMENT_POSITION];
        int page = integers[SECOND_ARGUMENT_POSITION];
        List<Movie> movies = new ArrayList<>();
        FetchData fetchData = new FetchData();
        try {
            String url = APIUtils.getRelatedMovieUrlById(id, page);
            String json = fetchData.getJsonData(url);
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonMovies = jsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
                for (int i = 0; i < jsonMovies.length(); i++) {
                    movies.add(new Movie(jsonMovies.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mException = e;
        } catch (Exception e) {
            e.printStackTrace();
            mException = e;
        }
        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (mException != null) {
            mCallback.onDataNotAvailable(mException);
        } else {
            mCallback.onDataLoaded(movies);
        }
        super.onPostExecute(movies);
    }
}
