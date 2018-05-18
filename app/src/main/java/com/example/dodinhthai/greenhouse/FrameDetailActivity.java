package com.example.dodinhthai.greenhouse;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrameDetailActivity extends AppCompatActivity {
    private List<Environment> mItems = new ArrayList<>();
    private TextView temperature, humidity, ph;
    private Switch led_switch, fan_switch, pumpa_switch, pumpb_switch, pumppu_switch, pumppd_switch;
    private String urls_device = "http://192.168.0.103:8080/GHServer/manager/frame/1/control/";
    private String urls_auto_mode = "http://192.168.0.103:8080/GHServer/manager/frame/1/control/mode?mode=off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_detail);
        new collectTask().execute(); //Collect data
        new controlTask().execute(urls_auto_mode);  //Turn off auto mode
        led_switch = (Switch) findViewById(R.id.led_switch);
        led_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    new controlTask().execute(urls_device + "led?led=on");
                    Toast.makeText(FrameDetailActivity.this, "LED ON", Toast.LENGTH_SHORT).show();
                } else {
                    new controlTask().execute(urls_device + "led?led=off");
                    Toast.makeText(FrameDetailActivity.this, "LED OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fan_switch = (Switch) findViewById(R.id.fan_switch);
        fan_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    new controlTask().execute(urls_device + "fan?fan=on");
                    Toast.makeText(FrameDetailActivity.this, "FAN ON", Toast.LENGTH_SHORT).show();
                } else {
                    new controlTask().execute(urls_device + "fan?fan=off");
                    Toast.makeText(FrameDetailActivity.this, "FAN OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pumpa_switch = (Switch) findViewById(R.id.pump_a_switch);
        pumpa_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    new controlTask().execute(urls_device + "pumpa?pumpa=on");
                    Toast.makeText(FrameDetailActivity.this, "PUMP A LIQUID ON", Toast.LENGTH_SHORT).show();
                } else {
                    new controlTask().execute(urls_device + "pumpa?pumpa=off");
                    Toast.makeText(FrameDetailActivity.this, "PUMP A LIQUID OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pumpb_switch = (Switch) findViewById(R.id.pump_b_switch);
        pumpb_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    new controlTask().execute(urls_device + "pumpb?pumpb=on");
                    Toast.makeText(FrameDetailActivity.this, "PUMP B LIQUID ON", Toast.LENGTH_SHORT).show();
                } else {
                    new controlTask().execute(urls_device + "pumpb?pumpb=off");
                    Toast.makeText(FrameDetailActivity.this, "PUMP B LIQUID OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pumppu_switch = (Switch) findViewById(R.id.pump_up_switch);
        pumppu_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    new controlTask().execute(urls_device + "pumppu?pumppu=on");
                    Toast.makeText(FrameDetailActivity.this, "PUMP PH UP ON", Toast.LENGTH_SHORT).show();
                } else {
                    new controlTask().execute(urls_device + "pumppu?pumppu=off");
                    Toast.makeText(FrameDetailActivity.this, "PUMP PH UP OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pumppd_switch = (Switch) findViewById(R.id.pump_down_switch);
        pumppd_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    new controlTask().execute(urls_device + "pumppd?pumppd=on");
                    Toast.makeText(FrameDetailActivity.this, "PUMP PH DOWN ON", Toast.LENGTH_SHORT).show();
                } else {
                    new controlTask().execute(urls_device + "pumppd?pumppd=off");
                    Toast.makeText(FrameDetailActivity.this, "PUMP PH DOWN OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI() {
        Environment ev = mItems.get(0);

        temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(ev.getTemperature() + "\u00b0" + "C");

        humidity = (TextView) findViewById(R.id.humidity);
        humidity.setText(ev.getHumidity() + "%");

        ph = (TextView) findViewById(R.id.ph);
        ph.setText(ev.getPH());
    }

    private class collectTask extends AsyncTask<Void, Void, List<Environment>> {
        @Override
        protected List<Environment> doInBackground(Void... params) {
            return new Collect().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Environment> items) {
            mItems = items;
            updateUI();
        }
    }

    private class controlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return new Control(params[0]).controlRequest();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }
        }
    }
}