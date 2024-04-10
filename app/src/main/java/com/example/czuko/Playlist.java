package com.example.czuko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;


public class Playlist implements Parcelable {
    private List<String> songList;

    public Playlist() {
        songList = new ArrayList<>();
    }

    public void addSong(String song){
        songList.add(song);
    }

    public String getSong(int i){
        return songList.get(i);
    }
    protected Playlist(Parcel in) {
        songList = in.createStringArrayList();
    }

    public void setSongList(List<String> songList) {
        this.songList = songList;
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public List<String> getSongList() {
        return songList;
    }

    public void clearSongList() {
        songList.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(songList);
    }
}

