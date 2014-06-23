package com.cheedep.philmsearch.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chandu on 6/22/2014.
 */
public class Utils {

    public static void closeStreamQuietly(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            // ignore exception
        }
    }
}
