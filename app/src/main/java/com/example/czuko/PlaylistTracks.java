package com.example.czuko;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PlaylistTracks implements Runnable{
    private String token;
    private String playlistId;
    private Context context;
    private String tracks;
    private SpotifyAnswerParser parser;
    File path;
    File file;
    List<String> playlist;
    public PlaylistTracks(String token, String playlistId, Context context, String tracks) {
        this.token = token;
        this.playlistId = playlistId;
        this.context = context;
        this.tracks = tracks;
        this.parser = new SpotifyAnswerParser();
        this.playlist = new ArrayList<>();
    }

    @Override
    public void run() {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

        try {
            URL playlistUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) playlistUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                playlist = parser.getResponse(response.toString());

            } else {
                Log.e("Spotify", "Błąd podczas pobierania utworów z playlisty. Kod odpowiedzi: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("Spotify", "Wyjątek podczas pobierania utworów z playlisty: " + e.getMessage());
        }
    }

    public List<String> getPlaylist() {
        return playlist;
    }
}
