package com.example.czuko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SelectPlaylist extends AppCompatActivity {

    private Playlist playlist;
    private EditText linkURL;
    private static final int PICK_CSV_FILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_playlist);
        linkURL = findViewById(R.id.SpotifyLink);
        playlist = new Playlist();
    }

    public void getLink(View view) {



        SpotifyToken spotifyToken = new SpotifyToken(new SpotifyToken.SpotifyTokenListener() {
            @Override
            public void onTokenReceived(String token) throws InterruptedException {
                String playlistId = getPlaylistId(String.valueOf(linkURL.getText()));
                Log.d("Spotify" , "toke connected ");
                PlaylistTracks playlistTracks = new
                        PlaylistTracks(token, playlistId, getApplicationContext(), "");
                Thread thread = new Thread(playlistTracks);
                thread.start();
                thread.join();
                playlist.setSongList(playlistTracks.getPlaylist());
                if(playlist.getSongList().size() > 0){
                    Toast.makeText(getApplicationContext(), "Pobrano utwory z playlisty!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTokenError(String error) {
                // Tutaj obsługa błędu podczas pobierania tokena
                Log.d("Spotify" , "porażka");

            }
        });
        spotifyToken.execute();
    }

    // Metoda obsługująca kliknięcie przycisku "SAVE"
    public void saveChanges(View view) {
        // Przeniesienie użytkownika do MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        // Przekazanie zmodyfikowanego obiektu Playlist do MainActivity
        intent.putExtra("updatedPlaylist", playlist);
        setResult(RESULT_OK, intent);
        // Zakończenie aktywności SelectPlaylist
        finish();
    }

    // Metoda wywoływana po zakończeniu działania SelectPlaylist
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Wywołanie metody z klasy bazowej
        // Przywróć zmodyfikowany obiekt playlist z powrotem do MainActivity
        Intent intent = new Intent();
        intent.putExtra("updatedPlaylist", playlist);
        setResult(RESULT_OK, intent);
        finish();
    }


    public static String getPlaylistId(String playlistLink) {

        String playlistId = "";
        if (playlistLink.contains("open.spotify.com/playlist/")) {
            String[] parts = playlistLink.split("/");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("playlist")) {
                    if (i + 1 < parts.length) {
                        playlistId = parts[i + 1];
                        if (playlistId.contains("?")) {
                            playlistId = playlistId.substring(0, playlistId.indexOf("?"));
                        }
                        break;
                    }
                }
            }
        }

        return playlistId;
    }

}


