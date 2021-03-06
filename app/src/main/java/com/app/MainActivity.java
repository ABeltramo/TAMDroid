package com.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tam.Engine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Private objects
    JSONObject settings;
    Engine tamEngine;

    // Helper method used to open local config JSON file
    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try{
            settings = new JSONObject(loadJSONFromAsset("sampleConfig.json"));

            HashMap constructors = new HashMap<String,Object>();
            constructors.put("p1",new Object[]{this,R.id.contatore1});
            constructors.put("p2",new Object[]{this,R.id.contatore2});
            constructors.put("p3",new Object[]{getApplicationContext(),"P3: 20 tick"});
            GroundTimerEx ground = new GroundTimerEx(new Handler());
            tamEngine = new Engine(ground,settings,constructors);
        }
        catch(JSONException e){
            Log.d("Exception", "onCreate: "+e.toString());
        }
        catch(Exception e){
            Log.d("Exception", "onCreate: "+e.toString());
        }

        // Start button show in main view
        final Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tamEngine.start();
            }
        });

        final Button btnTick = (Button) findViewById(R.id.btnTick);
        btnTick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tamEngine.enable();
                tamEngine.tick();
                tamEngine.disable();
            }
        });

        final Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tamEngine.stop();
            }
        });
    }
}