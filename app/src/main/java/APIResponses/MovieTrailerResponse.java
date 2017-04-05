package APIResponses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import advancedse.itu.jianyang.themoviedb.datamodels.Videos.Result;

/**
 * Created by jianyang on 4/3/17.
 */

public class MovieTrailerResponse {
    private String id;
    @SerializedName("results")
    private ArrayList<Result> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public static MovieTrailerResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieTrailerResponse movieTrailerResponse = gson.fromJson(response, MovieTrailerResponse.class);
        return movieTrailerResponse;
    }
}
