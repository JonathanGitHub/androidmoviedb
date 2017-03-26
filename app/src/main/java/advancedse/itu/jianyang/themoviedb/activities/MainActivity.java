package advancedse.itu.jianyang.themoviedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import advancedse.itu.jianyang.themoviedb.R;
import advancedse.itu.jianyang.themoviedb.apis.MovieDBAPIConstants;

/**
 * Created by JianYang on 3/23/17.
 */

public class MainActivity extends AppCompatActivity {

    ImageButton imageButtonPopular;

    ImageButton imageButtonTopRated;

    ImageButton imageButtonUpcoming;

    ImageButton imageButtonPlaying;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        imageButtonPopular = (ImageButton) findViewById(R.id.image_view_category_popular);
        imageButtonTopRated = (ImageButton) findViewById(R.id.image_view_category_top_rated);
        imageButtonUpcoming = (ImageButton) findViewById(R.id.image_view_category_upcoming);
        imageButtonPlaying = (ImageButton) findViewById(R.id.image_view_category_now_playing);

        imageButtonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.POPULAR);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imageButtonTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.TOP_RATED);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imageButtonUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.UPCOMING);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imageButtonPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                intent.putExtra(MovieDBAPIConstants.CATEGORY_KEY, MovieDBAPIConstants.MOVIECATEGORY.NOW_PLAYING);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
