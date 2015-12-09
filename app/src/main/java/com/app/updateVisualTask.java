package com.app;

import android.widget.TextView;
import com.tam.PerformerTask;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 08/12/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
class updateVisualTask extends PerformerTask {
    public updateVisualTask(Object obj){
        super(obj);
    }

    public void perform() {
        Object[] array = (Object[]) getObj();
        MainActivity activity = (MainActivity) array[0];
        int id = (int) array[1];
        TextView text = (TextView) activity.findViewById(id);
        int valore = Integer.parseInt(text.getText().toString());
        text.setText(Integer.toString(valore + 1));
    }
}