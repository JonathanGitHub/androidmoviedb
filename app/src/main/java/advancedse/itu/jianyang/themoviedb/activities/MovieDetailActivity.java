package advancedse.itu.jianyang.themoviedb.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import APIResponses.MovieTrailerResponse;
import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieDetail.Genres;
import advancedse.itu.jianyang.themoviedb.datamodels.MovieDetail.MovieDetail;
import advancedse.itu.jianyang.themoviedb.datamodels.Videos.Result;
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

    TextView tvDirector;

    TextView tvScreenPlay;

    TextView tvCast;

    ImageView imageViewMovieBackDropPath;

    TextView tvBudget;

    TextView tvRevenue;

    TextView tvProductionCompanies;

    TextView tvTagline;

    TextView tvStatus;

    ArrayList<Result> results;

    MovieDetail movieDetail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

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

        imageViewMovieBackDropPath = (ImageView) findViewById(R.id.movie_back_drop);

//        intent.putExtra(MovieDBAPIConstants.JSON_POSTER_RELATIVE_PATH, movieListItemList.get(position).getPosterRelativePath());
//        intent.putExtra(MovieDBAPIConstants.JSON_TITLE, movieListItemList.get(position).getTitle());
//        intent.putExtra(MovieDBAPIConstants.JSON_OVERVIEW, movieListItemList.get(position).getOverview());
//        intent.putExtra(MovieDBAPIConstants.JSON_RELEASE_DATE, movieListItemList.get(position).getReleaseDate());
//        intent.putExtra(MovieDBAPIConstants.JSON_VOTE_AVERAGE, movieListItemList.get(position).getVoteAverage());

        String title = getIntent().getStringExtra(MovieDBAPIConstants.JSON_TITLE);
        String overView = getIntent().getStringExtra(MovieDBAPIConstants.JSON_OVERVIEW);
        String rating = getIntent().getStringExtra(MovieDBAPIConstants.JSON_VOTE_AVERAGE);
        String releaseDate = getIntent().getStringExtra(MovieDBAPIConstants.JSON_RELEASE_DATE);
        String backDropPath = getIntent().getStringExtra(MovieDBAPIConstants.JSON_BACK_DROP_PATH);

        String id = getIntent().getStringExtra(MovieDBAPIConstants.JSON_ID);

        tvMovieTitle.setText(title);
        tvMoviePlot.setText(overView);
        tvMovieRating.setText(rating);
        tvMovieYear.setText(releaseDate);

        Picasso.with(this).load(MOVIE_IMAGE_BASE_URL_W_500 + backDropPath).into(imageViewMovieBackDropPath);

        Ion.with(this)
            .load(MovieDBAPIConstants.API_BASE_URL + id + MovieDBAPIConstants.API_MOVIE_VIDEOS_PARAM  + MovieDBAPIConstants.API_KEY)
            .asString()
            .setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (result != null) {
                        results = MovieTrailerResponse.parseJSON(result).getResults();
                        // If there is any movies
                        if (results != null && results.size() > 0) {
                            // Adding items to gridView
                            imageViewMovieBackDropPath.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String youtube_base_url = "https://www.youtube.com/watch?v=";
                                    String youtube_video_key = results.get(0).getKey();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_base_url + youtube_video_key)));
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
    }

}
