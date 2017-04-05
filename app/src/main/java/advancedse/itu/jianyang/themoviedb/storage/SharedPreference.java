package advancedse.itu.jianyang.themoviedb.storage;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import advancedse.itu.jianyang.themoviedb.datamodels.MovieList.MovieListItem;

/**
 * Created by jianyang on 4/4/17.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "THEMOVIEDB";
    public static final String FAVORITES = "my_Favorite";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, ArrayList<MovieListItem> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
            Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, MovieListItem movieListItem) {
        ArrayList<MovieListItem> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        if (!favorites.contains(movieListItem)) {
            favorites.add(movieListItem);
            saveFavorites(context, favorites);
        }
    }

    public void removeFavorite(Context context, MovieListItem movieListItem) {
        ArrayList<MovieListItem> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(movieListItem);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<MovieListItem> getFavorites(Context context) {
        SharedPreferences settings;
        List<MovieListItem> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
            Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MovieListItem[] favoriteItems = gson.fromJson(jsonFavorites,
                MovieListItem[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;

        return (ArrayList<MovieListItem>) favorites;
    }
}
