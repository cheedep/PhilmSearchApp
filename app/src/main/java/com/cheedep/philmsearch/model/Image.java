package com.cheedep.philmsearch.model;

import java.io.Serializable;

/**
 * Created by Chandu on 6/22/2014.
 */
public class Image implements Serializable{

    public static final String SIZE_ORIGINAL = "original";
    public static final String SIZE_MID = "mid";
    public static final String SIZE_COVER = "cover";
    public static final String SIZE_THUMB = "thumb";

    public static final String TYPE_PROFILE = "profile";
    public static final String TYPE_POSTER = "poster";

    public String type;
    public String url;
    public String size;
    public int width;
    public int height;

}
