package com.cheedep.philmsearch.model;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chandu on 6/22/2014.
 */
public class Person implements Serializable{

    public String score;
    public String popularity;
    public String name;
    public String id;
    public String biography;
    public String url;
    public String version;
    public String lastModifiedAt;
    public ArrayList<Image> imagesList;
    public String profilePath;

}