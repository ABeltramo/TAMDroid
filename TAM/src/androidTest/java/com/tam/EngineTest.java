package com.tam;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

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
     * Redefinition of PerformerTask
     * if hasCalled = true -> the Performer has performed
     */
    class task extends PerformerTask{
        public boolean hasCalled = false;

        void perform() {
            hasCalled = true;
        }
    }

    /*
     * setUp():
     * Open file and read JSON
     */
    @SuppressWarnings("unchecked")
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
        try{
            Engine en = new Engine(null,fileObj);
            Engine en2 = new Engine(null,simpleFile);
        }
        catch (Exception e){
            ex = e;
        }
        assertNull(ex); //I expect no exception were generated
    }
}
