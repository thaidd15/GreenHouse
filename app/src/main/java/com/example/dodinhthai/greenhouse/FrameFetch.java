package com.example.dodinhthai.greenhouse;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FrameFetch {
    private static final String TAG = "FrameFetch";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with" + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Frame> fetchItems() {
        List<Frame> items = new ArrayList<>();
        try {
            //String url = Uri.parse("http://192.168.0.103:8080/GHServer/manager/frame/1?row=5").buildUpon().build().toString();
            String url = Uri.parse("http://192.168.43.170:8080/GHServer/manager/frame/1?row=5").buildUpon().build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString); //Create JSON Body
            parseItems(items, jsonBody);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return items;
    }

    private void parseItems(List<Frame> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONArray lisJsonArray = jsonBody.getJSONArray("lis"); //Get lisJsonArray from jsonBody
        JSONObject frameJsonObject = lisJsonArray.getJSONObject(0); //Get frameJsonObject from lisJsonArray
        Frame fr = new Frame();
        fr.setId(frameJsonObject.getInt("id"));
        fr.setDays(frameJsonObject.getString("days"));
        fr.setCreatedat(frameJsonObject.getString("time_begin"));
        JSONObject plantJsonObject = frameJsonObject.getJSONObject("plant");
        fr.setTitle(plantJsonObject.getString("plant_name"));
        JSONObject availableframeJsonObject = frameJsonObject.getJSONObject("available_frame");
        fr.setStatus(availableframeJsonObject.getBoolean("status"));
        items.add(fr);
    }
}