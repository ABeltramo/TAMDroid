package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

public class Performer extends TimeSensitiveEntity {
    private PerformerTask task;

    public Performer(Timer parent, PerformerTask task){
        super(parent);
        this.task = task;
    }

    public void tick() {
        if (isEnable()) { //Se il perf è abilitato
            task.perform();
        }
    }

    public PerformerTask getTask(){ return task; }
}