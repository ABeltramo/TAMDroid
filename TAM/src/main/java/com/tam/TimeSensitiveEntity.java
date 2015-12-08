package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

public abstract class TimeSensitiveEntity {
    private boolean isEnabled = true;
    protected Timer parent;


    TimeSensitiveEntity(Timer parent){
        this.parent = parent;
        if(parent != null)
            parent.addChild(this); //Aggiungo nella lista del padre questo oggetto come figlio
    }

    abstract void tick();

    public Timer getParent(){ return parent; }

    public void enable() {isEnabled = true;}

    public void disable() {isEnabled = false;}

    public boolean isEnable() {return isEnabled;}
}
