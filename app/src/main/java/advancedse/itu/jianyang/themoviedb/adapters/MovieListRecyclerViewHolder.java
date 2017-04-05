package advancedse.itu.jianyang.themoviedb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import advancedse.itu.jianyang.themoviedb.R;

/**
 * Created by jianyang on 3/24/17.
 */

public class MovieListRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView movieThumbnail;
    public TextView movieTitle;
    public ImageView add_to_favorite;

    public MovieListRecyclerViewHolder(View itemView) {
        super(itemView);

        movieThumbnail = (ImageView) itemView.findViewById(R.id.movie_thumb_nail);
        movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        add_to_favorite = (ImageView) itemView.findViewById(R.id.add_to_favorite);
    }


}
