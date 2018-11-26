package com.framgia.moviedb.data.repository;

import android.os.AsyncTask;
import com.framgia.moviedb.data.model.GroupMovie;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MoviesDataSource;
import com.framgia.moviedb.utils.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesContentAsyncTask extends AsyncTask<String, Void, List<GroupMovie>> {
	private static final int LIMIT_IMAGE_VIEW_FLIPPER = 5;
	private MoviesDataSource.LoadMoviesCallback mCallback;
	private Exception mException;
	
	public MoviesContentAsyncTask(MoviesDataSource.LoadMoviesCallback callback) {
		mCallback = callback;
	}
	
	@Override protected List<GroupMovie> doInBackground(String... strings) {
		List<GroupMovie> groupMovies = new ArrayList<>();
		MoviesFetchData moviesFetchData = new MoviesFetchData();
		for (int i = 0; i < strings.length; i++) {
			try {
				List<Movie> movies;
				String json = moviesFetchData.getMovies(strings[i]);
				movies = getMovies(json);
				groupMovies.add(
						new GroupMovie(Constants.GROUP_TITLES[i], String.valueOf(i), movies));
			} catch (IOException e) {
				mException = e;
			} catch (JSONException e) {
				mException = e;
			} catch (Exception e) {
				mException = e;
			}
		}
		return groupMovies;
	}
	
	@Override protected void onPostExecute(List<GroupMovie> groupMovies) {
		super.onPostExecute(groupMovies);
		List<Movie> movies = getMoviesForFlipper(groupMovies);
		if (mCallback == null) return;
		if (mException == null) {
			mCallback.onMoviesLoaded(groupMovies, movies);
		} else {
			mCallback.onDataNotAvailable(mException);
		}
	}
	
	private List<Movie> getMoviesForFlipper(List<GroupMovie> groupMovies) {
		List<Movie> movies = new ArrayList<>();
		for (int i = 0; i < groupMovies.size(); i++) {
			if (i == Constants.POPULAR_POSITION) {
				for (int j = 0; j < LIMIT_IMAGE_VIEW_FLIPPER; j++) {
					movies.add(groupMovies.get(i).getMovies().get(j));
				}
			}
		}
		return movies;
	}
	
	private List<Movie> getMovies(String json) throws Exception {
		List<Movie> movies = new ArrayList<>();
		JSONObject jsonObject = new JSONObject(json);
		if (jsonObject != null) {
			JSONArray jsonMovies = jsonObject.getJSONArray("results");
			for (int j = 0; j < jsonMovies.length(); j++) {
				JSONObject item = jsonMovies.getJSONObject(j);
				if (item != null) {
					movies.add(new Movie(item));
				}
			}
		}
		return movies;
	}
}
