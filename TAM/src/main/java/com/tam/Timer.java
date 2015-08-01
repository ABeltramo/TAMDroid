package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

import java.util.ArrayList;

public class Timer implements TimeSensitiveEntity{
    private long duration;
    protected long curCounter;
    protected TimeSensitiveEntity parentTimer;
    private ArrayList<TimeSensitiveEntity> childTimer;

    /*
     * CONSTRUCTOR
     */
    public Timer(TimeSensitiveEntity parent, long duration){
        curCounter = 0;
        this.duration = duration;
        childTimer = new ArrayList<>();
        parent.addChild(this); //Aggiungo nella lista del padre questo oggetto come figlio
    }

    /*
     * Tick
     */
    public void tick(){
        curCounter++;
        if(curCounter >= duration){
            curCounter = 0;
            for(TimeSensitiveEntity child:childTimer){  // Per ogni timer figlio
                child.tick();                           // Eseguo il tick
            }
        }
    }

    /*
    * GETTER AND SETTER
    */
    public TimeSensitiveEntity getParent(){ return parentTimer; }

    public void addChild(TimeSensitiveEntity child) { this.childTimer.add(child); }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
