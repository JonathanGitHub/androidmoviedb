package advancedse.itu.jianyang.themoviedb.activities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.adapters.MovieListRecylerViewAdapter;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;
import advancedse.itu.jianyang.themoviedb.datamodels.Movie;

import static advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants.API_SORT_BY_PLAYING;
import static advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants.API_SORT_BY_POPULARITY;
import static advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants.API_SORT_BY_RATINGS;
import static advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants.API_SORT_BY_UNCOMING;
import static advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants.DATA_KEY;

/**
 * Created by jianyang on 3/23/17.
 */

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Movie> movieList = new ArrayList<>();

    private String tooBarTitle = "";

    private String movieCategoryUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);

        determineMovieListCategory();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.back_button);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.back_button);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        getSupportActionBar().setTitle(tooBarTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        fetchDataAndPopulateObject(movieCategoryUrl);

    }

    private void determineMovieListCategory() {
        MovieDBAPIConstants.MOVIECATEGORY moviecategory =
            (MovieDBAPIConstants.MOVIECATEGORY) getIntent().getSerializableExtra(MovieDBAPIConstants.CATEGORY_KEY);

        if (moviecategory == MovieDBAPIConstants.MOVIECATEGORY.POPULAR) {
            movieCategoryUrl = MovieDBAPIConstants.API_BASE_URL + API_SORT_BY_POPULARITY + MovieDBAPIConstants.API_KEY;
            tooBarTitle = getString(R.string.popular_menu);
        } else if (moviecategory == MovieDBAPIConstants.MOVIECATEGORY.TOP_RATED) {
            movieCategoryUrl = MovieDBAPIConstants.API_BASE_URL + API_SORT_BY_RATINGS + MovieDBAPIConstants.API_KEY;
            tooBarTitle = getString(R.string.top_rated_menu);
        } else if (moviecategory == MovieDBAPIConstants.MOVIECATEGORY.UPCOMING) {
            movieCategoryUrl = MovieDBAPIConstants.API_BASE_URL + API_SORT_BY_UNCOMING + MovieDBAPIConstants.API_KEY;
            tooBarTitle = getString(R.string.upcoming_menu);
        } else if (moviecategory == MovieDBAPIConstants.MOVIECATEGORY.NOW_PLAYING) {
            movieCategoryUrl = MovieDBAPIConstants.API_BASE_URL + API_SORT_BY_PLAYING + MovieDBAPIConstants.API_KEY;
            tooBarTitle = getString(R.string.now_playing_menu);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the data when configuration changes
        outState.putParcelableArrayList(DATA_KEY, movieList);
        super.onSaveInstanceState(outState);
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
                                    MovieListRecylerViewAdapter adapter =
                                        new MovieListRecylerViewAdapter(movieList, getApplicationContext());
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

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, MovieListActivity.class);

        switch (item.getItemId()) {

            case R.id.menu_favorite:
//                setFragmentTitle(R.string.favorites);
//                favoriteListFragment = new FavoriteListFragment();
//                switchContent(favoriteListFragment, FavoriteListFragment.TAG);
                return true;
            case R.id.menu_playing:
//                setFragmentTitle(R.string.favorites);
//                favoriteListFragment = new FavoriteListFragment();
//                switchContent(favoriteListFragment, FavoriteListFragment.TAG);

                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.NOW_PLAYING);
                startActivity(intent);

                return true;
            case R.id.menu_popular:
//                setFragmentTitle(R.string.favorites);
//                favoriteListFragment = new FavoriteListFragment();
//                switchContent(favoriteListFragment, FavoriteListFragment.TAG);

                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.POPULAR);
                startActivity(intent);

                return true;
            case R.id.menu_top_rated:
//                setFragmentTitle(R.string.favorites);
//                favoriteListFragment = new FavoriteListFragment();
//                switchContent(favoriteListFragment, FavoriteListFragment.TAG);

                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.TOP_RATED);
                startActivity(intent);

                return true;
            case R.id.menu_upcoming:
//                setFragmentTitle(R.string.favorites);
//                favoriteListFragment = new FavoriteListFragment();
//                switchContent(favoriteListFragment, FavoriteListFragment.TAG);

                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.UPCOMING);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
