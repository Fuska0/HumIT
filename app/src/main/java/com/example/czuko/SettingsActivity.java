package com.example.czuko;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editText1 = findViewById(R.id.editNumberOfSongs);
        editText2 = findViewById(R.id.editGuessTimeSongs);

        // Pobranie przekazanego obiektu Settings z MainActivity
        Settings settings = getIntent().getParcelableExtra("settings");
        if (settings != null) {
            // Ustawienie wartości pól EditText na podstawie obiektu Settings
            editText1.setText(String.valueOf(settings.getGuessSongTime()));
            editText2.setText(String.valueOf(settings.getNumberOfSongs()));
        }
    }

    // Metoda obsługująca kliknięcie przycisku "Save"
    public void saveSettings(View view) {
        // Pobranie wartości z pól EditText
        int value2 = Integer.parseInt(editText1.getText().toString());
        int value1 = Integer.parseInt(editText2.getText().toString());

        // Utworzenie zaktualizowanego obiektu Settings
        Settings updatedSettings = new Settings(value1, value2);

        // Utworzenie nowego Intentu z zaktualizowanym obiektem Settings
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedSettings", updatedSettings);
        setResult(RESULT_OK, resultIntent);

        // Zakończenie aktywności SettingsActivity
        finish();
    }
}
