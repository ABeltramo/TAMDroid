package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class Performer implements TimeSensitiveEntity {
    private PerformerTask task;
    protected TimeSensitiveEntity parent;

    public Performer(TimeSensitiveEntity parent, PerformerTask task){
        this.task = task;
        this.parent = parent;
        parent.addChild(this); //Aggiungo nella lista del padre questo oggetto come figlio
    }

    public void tick(){ task.perform(); }

    public TimeSensitiveEntity getParent(){ return parent; }

    public void addChild(TimeSensitiveEntity child){}
}