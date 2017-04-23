package com.adebayoyeleye.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;


import android.support.v7.app.AppCompatActivity;

import com.adebayoyeleye.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Adebayo on 23/04/2017.
 */

public final class NetworkUtils {
    private static final String TMDB_BASE_URL =
            "https://api.themoviedb.org/3/discover/movie";
//            "https://api.themoviedb.org/3/discover/movie?api_key=75e0e640b06746ee8ed8239099a16507&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    final static String SORT_PARAM = "sort_by";
    final static String API_KEY_PARAM = "api_key";
    final static String PAGE_PARAM =  "page";

    public static URL buildUrl(String sortBy, Context context) {

        String apiKey = context.getResources().getString(R.string.api_key_string);
        Uri builtUri= Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(SORT_PARAM, sortBy)
                .appendQueryParameter(PAGE_PARAM,"1")
                .build();

        URL url=null;
        try {
            url=new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
