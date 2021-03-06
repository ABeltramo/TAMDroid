package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

import java.util.ArrayList;

public class Timer extends TimeSensitiveEntity{
    protected long duration;
    protected long curCounter;
    protected ArrayList<TimeSensitiveEntity> child;

    /*
     * CONSTRUCTOR
     */
    public Timer(Timer parent, long duration){
        super(parent);
        curCounter = 0;
        this.duration = duration;
        child = new ArrayList<>();
    }

    /*
     * Tick
     */
    public void tick(){
        if(isEnable()) { //Se il timer è abilitato
            curCounter++;
            if (curCounter >= duration) {
                curCounter = 0;
                for (TimeSensitiveEntity child : this.child) {  // Per ogni timer figlio
                    child.tick();                               // Eseguo il tick
                }
            }
        }
    }

    public void addChild(TimeSensitiveEntity child){
        this.child.add(child);
    }

    public ArrayList<TimeSensitiveEntity> getChild() { return this.child; }

    /*
    * GETTER AND SETTER
    */

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void reset(){
        curCounter = 0;
    }
}
