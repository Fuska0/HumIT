package com.example.czuko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartGameActivity extends AppCompatActivity {

    private List<String> texts = new ArrayList<>();
    private TextView textView;
    private TextView timerTextView;
    private int currentIndex = 0;
    private int maxIndex;
    private Random random = new Random();
    private List<String> playlistCopy;
    private Settings settings;
    private MediaPlayer mediaPlayerCorrect;
    private MediaPlayer mediaPlayerIncorrect;
    private MediaPlayer mediaPlayerReady;

    private CountDownTimer countDownTimer;
    private List<String> selectedSongs = new ArrayList<>();
    private List<Integer> answers = new ArrayList<>();
    Playlist playlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // Initialize TextViews
        textView = findViewById(R.id.titleId);
        timerTextView = findViewById(R.id.editTextTime);

        // Initialize MediaPlayer
        mediaPlayerCorrect = MediaPlayer.create(this, R.raw.correct_sound);
        mediaPlayerIncorrect = MediaPlayer.create(this, R.raw.incorrect_song);
        mediaPlayerReady = MediaPlayer.create(this, R.raw.ready_go_sound);

        // Get Playlist and Settings from MainActivity
        Intent intent = getIntent();
        if (intent != null) {
            playlist = intent.getParcelableExtra("playlist");
            if (playlist != null) {
                playlistCopy = new ArrayList<>(playlist.getSongList());
                settings = intent.getParcelableExtra("settings");
                maxIndex = settings.getNumberOfSongs();

                mediaPlayerReady.start();
                textView.setText("Get Ready...");

                // Wyłącz możliwość dotykania ekranu na 2 sekundy
                disableTouchForDelay(2000); // 2000 milisekund (2 sekundy)
            }
        }
    }

    private void disableTouchForDelay(long delayMillis) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                startGame();

            }
        }, delayMillis);
    }

    private void startGame() {
        if (currentIndex < maxIndex) {
            displayNextSong();
            countDownTimer = new CountDownTimer(settings.getGuessSongTime() * 1000L, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) millisUntilFinished / 1000;
                    timerTextView.setText("Czas: " + secondsRemaining);
                }

                @Override
                public void onFinish() {
                    answers.add(0);
                    countDownTimer.cancel();
                    startGame();
                }
            }.start();
        }
        else {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putStringArrayListExtra("selectedSongs", (ArrayList<String>) selectedSongs);
            intent.putIntegerArrayListExtra("answers", (ArrayList<Integer>) answers);
            intent.putExtra("settings", settings);
            intent.putExtra("playlist", playlist);
            startActivity(intent);
        }
    }

    private void displayNextSong() {
        int maxSongID = playlistCopy.size();
        int songId = random.nextInt(maxSongID);
        String nextSong = playlistCopy.get(songId);
        selectedSongs.add(nextSong);
        playlistCopy.remove(songId);
        currentIndex++;
        textView.setText(nextSong);
    }

    // Handle "Correct" button click
    public void correctClicked(View view) {
        answers.add(1);
        mediaPlayerCorrect.start();
        countDownTimer.cancel();
        startGame();
    }

    // Handle "Incorrect" button click
    public void incorrectClicked(View view) {
        answers.add(0);
        mediaPlayerIncorrect.start();
        countDownTimer.cancel();
        startGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (mediaPlayerCorrect != null) {
            mediaPlayerCorrect.release();
            mediaPlayerCorrect = null;
        }
    }
}
