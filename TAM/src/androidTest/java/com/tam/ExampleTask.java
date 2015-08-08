package com.tam;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 08/08/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
class ExampleTask extends PerformerTask{
    public boolean hasCalled = false;

    void perform() {
        hasCalled = true;
    }
}
