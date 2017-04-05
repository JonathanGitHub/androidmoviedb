package APIResponses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import advancedse.itu.jianyang.themoviedb.datamodels.MovieList.MovieListItem;

/**
 * Created by jianyang on 3/28/17.
 */

public class MovieListResponse{

    /**
     * Matching variable names to JSON keys
     * For instance, if our property name matches that of the JSON key, then we do not need to
     * annotate the attributes. However, if we have a different name we wish to use, we can simply
     * annotate the declaration with @SerializedName:
     */
    @SerializedName("results")
    ArrayList<MovieListItem> movieList;

    // public constructor is necessary for collections
    public MovieListResponse() {
        movieList = new ArrayList<>();
    }

    public ArrayList<MovieListItem> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<MovieListItem> movieList) {
        this.movieList = movieList;
    }

    public static MovieListResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieListResponse boxOfficeMovieResponse = gson.fromJson(response, MovieListResponse.class);
        return boxOfficeMovieResponse;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
