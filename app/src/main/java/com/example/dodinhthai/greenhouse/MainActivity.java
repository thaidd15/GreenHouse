package com.example.dodinhthai.greenhouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Frame> mItems = new ArrayList<>();
    private LinearLayout frame_1_linearlayout;
    private TextView frame_title, frame_id, frame_created_at, frame_status, frame_days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchFrameTask().execute();
        frame_1_linearlayout = (LinearLayout) findViewById(R.id.frame_1_linearlayout);
        frame_1_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FrameDetailActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateUI() { //Update user interface
        Frame fr = mItems.get(0);

        frame_id = (TextView) findViewById(R.id.frame_1_id);
        frame_id.setText("Frame " + String.valueOf(fr.getId()));

        frame_title = (TextView) findViewById(R.id.frame_1_title);
        frame_title.setText(fr.getTitle());

        frame_created_at = (TextView) findViewById(R.id.frame_1_created_at);
        frame_created_at.setText("Created at: " + fr.getCreatedat());

        frame_status = (TextView) findViewById(R.id.frame_1_status);
        if (fr.getStatus() == true) {
            frame_status.setText("Status: Connected");
        } else {
            frame_status.setText("Status: Disconnected");
        }

        frame_days = (TextView) findViewById(R.id.frame_1_days);
        frame_days.setText("Days: " + fr.getDays());
    }

    private class FetchFrameTask extends AsyncTask<Void, Void, List<Frame>> {
        @Override
        protected List<Frame> doInBackground(Void... params) { //This method run in background
            return new FrameFetch().fetchItems(); //Return a List<Frame> which input for method onPostExecute()
        }

        @Override
        protected void onPostExecute(List<Frame> items) {  //This method run in foreground
            mItems = items; //Return a List<Frame> mItems
            updateUI();
        }
    }
}
