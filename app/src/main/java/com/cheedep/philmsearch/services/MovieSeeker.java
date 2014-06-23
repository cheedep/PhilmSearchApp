package com.cheedep.philmsearch.services;

import android.util.Log;

import com.cheedep.philmsearch.model.Movie;
import com.cheedep.philmsearch.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by Chandu on 6/22/2014.
 */
public class MovieSeeker extends GenericSeeker<Movie> {

    private static final String MOVIE_SEARCH_PATH = "search/movie";

    @Override
    public ArrayList<Movie> find(String query) {
        return retrieveMoviesList(query);
    }

    @Override
    public ArrayList<Movie> find(String query, int maxResults) {
        return retrieveFirstResults(retrieveMoviesList(query), maxResults);
    }

    private ArrayList<Movie> retrieveMoviesList(String query){
        String url = constructSearchUrl(query);
        String response = httpRetriever.retrieve(url);
        if(response == null)
        {
            Log.d(getClass().getSimpleName(), "Network error");
            return null;
        }
        Log.d(getClass().getSimpleName(), response);
        return JSONUtils.parseMovieResponse(response);
    }

    @Override
    public String retrieveSearchMethodPath() {
        return MOVIE_SEARCH_PATH;
    }
}
