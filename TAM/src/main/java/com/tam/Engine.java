package com.tam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

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
    private ArrayList<Performer> readyQueue;
    private Map<String,Object> constructors;

    public Engine(Timer realTimer, JSONObject startOptions, Map<String,Object> constructors) throws Exception{
        super(realTimer);
        this.options = startOptions;
        this.disable(); //Engine start stopped
        this.constructors = constructors;
        readyQueue = new ArrayList<>();
        setup();
    }

    /*
     * SETUP:
     * Read the JSON file and create the istance
     */
    private void setup() throws Exception{
        if (options == null)
            throw new JSONNullObject();
        groundTimer = new Timer(getParent(),options.getJSONObject("GroundTimer").getLong("duration")); //Create the fake ground Timer
        groundTimer.disable();  //Start disabled
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
                    Constructor c = Class.forName(obj.getString("taskClass")).getConstructor(Object.class);
                    PerformerTask t;
                    if(constructors != null)
                        t = (PerformerTask) c.newInstance(constructors.get(obj.getString("taskConstructorKey")));
                    else
                        t = (PerformerTask) c.newInstance(new Object());
                    Performer p = new Performer(parent,t,this);
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
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new PerformerTaskNotFound();
        }
    }

    /*
     * Tick
     */
    public void tick(){
        if(isEnable()) {
            groundTimer.tick();             // tick propagation
            for(Performer perf:readyQueue){ // Simple implementation
                perf.perform();             // Just execute all the performer
            }
            readyQueue.clear();             // Remove all the performer from the readyQueue
        }
    }

    protected void setPerfReady(Performer perf){
        readyQueue.add(perf);               // Add the performer to the ready queue
    }

     /*
      * basic control methods
      */

    public void start(){
        this.enable();
        groundTimer.enable();
    }

    public void stop(){
        this.pause();
        this.reset();
        groundTimer.disable();
    }

    public void pause(){
        this.disable();
        groundTimer.disable();
    }

    public void resume(){
        this.enable();
    }

    public void reset(){groundTimer.reset();}

    public void setSpeed(long speed){ groundTimer.setDuration(speed); }
    /*
     * Getter & setters
     */
    public Timer getGroundTimer(){
        if(parent != null)
            return parent;
        else
            return groundTimer;
    }
}