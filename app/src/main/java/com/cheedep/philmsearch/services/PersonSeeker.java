package com.cheedep.philmsearch.services;

import android.util.Log;

import com.cheedep.philmsearch.model.Person;
import com.cheedep.philmsearch.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by Chandu on 6/22/2014.
 */
public class PersonSeeker extends GenericSeeker<Person> {

    private static final String PERSON_SEARCH_PATH = "search/person";

    @Override
    public ArrayList<Person> find(String query) {
        return retrievePersonsList(query);
    }

    @Override
    public ArrayList<Person> find(String query, int maxResults) {
        return retrieveFirstResults(retrievePersonsList(query),maxResults);
    }

    private ArrayList<Person> retrievePersonsList(String query){
        String url = constructSearchUrl(query);
        String response = httpRetriever.retrieve(url);
        Log.d(getClass().getSimpleName(), response);
        return JSONUtils.parsePersonResponse(response);
    }

    @Override
    public String retrieveSearchMethodPath() {
        return PERSON_SEARCH_PATH;
    }
}
