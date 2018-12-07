package com.framgia.moviedb.utils;

import com.framgia.moviedb.BuildConfig;

import static com.framgia.moviedb.utils.Constants.CREDITS_TYPE;
import static com.framgia.moviedb.utils.Constants.VIDEOS_TYPE;

public class APIUtils {
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String PRE_POSTER_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String PRE_BACKDROP_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String PRE_GET_MOVIES_URL = "https://api.themoviedb.org/3/movie/";
    public static final String PRE_DISCOVER_MOVIES_URL = "http://api.themoviedb.org/3/discover/movie/";
    public static final String PRE_GET_PERSON_URL = "https://api.themoviedb.org/3/person/";
    public static final String LANGUAGE_CONFIGURATION = "&language=en-US";
    public static final String SORT_CONFIGURATION = "&sort_by=popularity.desc";
    public static final String API_KEY_CONFIGURATION = "?api_key=";
    public static final String PAGE_NUMBER_CONFIGURATION = "&page=";
    public static final String PEOPLE_ID_CONFIGURATION = "&with_people=";
    public static final String GET_VIDEOS_TYPE = "/videos";
    public static final String GET_CREDITS_TYPE = "/credits";
    public static final String GET_RELATED_TYPE = "/similar";
    public static final String GET_RELEASED_DATE_TYPE = "/release_dates";
    public static final int FIRST_PAGE_NUMBER = 1;


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

    public static String getApiMovieInfoUrl(String type, String movieId) {
        StringBuilder api = new StringBuilder();
        api.append(PRE_GET_MOVIES_URL).append(movieId);
        switch (type) {
            case VIDEOS_TYPE:
                api.append(GET_VIDEOS_TYPE);
                break;
            case CREDITS_TYPE:
                api.append(GET_CREDITS_TYPE);
                break;
        }
        api.append(API_KEY_CONFIGURATION)
                .append(API_KEY)
                .append(LANGUAGE_CONFIGURATION);
        return api.toString();
    }

    public static String getMovieUrlById(String movieId) {
        StringBuilder api = new StringBuilder();
        api.append(PRE_GET_MOVIES_URL).append(movieId)
                .append(API_KEY_CONFIGURATION)
                .append(API_KEY)
                .append(LANGUAGE_CONFIGURATION);
        return api.toString();
    }

    public static String getRelatedMovieUrl(String movieId, int page) {
        StringBuilder api = new StringBuilder();
        api.append(PRE_GET_MOVIES_URL).append(movieId)
                .append(GET_RELATED_TYPE)
                .append(API_KEY_CONFIGURATION)
                .append(API_KEY)
                .append(LANGUAGE_CONFIGURATION)
                .append(PAGE_NUMBER_CONFIGURATION)
                .append(page);
        return api.toString();
    }

    public static String getRelatedMovieUrlById(int id, int page) {
        StringBuilder api = new StringBuilder();
        api.append(PRE_DISCOVER_MOVIES_URL)
                .append(API_KEY_CONFIGURATION)
                .append(API_KEY)
                .append(SORT_CONFIGURATION)
                .append(LANGUAGE_CONFIGURATION)
                .append(PAGE_NUMBER_CONFIGURATION)
                .append(page)
                .append(PEOPLE_ID_CONFIGURATION)
                .append(id);
        return api.toString();
    }

    public static String getPersonUrlById(int id) {
        StringBuilder api = new StringBuilder();
        api.append(PRE_GET_PERSON_URL).append(id)
                .append(API_KEY_CONFIGURATION)
                .append(API_KEY)
                .append(LANGUAGE_CONFIGURATION);
        return api.toString();
    }

    public static String getApiByGenre(String genreName, int pageNumber) {

        return "";
    }
}
