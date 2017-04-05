package advancedse.itu.jianyang.themoviedb.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import advancedse.itu.jianyang.themoviedb.APIResponses.MovieTrailerResponse;
import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieDetail.Genres;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieDetail.MovieDetail;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieList.MovieListItem;
import advancedse.itu.jianyang.themoviedb.datamodels.Videos.Result;
import advancedse.itu.jianyang.themoviedb.storage.SharedPreference;
import me.gujun.android.taggroup.TagGroup;

import static advancedse.itu.jianyang.themoviedb.adapters.MovieListRecylerViewAdapter.MOVIE_IMAGE_BASE_URL_W_500;

/**
 * Created by jianyang on 3/23/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    TextView tvMovieTitle;

    TagGroup tvMovieTags;

    TextView tvMovieRating;

    TextView tvMovieYear;

    TextView tvMovieLength;

    TextView tvMoviePlot;


    ImageView imageViewMovieBackDropPath;

    TextView tvBudget;

    TextView tvRevenue;

    TextView tvProductionCompanies;

    TextView tvTagline;

    TextView tvStatus;

    ImageView imageViewFavorite;

    ArrayList<Result> results;

    MovieDetail movieDetail;

    Button shareButton;

    String shareContent;


    private List<MovieListItem> movieListItemList;

    private SharedPreference sharedPreference;

    private Context context;

    public static final String WHITE_KEY = "white";

    public static final String RED_KEY = "red";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreference = new SharedPreference();

        context = this;

        movieListItemList = sharedPreference.getFavorites(this);

        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button_16by16);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MovieDetails");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        shareButton = (Button) toolbar.findViewById(R.id.share);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvMovieTitle = (TextView) findViewById(R.id.movie_title);
        tvMovieRating = (TextView) findViewById(R.id.movie_rating);
        tvMoviePlot = (TextView) findViewById(R.id.movie_plot);
        tvMovieYear = (TextView) findViewById(R.id.movie_year);
        tvMovieTags = (TagGroup) findViewById(R.id.movie_tags);

        tvBudget = (TextView) findViewById(R.id.movie_budget);
        tvRevenue = (TextView) findViewById(R.id.movie_revenue);
        tvStatus = (TextView) findViewById(R.id.movie_status);

        tvProductionCompanies = (TextView) findViewById(R.id.movie_production_company);
        tvTagline = (TextView) findViewById(R.id.movie_tagline);

        imageViewFavorite = (ImageView) findViewById(R.id.movie_detail_favorite);

        imageViewMovieBackDropPath = (ImageView) findViewById(R.id.movie_back_drop);

        final String title = getIntent().getStringExtra(MovieDBAPIConstants.JSON_TITLE);
        final String overView = getIntent().getStringExtra(MovieDBAPIConstants.JSON_OVERVIEW);
        String rating = getIntent().getStringExtra(MovieDBAPIConstants.JSON_VOTE_AVERAGE);
        String releaseDate = getIntent().getStringExtra(MovieDBAPIConstants.JSON_RELEASE_DATE);
        final String backDropPath = getIntent().getStringExtra(MovieDBAPIConstants.JSON_BACK_DROP_PATH);

        final String id = getIntent().getStringExtra(MovieDBAPIConstants.JSON_ID);

        tvMovieTitle.setText(title);
        tvMoviePlot.setText(overView);
        tvMovieRating.setText(rating);
        tvMovieYear.setText(releaseDate);

        Picasso.with(this).load(MOVIE_IMAGE_BASE_URL_W_500 + backDropPath).into(imageViewMovieBackDropPath);

        Ion.with(this)
            .load(MovieDBAPIConstants.API_BASE_URL + id + MovieDBAPIConstants.API_MOVIE_VIDEOS_PARAM + MovieDBAPIConstants.API_KEY)
            .asString()
            .setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (result != null) {
                        results = MovieTrailerResponse.parseJSON(result).getResults();
                        // If there is any movies
                        if (results != null && results.size() > 0) {

                            final String youtube_base_url = "https://www.youtube.com/watch?v=";
                            final String youtube_video_key = results.get(0).getKey();
                            shareContent = youtube_base_url + youtube_video_key;

                            // Adding items to gridView
                            imageViewMovieBackDropPath.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_base_url + youtube_video_key)));
                                }
                            });

                            shareButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    String shareBody = shareContent;
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                                }
                            });

                        }


                    } else {

                        // mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_message), Toast.LENGTH_LONG).show();
                    }


                }
            });

        Ion.with(this)
            .load(MovieDBAPIConstants.API_BASE_URL + id + "?" + MovieDBAPIConstants.API_KEY)
            .asString()
            .setCallback(new FutureCallback<String>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onCompleted(Exception e, String result) {
                    if (result != null) {
                        movieDetail = MovieDetail.parseJSON(result);
                        // If there is any movies
                        if (movieDetail.getGenres() != null && movieDetail.getGenres().size() > 0) {
                            ArrayList<String> tags = new ArrayList<String>();
                            for (Genres genres : movieDetail.getGenres()) {
                                tags.add(genres.getName());
                            }
                            tvMovieTags.setTags(tags);
                        }

                        tvBudget.setText("$" + movieDetail.getBudget());
                        tvTagline.setText(movieDetail.getTagline());
                        tvRevenue.setText("$" + movieDetail.getRevenue());
                        tvStatus.setText(movieDetail.getStatus());

                        ArrayList<MovieDetail.ProductionCompany> productionCompanies = movieDetail.getProductionCompanies();
                        StringBuilder sb = new StringBuilder();
                        for (MovieDetail.ProductionCompany productionCompany : productionCompanies) {
                            sb.append(productionCompany.getName()).append(" ");
                        }
                        tvProductionCompanies.setText(sb.toString());

                    }
                }
            });

        for (MovieListItem item : movieListItemList) {
            if (item.getId().equalsIgnoreCase(id)) {
                imageViewFavorite.setImageResource(R.drawable.pink_solid_heart);
                imageViewFavorite.setTag(RED_KEY);
            } else {
                imageViewFavorite.setImageResource(R.drawable.pink_hallow_heart);
                imageViewFavorite.setTag(WHITE_KEY);
            }
        }

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = view.getTag().toString();

                MovieListItem movieListItem = new MovieListItem();
                movieListItem.setId(id);
                movieListItem.setBackDropPath(backDropPath);
                movieListItem.setOverview(overView);
                movieListItem.setTitle(title);

                if (tag.equalsIgnoreCase(WHITE_KEY)) {

                    sharedPreference.addFavorite(context, movieListItem);
                    imageViewFavorite.setTag(RED_KEY);
                    imageViewFavorite.setImageResource(R.drawable.pink_solid_heart);
                } else {
                    sharedPreference.removeFavorite(context, movieListItem);
                    imageViewFavorite.setTag(WHITE_KEY);
                    imageViewFavorite.setImageResource(R.drawable.pink_hallow_heart);
                }
            }
        });

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

                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.MY_FAVORITE);
                startActivity(intent);

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
