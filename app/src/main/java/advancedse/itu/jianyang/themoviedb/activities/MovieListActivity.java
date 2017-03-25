package advancedse.itu.jianyang.themoviedb.activities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.adapters.MovieListRecyclerViewHolder;
import advancedse.itu.jianyang.themoviedb.adapters.MovieListRecylerViewAdapter;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;
import advancedse.itu.jianyang.themoviedb.datamodels.Movie;

/**
 * Created by jianyang on 3/23/17.
 */

public class MovieListActivity extends AppCompatActivity {
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        String movieCategoryUrl = MovieDBAPIConstants.API_BASE_URL + MovieDBAPIConstants.API_KEY;

        MovieDBAPIConstants.MOVIECATEGORY moviecategory =
            (MovieDBAPIConstants.MOVIECATEGORY) getIntent().getSerializableExtra(MovieDBAPIConstants.CATEGORY_KEY);

//        if (moviecategory == MovieDBAPIConstants.MOVIECATEGORY.POPULAR) {
//
//        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        fetchDataAndPopulateObject(movieCategoryUrl);

//        MovieListRecylerViewAdapter rcAdapter = new MovieListRecylerViewAdapter(movieListData, this);
//        recyclerView.setAdapter(rcAdapter);

    }

    // fetchData asynchronously with Ion lib based on different endpoints
    private void fetchDataAndPopulateObject(String url) {
        // Make http call and process response
        Ion.with(this)
            .load(url)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    // do stuff with the result or error
                    if (result != null) {
                        // Remove mProgressBar after network callback
                        //mProgressBar.setVisibility(View.INVISIBLE);

                        // Clear previous data if there is any
                        movieList.clear();
                        recyclerView.setAdapter(null);

                        try {
                            // find the results and populate
                            if (result.getAsJsonArray(MovieDBAPIConstants.JSON_RESULTS) != null) {
                                JsonArray results = result.getAsJsonArray(MovieDBAPIConstants.JSON_RESULTS);

                                // find the results and populate
                                for (int i = 0; i < results.size(); i++) {
                                    Movie movie = new Movie();

                                    JsonObject jsonObject = results.get(i).getAsJsonObject();

                                    movie.setId(parseJson(jsonObject, MovieDBAPIConstants.JSON_ID));
                                    movie.setTitle(parseJson(jsonObject, MovieDBAPIConstants.JSON_TITLE));
                                    movie.setReleaseDate(parseJson(jsonObject, MovieDBAPIConstants.JSON_RELEASE_DATE));
                                    movie.setOverview(parseJson(jsonObject, MovieDBAPIConstants.JSON_OVERVIEW));
                                    movie.setVoteAverage(parseJson(jsonObject, MovieDBAPIConstants.JSON_VOTE_AVERAGE));
                                    movie.setPopularity(parseJson(jsonObject, MovieDBAPIConstants.JSON_POPULARITY));
                                    movie.setPosterRelativePath(parseJson(jsonObject, MovieDBAPIConstants.JSON_POSTER_RELATIVE_PATH));

                                    movieList.add(movie);
                                }
                                // If there is any movies
                                if (movieList.size() > 0) {
                                    // Adding items to gridView
                                    MovieListRecylerViewAdapter adapter = new MovieListRecylerViewAdapter(movieList, getApplicationContext());
                                    recyclerView.setAdapter(adapter);
                                }
                                // Display empty data toast
                                else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.no_data_message), Toast.LENGTH_LONG)
                                        .show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.no_data_message), Toast.LENGTH_LONG)
                                    .show();
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            Toast.makeText(getApplicationContext(), getString(R.string.server_error_message), Toast.LENGTH_LONG)
                                .show();
                        }
                    } else {
                        // Remove mProgressBar
                       // mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_message), Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    // Private method to parseJson field
    private String parseJson(JsonObject jsonObject, String jsonStringKey) {

        // Please note that != null is NOT equal to isJsonNull()
        return !jsonObject.get(jsonStringKey).isJsonNull() ? jsonObject.get(jsonStringKey).getAsString() : "";
    }
}
