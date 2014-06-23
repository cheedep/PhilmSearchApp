package com.cheedep.philmsearch.utils;

import com.cheedep.philmsearch.model.Movie;
import com.cheedep.philmsearch.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chandu on 6/22/2014.
 */
public class JSONUtils {

    public static ArrayList<Person> parsePersonResponse(String jsonStr) {

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray results = jsonObject.getJSONArray("results");

            ArrayList<Person> persons = new ArrayList<Person>();

            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                Person person = new Person();

                //person.adult = result.getBoolean("adult");
                person.name = result.getString("name");
                person.id = result.getString("id");
                person.popularity = result.getString("popularity");
                person.profilePath = result.getString("profile_path");
                persons.add(person);
            }

            return persons;
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;

    }

    public static ArrayList<Movie> parseMovieResponse(String jsonStr) {
        try{
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray results = jsonObject.getJSONArray("results");

            ArrayList<Movie> movies = new ArrayList<Movie>();

            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.rating = result.getString("vote_average");
                movie.adult = result.getBoolean("adult");
                movie.id = result.getString("id");
                movie.name = result.getString("title");
                movie.originalName = result.getString("original_title");
                movie.popularity = result.getString("popularity");
                movie.released = result.getString("release_date");
                movie.votes = result.getString("vote_count");
                movie.posterPath = result.getString("poster_path");
                movies.add(movie);
            }

            return movies;

        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
