package advancedse.itu.jianyang.themoviedb.datamodels.Videos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edd571 on 4/3/17.
 */

public class Result implements Parcelable{
    private String id;
    private String key;

    protected Result(Parcel in) {
        id = in.readString();
        key = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
    }
}
