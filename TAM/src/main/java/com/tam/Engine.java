package com.tam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public class PerformerTaskNotFound extends RuntimeException{}

    private JSONObject options;
    private Timer groundTimer;

    Engine(Timer realTimer, JSONObject startOptions) throws Exception{
        super(realTimer);
        this.options = startOptions;
        this.disable(); //Engine start stopped
        setup();
    }

    /*
     * SETUP:
     * Read the JSON file and create the istance
     */
    private void setup() throws Exception{
        if (options == null)
            throw new JSONNullObject();
        //Create the ground Timer
        groundTimer = new Timer(getParent(),options.getJSONObject("GroundTimer").getLong("duration"));
        createChild(groundTimer, options.getJSONObject("GroundTimer"));
    }

    /*
     * createChild
     * Recursively read the JSONObject and create instance of the objects
     */
    private void createChild(Timer parent,JSONObject currentTimer){
        try{
            JSONArray child = currentTimer.getJSONArray("child");
            for(int i=0;i<child.length();i++){ //Iterate through child
                JSONObject obj = child.getJSONObject(i);
                if(obj.has("Timer")){   //Create a timer
                    obj = obj.getJSONObject("Timer");
                    Timer t = new Timer(parent,obj.getLong("duration"));
                    createChild(t,obj); //Recursive step
                }
                else if(obj.has("Performer")){ //Create a performer
                    obj = obj.getJSONObject("Performer");
                    PerformerTask t = (PerformerTask) Class.forName(obj.getString("taskClass")).newInstance();
                    Performer p = new Performer(parent,t);
                    //No recursion here.
                }
                else{
                    throw new JSONBadFormed();
                }
            }
        }
        catch (JSONException error){
            throw new JSONBadFormed();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new PerformerTaskNotFound();
        }
    }

    /*
     * Tick
     */
    public void tick(){
        if(isEnable())
            groundTimer.tick(); //tick propagation
    }

     /*
      * basic control methods
      */

    public void start(){ this.enable(); }

    public void stop(){
        this.pause();
        this.reset();
    }

    public void pause(){ this.disable(); }

    public void resume(){
        this.enable();
    }

    public void reset(){
        groundTimer.reset();
    }

    public void setSpeed(long speed){ groundTimer.setDuration(speed); }

    /*
     * Getter & setters
     */
    public Timer getGroundTimer(){return groundTimer;}
}
