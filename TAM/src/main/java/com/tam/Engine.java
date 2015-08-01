package com.tam;

import java.util.ArrayList;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 01/08/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class Engine {
    private ArrayList<TimeSensitiveEntity> timeEntities;
    private ArrayList<Performer> performers;
    private TimeSensitiveEntity groundClock;

    Engine(TimeSensitiveEntity groundClock){
        this.groundClock = groundClock;
    }

    /*
     * METODI FONDAMENTALI
     */

    public void start(){

    }

    public void stop(){}

    public void pause(){}

    public void resume(){}

    public void reset(){}

    public void slowDown(long time){}

    public void speedUp(long time){}

    public void addTimeEntity(TimeSensitiveEntity timeEntities) {
        this.timeEntities.add(timeEntities);
    }
}
