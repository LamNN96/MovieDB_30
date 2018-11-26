package com.framgia.moviedb.utils;

import com.framgia.moviedb.BuildConfig;

public class APIUtils {
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String PRE_POSTER_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String PRE_BACKDROP_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String PRE_GET_MOVIES_URL = "https://api.themoviedb.org/3/movie/";
    public static final String LANGUAGE_CONFIGURATION = "&language=en-US";
    public static final String API_KEY_CONFIGURATION = "?api_key=";
    public static final String PAGE_NUMBER_CONFIGURATION = "&page=";

    public static String getApiUrl(String groupName, int pageNumber) {
        StringBuilder api = new StringBuilder();
        api.append(PRE_GET_MOVIES_URL)
                .append(groupName)
                .append(API_KEY_CONFIGURATION)
                .append(API_KEY)
                .append(LANGUAGE_CONFIGURATION)
                .append(PAGE_NUMBER_CONFIGURATION)
                .append(pageNumber);
        return api.toString();
    }

    public static String getApiByGenre(String genreName, int pageNumber) {

        return "";
    }
}
