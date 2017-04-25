package com.adebayoyeleye.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


/**
 * Created by Adebayo on 24/04/2017.
 */

public final class MoviesJsonUtils {

//    JSONArray results = setResults();

    public static JSONArray setResults(String JSONString) throws JSONException {
        JSONObject object = new JSONObject(JSONString);
        JSONArray results = object.getJSONArray("results");
//            return weather.getString("condition");
        return results;

    }
}
