package com.app;

import android.os.Handler;

import com.tam.Clock;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 09/12/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class GroundTimerEx extends Clock {
    private final int interval = 5000; // 5 Second
    private Handler handler;
    private Runnable runnable = new Runnable(){
        public void run() {
            tick();
            startTimer(); //Restart
        }
    };

    private void startTimer(){
        handler.postDelayed(runnable, interval);
    }

    public GroundTimerEx(Handler handler){
        super(null);
        this.handler = handler;
        startTimer();
    }


}
