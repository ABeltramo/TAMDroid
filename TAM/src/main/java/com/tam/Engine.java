package com.tam;

import java.util.ArrayList;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 01/08/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class Engine {
    private Timer groundTimer;

    Engine(Timer groundTimer){
        this.groundTimer = groundTimer;
    }

    /*
     * METODI FONDAMENTALI
     */

    public void start(){
        groundTimer.enable();
    }

    public void stop(){
        this.pause();
        this.reset();
    }

    public void pause(){
        groundTimer.disable();
    }

    public void resume(){
        groundTimer.enable();
    }

    public void reset(){
        groundTimer.reset();
    }

    public void setSpeed(long speed){
        this.groundTimer.setDuration(speed);
    }
}
