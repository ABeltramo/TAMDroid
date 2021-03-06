package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 29/07/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public abstract class PerformerTask {
    Object obj;

    public PerformerTask(Object obj){
        this.obj = obj;
    }

    public Object getObj(){
        return obj;
    }

    public abstract void perform();
}
