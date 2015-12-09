package com.tam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

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

    private JSONObject options;                                 // Startup JSON object with Objects definitions
    private Timer groundTimer;
    private ArrayList<Performer> readyQueue;                    // Queue of performers ready to be executed
    private HashMap<String,Object> constructors;                    // Map that contain objects to be passed to Performer
    private HashMap<String,TimeSensitiveEntity> timeEntities;   // Map of currently allocated Time entities

    /*
     * Constructor:
     * Create the Engine class.
     */
    public Engine(Timer realTimer, JSONObject startOptions, HashMap<String,Object> constructors) throws Exception{
        super(realTimer);
        this.disable();  // Engine start disabled this prevent early tick() from real timer
        this.options = startOptions;
        this.constructors = constructors;
        readyQueue = new ArrayList<>();
        timeEntities = new HashMap<String,TimeSensitiveEntity>();
        setup();        // Read the JSON options
    }

    /*
     * SETUP:
     * Read the JSON file and create the istance
     */
    private void setup() throws Exception{
        if (options == null)
            throw new JSONNullObject();
        groundTimer = new Timer(null,options.getJSONObject("GroundTimer").getLong("duration")); //Create the fake ground Timer
        createChild(groundTimer, options.getJSONObject("GroundTimer"));
    }

    /*
     * createChild
     * Recursively read the JSONObject and create instance of the objects
     */
    private void createChild(Timer parent,JSONObject currentTimer){
        try{
            JSONArray child = currentTimer.getJSONArray("child");
            for(int i=0;i<child.length();i++){          //Iterate through child
                JSONObject obj = child.getJSONObject(i);
                if(obj.has("Timer")){                   //Create a timer
                    obj = obj.getJSONObject("Timer");
                    Timer t = new Timer(parent,obj.getLong("duration"));
                    if(obj.has("ID"))
                        timeEntities.put(obj.getString("ID"),t);
                    createChild(t,obj);                 //Recursive step
                }
                else if(obj.has("Clock")){              //Create a clock
                    obj = obj.getJSONObject("Clock");
                    Clock c = new Clock(parent);
                    if(obj.has("ID"))
                        timeEntities.put(obj.getString("ID"),c);
                    createChild(c,obj);                 //Recursive step
                }
                else if(obj.has("Performer")){          //Create a performer
                    obj = obj.getJSONObject("Performer");
                    Constructor c = Class.forName(obj.getString("taskClass")).getConstructor(Object.class);
                    PerformerTask t;
                    if(constructors != null)
                        t = (PerformerTask) c.newInstance(constructors.get(obj.getString("taskConstructorKey")));
                    else
                        t = (PerformerTask) c.newInstance(new Object());
                    Performer p = new Performer(parent,t,this);
                    if(obj.has("ID"))
                        timeEntities.put(obj.getString("ID"),p);
                    //No recursion here. Performer's can't have childs
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
                perf.perform();             // Just execute all the performer ready
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
    }

    public void stop(){
        this.pause();
        this.reset();
    }

    public void pause(){
        this.disable();
    }

    public void resume(){
        this.enable();
    }

    public void reset(){
        groundTimer.reset();
    }

    public void setSpeed(long speed){
        groundTimer.setDuration(speed);
    }

    /*
     * Getter & setters
     */
    public Timer getGroundTimer(){
        if(parent != null)
            return parent;
        else
            return groundTimer;
    }

    public TimeSensitiveEntity getEntityById(String ID){
        return timeEntities.get(ID);
    }
}