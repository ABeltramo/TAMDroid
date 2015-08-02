package com.tam;

import java.util.ArrayList;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

public abstract class TimeSensitiveEntity {
    private boolean isEnabled = true;
    protected TimeSensitiveEntity parent;
    protected ArrayList<TimeSensitiveEntity> child;

    TimeSensitiveEntity(TimeSensitiveEntity parent){
        this.parent = parent;
        if(parent != null)
            parent.addChild(this); //Aggiungo nella lista del padre questo oggetto come figlio
        child = new ArrayList<>();
    }

    abstract void tick();

    private void addChild(TimeSensitiveEntity child){
        this.child.add(child);
    }

    public ArrayList<TimeSensitiveEntity> getChild() { return this.child; }

    public TimeSensitiveEntity getParent(){ return parent; }

    public void enable() {isEnabled = true;}

    public void disable() {isEnabled = false;}

    public boolean isEnable() {return isEnabled;}
}
