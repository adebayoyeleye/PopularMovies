package com.adebayoyeleye.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private String movieDetails;
    private String posterDetails;
    private TextView mMovieDetailsDisplay;
    private ImageView mPosterImageDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mMovieDetailsDisplay = (TextView) findViewById(R.id.tv_movie_details);
        mPosterImageDisplay = (ImageView) findViewById(R.id.iv_movie_poster_details);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String extraText = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

//                String url = "http://www.youtube-nocookie.com/embed/zaaU9lJ34c5?rel=0";
                movieDetails  = extraText.substring(extraText.indexOf("jpg") + 3, extraText.length());
                posterDetails = extraText.substring(0,extraText.indexOf("jpg")+3);

                mMovieDetailsDisplay.setText(movieDetails);
                Picasso.with(this).load("http://image.tmdb.org/t/p/w500/"+posterDetails).into(mPosterImageDisplay);

            }
        }

    }
}
