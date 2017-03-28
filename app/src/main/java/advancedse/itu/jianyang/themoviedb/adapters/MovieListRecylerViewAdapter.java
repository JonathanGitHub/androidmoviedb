package advancedse.itu.jianyang.themoviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.activities.MovieDetailActivity;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieListItem;

/**
 * Created by jianyang on 3/24/17.
 */

public class MovieListRecylerViewAdapter extends RecyclerView.Adapter<MovieListRecyclerViewHolder> {


    private List<MovieListItem> movieListItemList;

    private Context context;

    public static String API_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    public MovieListRecylerViewAdapter(List<MovieListItem> movieListItemList, Context context) {
        this.movieListItemList = movieListItemList;
        this.context = context;
    }

    @Override
    public MovieListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, null);
        MovieListRecyclerViewHolder viewHolder = new MovieListRecyclerViewHolder(layoutView);
        return viewHolder;
    }

    /**
     * TODO....Move onclick listener logic to Fragment or activity to decouple logic... Use interface listener pattern
     * TODO....Single Responsibility for adapter
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieListRecyclerViewHolder holder, final int position) {
        Picasso.with(context).load(API_BASE_URL + movieListItemList.get(position).getPosterRelativePath()).into(holder.movieThumbnail);
        holder.movieTitle.setText(movieListItemList.get(position).getVoteAverage() + " / 10");

        holder.movieThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDBAPIConstants.JSON_POSTER_RELATIVE_PATH, movieListItemList.get(position).getPosterRelativePath());
                intent.putExtra(MovieDBAPIConstants.JSON_TITLE, movieListItemList.get(position).getTitle());
                intent.putExtra(MovieDBAPIConstants.JSON_OVERVIEW, movieListItemList.get(position).getOverview());
                intent.putExtra(MovieDBAPIConstants.JSON_RELEASE_DATE, movieListItemList.get(position).getReleaseDate());
                intent.putExtra(MovieDBAPIConstants.JSON_VOTE_AVERAGE, movieListItemList.get(position).getVoteAverage());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieListItemList.size();
    }
}
