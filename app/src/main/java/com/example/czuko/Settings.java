package com.example.czuko;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {
    private int numberOfSongs;
    private int guessSongTime;

    public Settings(int numberOfSongs, int guessSongTime) {
        this.numberOfSongs = numberOfSongs;
        this.guessSongTime = guessSongTime;
    }

    protected Settings(Parcel in) {
        numberOfSongs = in.readInt();
        guessSongTime = in.readInt();
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public int getGuessSongTime() {
        return guessSongTime;
    }

    public void setGuessSongTime(int guessSongTime) {
        this.guessSongTime = guessSongTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numberOfSongs);
        dest.writeInt(guessSongTime);
    }
}
