package advancedse.itu.jianyang.themoviedb.datamodels.MovieDetail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jianyang on 4/4/17.
 */

public class MovieDetail implements Parcelable{

    private String id;
    private String imdb_id;
    private String budget;
    private String status;
    private String tagline;
    private String revenue;
    @SerializedName("production_companies")
    private ArrayList<ProductionCompany> productionCompanies;

    @SerializedName("genres")
    private ArrayList<Genres> genres;

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

    public MovieDetail() {

    }
    public MovieDetail(Parcel in) {
    }


    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(
        ArrayList<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public class ProductionCompany implements Parcelable{
        private String id;
        private String name;

        protected ProductionCompany(Parcel in) {
            id = in.readString();
            name = in.readString();
        }

        public final Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {
            @Override
            public ProductionCompany createFromParcel(Parcel in) {
                return new ProductionCompany(in);
            }

            @Override
            public ProductionCompany[] newArray(int size) {
                return new ProductionCompany[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
        }
    }

    public static MovieDetail parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieDetail movieDetailResponse = gson.fromJson(response, MovieDetail.class);
        return movieDetailResponse;
    }
}
