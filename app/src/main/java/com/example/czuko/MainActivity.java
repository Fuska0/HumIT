package com.example.czuko;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private Playlist playlist = new Playlist();
    private Settings settings = new Settings(8, 75);
    private static final int SETTINGS_REQUEST_CODE = 1;
    private static final int SELECT_PLAYLIST_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Playlist receivedPlaylist = getIntent().getParcelableExtra("playlist");
        // Pobranie obiektu Settings z Intent
        Settings receivedSettings = getIntent().getParcelableExtra("settings");

        // Sprawdzenie, czy obiekty nie są null
        if (receivedPlaylist != null) {
            // Aktualizacja obiektu Playlist w MainActivity
            playlist = receivedPlaylist;
        }
        if (receivedSettings != null) {
            // Aktualizacja obiektu Settings w MainActivity
            settings = receivedSettings;
        }

    }

    // Metoda obsługująca kliknięcie przycisku startGame
    public void startGame(View view) {
        Intent intent = new Intent(this, StartGameActivity.class);
        // Przekazanie obiektu Playlist do StartGameActivity
        intent.putExtra("playlist", playlist);
        // Przekazanie obiektu Settings do StartGameActivity
        intent.putExtra("settings", settings);
        startActivity(intent);
    }

    // Metoda obsługująca kliknięcie przycisku settings
    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        // Przekazanie obiektu Settings do SettingsActivity
        intent.putExtra("settings", settings);
        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }

    // Metoda wywoływana po powrocie z SettingsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK) {
            // Pobranie zaktualizowanego obiektu Settings z SettingsActivity
            Settings updatedSettings = data.getParcelableExtra("updatedSettings");
            // Aktualizacja obiektu Settings w MainActivity
            settings = updatedSettings;
            Log.d("TAG", String.valueOf(settings.getNumberOfSongs()));
            // Tutaj możesz wykonać odpowiednie działania na podstawie zaktualizowanego obiektu Settings
        } else if (requestCode == SELECT_PLAYLIST_REQUEST_CODE && resultCode == RESULT_OK) {
            // Pobranie zaktualizowanego obiektu Playlist z SelectPlaylist
            Playlist updatedPlaylist = data.getParcelableExtra("updatedPlaylist");
            // Aktualizacja obiektu Playlist w MainActivity
            playlist = updatedPlaylist;
            // Tutaj możesz wykonać odpowiednie działania na podstawie zaktualizowanego obiektu Playlist

        }
    }

    public void selectPlaylist(View view) {
        Intent intent = new Intent(this, SelectPlaylist.class);
        // Przekazanie obiektu Playlist do SelectPlaylist
        intent.putExtra("playlist", playlist);
        startActivityForResult(intent, SELECT_PLAYLIST_REQUEST_CODE);
    }


}