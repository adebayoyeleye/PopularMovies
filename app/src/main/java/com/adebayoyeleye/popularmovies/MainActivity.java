package com.adebayoyeleye.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adebayoyeleye.popularmovies.utilities.MoviesJsonUtils;
import com.adebayoyeleye.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mDisplayResult;
    private ImageView imageView;
    private JSONArray results;
    private RecyclerView mRecyclerView;

    private MoviesAdapter mMoviesAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        GridLayoutManager layoutManager
                = new GridLayoutManager(this,3);
//                = new GridLayoutManager(this,GridLayoutManager.DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMoviesData();
    }
    private void loadMoviesData() {
        String sortBy = getResources().getString(R.string.sort_by_string);
        new AClass().execute(NetworkUtils.buildUrl(sortBy, this));

//        mDisplayResult=(TextView)findViewById(R.id.tv_result_display);
//        imageView = (ImageView) findViewById(R.id.iv_test);

    }

    private void showWeatherDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    private class AClass extends AsyncTask<URL,Void,JSONArray> {
        @Override
        protected JSONArray doInBackground(URL... params) {
            URL moviesUrl = params[0];
            String moviesResultString=null;
            JSONArray moviesResult=null;
            try {
                moviesResultString = NetworkUtils.getResponseFromHttpUrl(moviesUrl);
                moviesResult = MoviesJsonUtils.setResults(moviesResultString);

            }catch (JSONException| IOException e){
                e.printStackTrace();
            }
            return moviesResult;
        }

        @Override
        protected void onPostExecute(JSONArray s) {
            super.onPostExecute(s);
            if (s!=null && s.length()!=0){
                mMoviesAdapter.setResults(s);

//                mDisplayResult.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }

}
