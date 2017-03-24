package advancedse.itu.jianyang.themoviedb.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import advancedse.itu.jianyang.themoviedb.R;

/**
 * Created by JianYang on 3/23/17.
 *
 *
 */

public class MainActivity extends Activity {

    ImageButton imageButtonPopular;
    ImageButton imageButtonTopRated;
    ImageButton imageButtonUpcoming;
    ImageButton imageButtonPlaying;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar);

        imageButtonPopular = (ImageButton) findViewById(R.id.image_view_category_popular);
        imageButtonTopRated = (ImageButton) findViewById(R.id.image_view_category_top_rated);
        imageButtonUpcoming = (ImageButton) findViewById(R.id.image_view_category_upcoming);
        imageButtonPlaying = (ImageButton) findViewById(R.id.image_view_category_now_playing);


        imageButtonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageButtonTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageButtonUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageButtonPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}
