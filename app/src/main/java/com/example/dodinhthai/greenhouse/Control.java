package com.example.dodinhthai.greenhouse;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Control {
    private String urls;

    public Control(String str) {
        urls = Uri.parse(str).buildUpon().build().toString();
    }

    public String controlRequest() throws IOException {
        URL url = new URL(urls);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with" + urls);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return new String(out.toByteArray());
        } finally {
            connection.disconnect();
        }
    }
}
