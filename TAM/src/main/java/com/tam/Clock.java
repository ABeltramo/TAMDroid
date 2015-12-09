package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class Clock extends Timer {

    /*
    * Constructor
    */
    public Clock(Timer parent){
        super(parent,1);
    }

    /*
     * Tick
     * in Clock the tick function don't reset the counter
     */
    public void tick(){
        curCounter++;
        for (TimeSensitiveEntity child : this.child) {  // Per ogni timer figlio
            child.tick();                               // Eseguo il tick
        }
    }

    public long getCurTick(){
        return curCounter;
    }
}
