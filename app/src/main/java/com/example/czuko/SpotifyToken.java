package com.example.czuko;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SpotifyToken extends AsyncTask<Void, Void, String> {

    private static final String CLIENT_ID = "179bed59f472450cab98db4de128d8e5";
    private static final String CLIENT_SECRET = "fb74999b316f447485fd21b266bcfa58";
    private static final String TOKEN_ENDPOINT = "https://accounts.spotify.com/api/token";

    private SpotifyTokenListener listener;

    public interface SpotifyTokenListener {
        void onTokenReceived(String token) throws InterruptedException;
        void onTokenError(String error);
    }

    public SpotifyToken(SpotifyTokenListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(TOKEN_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic " + getBase64Auth());

            String postData = "grant_type=client_credentials";
            byte[] postDataBytes = postData.getBytes("UTF-8");
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(postDataBytes);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                return response.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String accessToken = jsonObject.getString("access_token");
                if (listener != null) {
                    listener.onTokenReceived(accessToken);
                }
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onTokenError("Error parsing JSON");
                }
            }
        } else {
            if (listener != null) {
                listener.onTokenError("Error getting token");
            }
        }
    }

    private String getBase64Auth() {
        String clientCredentials = CLIENT_ID + ":" + CLIENT_SECRET;
        return android.util.Base64.encodeToString(clientCredentials.getBytes(), android.util.Base64.NO_WRAP);
    }
}

