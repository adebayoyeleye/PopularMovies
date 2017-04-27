package com.adebayoyeleye.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adebayo on 24/04/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private JSONArray results;
    private Context context;
    private final MoviesAdapterOnClickHandler mClickHandler;


    MoviesAdapter(Context c, MoviesAdapterOnClickHandler clickHandler) {
        context = c;
        mClickHandler = clickHandler;
    }


    /**
     * The interface that receives onClick messages.
     */
    interface MoviesAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }



    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMovieTitle;
        private ImageView mMoviePoster;

        MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mMoviePoster = (ImageView)itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            JSONObject movieClicked;
            StringBuilder movieDetails = new StringBuilder("");
            try {
                movieClicked = results.getJSONObject(adapterPosition);
                movieDetails.append(movieClicked.getString("poster_path")+"\n\n\n");
                movieDetails.append(movieClicked.getString("original_title")+"\n\n\n");
                movieDetails.append("Synopsis\n"+movieClicked.getString("overview")+"\n\n\n");
                movieDetails.append("User Rating\n"+movieClicked.getString("vote_average")+"\n\n\n");
                movieDetails.append("Release Date\n"+movieClicked.getString("release_date")+"\n\n\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mClickHandler.onClick(movieDetails.toString());
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movies_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        try {
            JSONObject movie = results.getJSONObject(position);
            String title = movie.getString("title");
            String posterPath = movie.getString("poster_path");

            holder.mMovieTitle.setText(title);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+posterPath).into(holder.mMoviePoster);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (results==null)return 0;
        return results.length();
    }

    void setResults(JSONArray results) {
        this.results = results;
        notifyDataSetChanged();
    }
}
