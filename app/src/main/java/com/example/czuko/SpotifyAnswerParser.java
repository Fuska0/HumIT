package com.example.czuko;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpotifyAnswerParser {
    List<String> tracksList = new ArrayList<>();
    public List<String> getResponse(String response) throws JSONException {

        JSONObject responseJson = new JSONObject(response);
        JSONArray items = responseJson.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONObject track = item.getJSONObject("track");
            JSONObject album = track.getJSONObject("album");
            JSONArray artists = album.getJSONArray("artists");

            StringBuilder stringBuilder = new StringBuilder();

            // Pobierz wykonawców
            for (int j = 0; j < artists.length(); j++) {
                JSONObject artist = artists.getJSONObject(j);
                String artistName = artist.getString("name");
                stringBuilder.append(artistName);
                if (j < artists.length() - 1) {
                    stringBuilder.append(", ");
                }
            }

            // Pobierz tytuł utworu
            String trackName = album.getString("name");

            // Dodaj do listy w formacie "Tytuł - Autor"
            String trackInfo = trackName + " - " + stringBuilder.toString();
            tracksList.add(trackInfo);

        }

        return tracksList;
    }
}
