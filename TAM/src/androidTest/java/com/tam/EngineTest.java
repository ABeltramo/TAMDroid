package com.tam;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 07/08/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

public class EngineTest extends InstrumentationTestCase {
    /*
     * setUp():
     * Open file and read JSON
     */
    @SuppressWarnings("all")
    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getInstrumentation().getTargetContext().getResources().getAssets().open(fileName);
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

    JSONObject fileObj;
    JSONObject simpleFile;
    public void setUp() throws Exception {
        fileObj = new JSONObject(loadJSONFromAsset("sampleConfig.json"));
        simpleFile = new JSONObject(loadJSONFromAsset("simpleFile.json"));
        assertNotNull(fileObj);
    }

    public void testEngineCreation(){
        Exception ex = null;
        Engine en;
        try{
            en = new Engine(null,fileObj);
            Engine en2 = new Engine(null,simpleFile);
            //Testing Timers creation
            Timer gt = en.getGroundTimer(); //Getting ground timer
            assertEquals(gt.getChild().size(),2);
        }
        catch (Exception e){
            Log.e("testEngineCreation", e.getMessage());
            ex = e;
        }
        assertNull(ex); //I expect no exception were generated
    }

    @SuppressWarnings("all")
    public void testEngineErrors(){
        // 1° test

        Exception ex = null;
        try{
            Engine en = new Engine(null,new JSONObject(loadJSONFromAsset("errorEmpty.json")));
        }
        catch (JSONException e){
            ex = e;
        }
        catch (Exception e){

        }
        assertNotNull(ex);

        // 2° test

        ex = null;
        try{
            Engine en = new Engine(null,new JSONObject(loadJSONFromAsset("errorBadFormed.json")));
        }
        catch (Engine.JSONBadFormed e){
            ex = e;
        }
        catch (Exception e){

        }
        assertNotNull(ex);

        // 3° test

        ex = null;
        try{
            Engine en = new Engine(null,new JSONObject(loadJSONFromAsset("errorNoTask.json")));
        }
        catch (Engine.PerformerTaskNotFound e){
            ex = e;
        }
        catch (Exception e){
        }
        assertNotNull(ex);

    }

    public void testEngineRunning(){
        Engine en;
        Exception error = null;
        try{
            en = new Engine(null,fileObj);
            //Scanning the tree to find the PerformerTask
            Timer t1 = (Timer) en.getGroundTimer().getChild().get(0);
            Timer t2 = (Timer) en.getGroundTimer().getChild().get(1);
            Performer p1 = (Performer) t1.getChild().get(0);
            Performer p2 = (Performer) t2.getChild().get(0);
            ExampleTask pt1 = (ExampleTask) p1.getTask();
            ExampleTask pt2 = (ExampleTask) p2.getTask();
            //Found it! Now test if the tick make them called
            assertEquals(pt1.hasCalled,false);
            assertEquals(pt2.hasCalled,false);
            en.start(); //Enable the Engine
            en.tick();  //Manual engine trigger
            assertEquals(pt1.hasCalled, true);      // Performer 1 launched
            assertEquals(pt2.hasCalled, false);
            pt1.hasCalled = false; //Reset pt1
            en.tick();
            assertEquals(pt1.hasCalled, true);      // Performer 1 launched
            assertEquals(pt2.hasCalled, false);
            en.tick();
            assertEquals(pt2.hasCalled,true);       // Performer 2 launched
        }
        catch (Exception e){
            error = e;
        }
        assertNull(error);
    }
}
