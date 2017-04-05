package advancedse.itu.jianyang.themoviedb.apis;

/**
 * Created by jianyang on 3/24/17.
 */

public class MovieDBAPIConstants {

    public static String CATEGORY_KEY = "category";

    public enum MOVIECATEGORY{
        POPULAR, TOP_RATED, UPCOMING, NOW_PLAYING
    }
    public static String JSON_RESULTS = "results";

    public static String JSON_ID = "id";

    public static String JSON_TITLE = "title";

    public static String JSON_OVERVIEW = "overview";

    public static String JSON_VOTE_AVERAGE = "vote_average";

    public static String JSON_POPULARITY = "popularity";

    public static String JSON_BACK_DROP_PATH = "backdrop_path";

    public static String JSON_RELEASE_DATE = "release_date";

    public static String JSON_POSTER_RELATIVE_PATH = "poster_path";

    public static String API_KEY = "api_key=";

    public static String API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static String API_MOVIE_VIDEOS_PARAM = "/videos?";

    public static String API_SORT_BY_POPULARITY = "popular?";

    public static String API_SORT_BY_RATINGS = "top_rated?";

    public static String API_SORT_BY_UNCOMING = "upcoming?";

    public static String API_SORT_BY_PLAYING = "now_playing?";

    public static String DATA_KEY = "dataKey";

}
