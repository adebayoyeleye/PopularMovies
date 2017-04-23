package com.adebayoyeleye.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.adebayoyeleye.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView mDisplayResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayResult=(TextView)findViewById(R.id.tv_result_display);
        String sortBy = getResources().getString(R.string.sort_by_string);
        new AClass().execute(NetworkUtils.buildUrl(sortBy, this));

    }

    private class AClass extends AsyncTask<URL,Void,String> {
        @Override
        protected String doInBackground(URL... params) {
            URL moviesUrl = params[0];
            String moviesResult=null;
            try {
                moviesResult = NetworkUtils.getResponseFromHttpUrl(moviesUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
            return moviesResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null && !s.equals("")){
                mDisplayResult.setText(s);
            }
        }
    }

}
