package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */

public class Performer extends TimeSensitiveEntity {
    private PerformerTask task;
    private Engine eng;

    public Performer(Timer parent, PerformerTask task, Engine eng){
        super(parent);
        this.task = task;
        this.eng = eng;
    }

    public void tick() {
        if (isEnable() && eng != null) {
            eng.setPerfReady(this);
        }
        else if(isEnable()){ // If no engine were passed i'll run the task anyway
            perform();
        }
    }

    public void perform(){
        if (isEnable() && task != null) {
            task.perform();
        }
    }

    public PerformerTask getTask(){ return task; }
}