package advancedse.itu.jianyang.themoviedb.adapters;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import advancedse.itu.jianyang.themoviedb.R;

/**
 * Created by edd571 on 3/24/17.
 */

public class MovieListRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView movieThumbnail;
    public TextView movieTitle;

    public MovieListRecyclerViewHolder(View itemView) {
        super(itemView);

        movieThumbnail = (ImageView) itemView.findViewById(R.id.movie_thumb_nail);
        movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
    }


}
