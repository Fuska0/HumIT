package com.example.czuko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//
public class Playlist implements Parcelable {
    private List<String> songList;
    private List<String> used;
    String song;
    public Playlist() {
        songList = new ArrayList<>();
        used = new ArrayList<>();
    }

    public void addSong(String song){
        songList.add(song);
    }

    public String getSong(){
        if(songList.size() == 0){
            Collections.shuffle(used);
            songList.addAll(used);
            used.clear();
        }

        song = songList.remove(0);
        used.add(song);
        return song;
    }
    protected Playlist(Parcel in) {
        songList = in.createStringArrayList();
        used = in.createStringArrayList();
    }

    public void setSongList(List<String> songList) {
        this.songList = songList;
        Log.d("Playlist!!!2", Integer.toString(songList.size()));
        used.clear();
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
        dest.writeStringList(used);
    }
}

