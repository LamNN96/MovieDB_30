package com.framgia.moviedb.data.model;

import java.util.List;

public class GroupMovie {
    private String mTitle;
    private String mId;
    private List<Movie> mMovies;

    public GroupMovie() {
    }

    public GroupMovie(String title, String id,
            List<Movie> movies) {
        mTitle = title;
        mId = id;
        mMovies = movies;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
