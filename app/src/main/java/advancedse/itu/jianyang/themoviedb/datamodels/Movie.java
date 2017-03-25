package advancedse.itu.jianyang.themoviedb.datamodels;

/**
 * Created by jianyang on 3/24/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JianYang on 12/3/15.
 */
public class Movie implements Parcelable {
    private String id;
    private String overview;
    private String releaseDate;
    private String posterRelativePath;
    private String title;
    private String popularity;
    private String voteAverage;


    public Movie() {

    }

    public Movie(String id, String overview, String releaseDate, String posterRelativePath, String title, String popularity,
        String voteAverage) {
        this.id = id;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterRelativePath = posterRelativePath;
        this.title = title;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterRelativePath() {
        return posterRelativePath;
    }

    public void setPosterRelativePath(String posterRelativePath) {
        this.posterRelativePath = posterRelativePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id='" + id + '\'' +
            ", overview='" + overview + '\'' +
            ", releaseDate='" + releaseDate + '\'' +
            ", posterRelativePath='" + posterRelativePath + '\'' +
            ", title='" + title + '\'' +
            ", voteAverage=" + voteAverage +
            ", popularity=" + popularity +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Parcelling part, since our Movie is a customized class so if we wanna retain this object when user
    // rotates screen, we must implements Parcelable interface
    public Movie(Parcel in)
    {
        String[] data = new String[7];
        in.readStringArray(data);
        this.id = data[0];
        this.overview = data[1];
        this.releaseDate = data[2];
        this.posterRelativePath = data[3];
        this.title = data[4];
        this.popularity = data[5];
        this.voteAverage = data[6];
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
            this.id, this.overview, this.releaseDate, this.posterRelativePath, this.title, this.popularity, this.voteAverage
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {

        @Override
        public Movie createFromParcel(Parcel source)
        {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
}