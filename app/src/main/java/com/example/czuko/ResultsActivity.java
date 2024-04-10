package com.example.czuko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private TextView resultsTextView;
    private TextView resultsTextView2;
    private MediaPlayer mediaPlayerEndSong;
    Playlist playlist;
    Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        mediaPlayerEndSong = MediaPlayer.create(this, R.raw.endsong);
        resultsTextView = findViewById(R.id.resultsTextView);
        resultsTextView2 = findViewById(R.id.textView2);
        Intent intent = getIntent();
        if (intent != null) {
            List<String> selectedSongs = intent.getStringArrayListExtra("selectedSongs");
            List<Integer> answers = intent.getIntegerArrayListExtra("answers");
            settings = intent.getParcelableExtra("settings");
            playlist = intent.getParcelableExtra("playlist");

            if(selectedSongs != null && answers != null)
                displayResults(selectedSongs, answers);

        }
    }

    private void displayResults(List<String> selectedSongs, List<Integer> answers) {
        int sum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < selectedSongs.size(); i++) {
            String result =selectedSongs.get(i) + " | " + (answers.get(i) == 1 ? "Correct" : "Incorrect") + "\n";
            stringBuilder.append(result);
            sum += answers.get(i);
        }
        if(sum >= (int) answers.size()/2){
            resultsTextView2.setText("WINNER");
        }
        else{
            resultsTextView2.setText("LOSER...");
        }
        resultsTextView.setText(stringBuilder.toString());
        mediaPlayerEndSong.start();
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        // Przekazanie obiektu Playlist do MainActivity
        intent.putExtra("playlist", playlist);
        // Przekazanie obiektu Settings do MainActivity
        intent.putExtra("settings", settings);
        startActivity(intent);
    }
}
