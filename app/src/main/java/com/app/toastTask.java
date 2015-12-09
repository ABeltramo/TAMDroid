package com.app;

import android.content.Context;
import android.widget.Toast;

import com.tam.PerformerTask;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 08/12/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
class toastTask extends PerformerTask {
    public toastTask(Object obj){
        super(obj);
    }

    public void perform() {
        Object[] array = (Object[]) getObj();
        Context context = (Context) array[0];
        String testo = (String) array[1];
        Toast.makeText(context, testo, Toast.LENGTH_SHORT).show();
    }
}