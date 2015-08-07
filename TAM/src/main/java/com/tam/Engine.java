package com.tam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 01/08/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class Engine extends TimeSensitiveEntity{
    /*
     * Exceptions
     */
    public class JSONNullObject extends RuntimeException{}
    public class JSONBadFormed extends RuntimeException{}

    private JSONObject options;
    private Timer groundTimer;

    Engine(Timer realTimer, JSONObject startOptions){
        super(realTimer);
        this.options = startOptions;
        setup();
    }

    /*
     * SETUP:
     * Read the JSON file and create the istance
     */
    private void setup() {
        if (options == null)
            throw new JSONNullObject();
        //Create the ground Timer
        try{
            Timer gt = new Timer(getParent(),options.getJSONObject("GroundTimer").getLong("duration"));
            createChild(gt,options.getJSONObject("GroundTimer"));
        }
        catch (Exception error){
            throw new JSONBadFormed();
        }
    }

    private void createChild(Timer parent,JSONObject currentTimer){
        try{
            JSONArray child = currentTimer.getJSONArray("child");
            for(int i=0;i<child.length();i++){ //Iterate through child
                JSONObject obj = child.getJSONObject(i);
                if(obj.has("Timer")){   //Create a timer
                    Timer t = new Timer(parent,obj.getLong("duration"));
                    createChild(t,obj); //Recursive step
                }
                else if(obj.has("Performer")){ //Create a performer
                    Performer p = new Performer(parent,null);
                    //No recursion here.
                }
            }
        }
        catch (JSONException error){
            throw new JSONBadFormed();
        }
    }

    /*
     * Tick
     */
    public void tick(){

    }

     /*
     * METODI FONDAMENTALI
     */

    public void start(){
        getParent().enable();
    }

    public void stop(){
        this.pause();
        this.reset();
    }

    public void pause(){
        getParent().disable();
    }

    public void resume(){
        getParent().enable();
    }

    public void reset(){
        getParent().reset();
    }

    public void setSpeed(long speed){
        this.getParent().setDuration(speed);
    }
}
