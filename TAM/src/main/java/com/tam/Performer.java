package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class Performer implements TimeSensitiveEntity {
    private PerformerTask task;

    public Performer(PerformerTask task){
        this.task = task;
    }

    public void tick(){
        task.perform();
    }
}