package com.cheedep.philmsearch.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chandu on 6/22/2014.
 */
public class Movie implements Serializable{

    private final String BASE_URL = "http://image.tmdb.org/t/p/";
    private final String SIZE = "w92"; //['w92', 'w154', 'w185', 'w342', 'w500', 'original']
    private final String POSTER_SIZE = "w500";
    private final String SLASH = "/";

    public String score;
    public String popularity;
    public boolean translated;
    public boolean adult;
    public String language;
    public String originalName;
    public String name;
    public String type;
    public String id;
    public String imdbId;
    public String url;
    public String votes;
    public String rating;
    public String certification;
    public String overview;
    public String released;
    public String version;
    public String lastModifiedAt;
    public ArrayList<Image> imagesList;
    public String posterPath;

    public String retrieveThumbnail() {
        if (imagesList!=null && !imagesList.isEmpty()) {
            for (Image movieImage : imagesList) {
                if (movieImage.size.equalsIgnoreCase(Image.SIZE_THUMB) &&
                        movieImage.type.equalsIgnoreCase(Image.TYPE_POSTER)) {
                    return movieImage.url;
                }
            }
        }
        return null;
    }

    public String retrieveThumbnailUrl(){
        if(posterPath !=null && !posterPath.isEmpty())
            return BASE_URL + SIZE + posterPath;

        return null;
    }

    public String retrievePosterUrl(){
        if(posterPath !=null && !posterPath.isEmpty())
            return BASE_URL + POSTER_SIZE + posterPath;

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Movie [name=");
        builder.append(name);
        builder.append("] - ");
        builder.append(rating);
        return builder.toString();
    }
}
