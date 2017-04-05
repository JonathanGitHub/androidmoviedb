package advancedse.itu.jianyang.themoviedb.datamodels.MovieDetail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edd571 on 4/4/17.
 */

public class Genres implements Parcelable{
    private String id;
    private String name;

    protected Genres(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Genres> CREATOR = new Creator<Genres>() {
        @Override
        public Genres createFromParcel(Parcel in) {
            return new Genres(in);
        }

        @Override
        public Genres[] newArray(int size) {
            return new Genres[size];
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
