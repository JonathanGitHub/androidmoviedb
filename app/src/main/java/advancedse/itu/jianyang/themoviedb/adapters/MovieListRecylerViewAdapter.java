package advancedse.itu.jianyang.themoviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.activities.MovieDetailActivity;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieList.MovieListItem;
import advancedse.itu.jianyang.themoviedb.storage.SharedPreference;

/**
 * Created by jianyang on 3/24/17.
 */

public class MovieListRecylerViewAdapter extends RecyclerView.Adapter<MovieListRecyclerViewHolder> {


    private List<MovieListItem> movieListItemList;

    private SharedPreference sharedPreference;

    private Context context;

    public static final String WHITE_KEY = "white";

    public static final String RED_KEY = "red";

    public static String MOVIE_IMAGE_BASE_URL_W_185 = "http://image.tmdb.org/t/p/w185/";

    public static String MOVIE_IMAGE_BASE_URL_W_500 = "http://image.tmdb.org/t/p/w500/";




    public MovieListRecylerViewAdapter(List<MovieListItem> movieListItemList, Context context) {
        this.movieListItemList = movieListItemList;
        this.context = context;

        sharedPreference = new SharedPreference();
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
    public void onBindViewHolder(final MovieListRecyclerViewHolder holder, final int position) {
        Picasso.with(context).load(MOVIE_IMAGE_BASE_URL_W_185 + movieListItemList.get(position).getPosterRelativePath()).into(holder.movieThumbnail);
        holder.movieTitle.setText(movieListItemList.get(position).getVoteAverage() + " / 10");


        if (checkFavoriteItem(movieListItemList.get(position))) {
            holder.add_to_favorite.setImageResource(R.drawable.white_solid_heart);
            holder.add_to_favorite.setTag(RED_KEY);
        } else {
            holder.add_to_favorite.setImageResource(R.drawable.white_hallow_heart);
            holder.add_to_favorite.setTag(WHITE_KEY);
        }


        holder.add_to_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = view.getTag().toString();
                if (tag.equalsIgnoreCase(WHITE_KEY)) {
                    sharedPreference.addFavorite(context, movieListItemList.get(position));
                    holder.add_to_favorite.setTag(RED_KEY);
                    holder.add_to_favorite.setImageResource(R.drawable.white_solid_heart);
                } else {
                    sharedPreference.removeFavorite(context, movieListItemList.get(position));
                    holder.add_to_favorite.setTag(WHITE_KEY);
                    holder.add_to_favorite.setImageResource(R.drawable.white_hallow_heart);
                }
            }
        });


        holder.movieThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDBAPIConstants.JSON_ID, movieListItemList.get(position).getId());
                intent.putExtra(MovieDBAPIConstants.JSON_POSTER_RELATIVE_PATH, movieListItemList.get(position).getPosterRelativePath());
                intent.putExtra(MovieDBAPIConstants.JSON_TITLE, movieListItemList.get(position).getTitle());
                intent.putExtra(MovieDBAPIConstants.JSON_OVERVIEW, movieListItemList.get(position).getOverview());
                intent.putExtra(MovieDBAPIConstants.JSON_RELEASE_DATE, movieListItemList.get(position).getReleaseDate());
                intent.putExtra(MovieDBAPIConstants.JSON_VOTE_AVERAGE, movieListItemList.get(position).getVoteAverage());
                intent.putExtra(MovieDBAPIConstants.JSON_BACK_DROP_PATH, movieListItemList.get(position).getBackDropPath());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieListItemList.size();
    }



    //check if movie is saved in my favorite list or not
    public boolean checkFavoriteItem(MovieListItem movieListItemToCompare) {
        boolean check = false;
        ArrayList<MovieListItem> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (MovieListItem movieListItem : favorites) {
                if (movieListItem.equals(movieListItemToCompare)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

}
