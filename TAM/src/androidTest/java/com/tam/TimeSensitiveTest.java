package com.tam;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by ABeltramo <beltramo.ale@gmail.com> on 01/08/15.
 * The MIT License (MIT)
 * Copyright (c) 2015 Alessandro Beltramo
 * https://raw.githubusercontent.com/ABeltramo/TAM-Android/master/LICENSE
 */
public class TimeSensitiveTest extends ApplicationTestCase<Application> {
    public TimeSensitiveTest() {super(Application.class);}

    Timer t1;
    Timer t2;
    Timer t3;
    Performer p1;
    Performer p2;
    ExampleTask exampleTaskP1;
    ExampleTask exampleTaskP2;

    //Istantiate a basic TimeEntity hierarchy
    public void setUp() throws Exception {
        //t1
        // -> t2    t3
        //    -> p1   ->p2
        t1 = new Timer(null,1);
        t2 = new Timer(t1,3);
        t3 = new Timer(t1,2);
        exampleTaskP1 = new ExampleTask(null);
        p1 = new Performer(t2, exampleTaskP1,null);
        exampleTaskP2 = new ExampleTask(null);
        p2 = new Performer(t3, exampleTaskP2,null);
    }

    public void testTimeEntityHierarchy(){
        //Testing parent
        assertEquals(p1.getParent(),t2);
        assertEquals(p1.getParent().getParent(), t1);

        //Testing child
        int i = 0;
        for (TimeSensitiveEntity child : t1.getChild()){
            assertEquals(child.getParent(),t1);
            i++;
        }
        assertEquals(i,2); //Test if we have exactly 2 child
    }

    public void testPerformerPerform(){
        assertEquals(exampleTaskP2.hasCalled,false);
        p2.tick();
        assertEquals(exampleTaskP2.hasCalled,true);
    }

    public void testTimerPerf(){
        assertEquals(exampleTaskP2.hasCalled, false);
        assertEquals(exampleTaskP1.hasCalled, false);
        t1.tick();
        assertEquals(exampleTaskP2.hasCalled, false);
        assertEquals(exampleTaskP1.hasCalled, false);
        t1.tick();
        assertEquals(exampleTaskP2.hasCalled, true); //t3.tick() -> p2.tick()
        assertEquals(exampleTaskP1.hasCalled, false);
        t1.tick();
        assertEquals(exampleTaskP1.hasCalled,true); //t2.tick() -> p1.tick()
        //Reset the has called variable
        exampleTaskP2.hasCalled = false;
        exampleTaskP1.hasCalled = false;
        t1.tick();
        assertEquals(exampleTaskP2.hasCalled, true); //t3.tick() -> p2.tick()
        assertEquals(exampleTaskP1.hasCalled, false);
        t1.tick();
        assertEquals(exampleTaskP1.hasCalled, false);
        t1.tick();
        assertEquals(exampleTaskP1.hasCalled, true); //t2.tick() -> p1.tick()
    }
}