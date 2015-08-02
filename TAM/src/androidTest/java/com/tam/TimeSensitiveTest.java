package com.tam;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TimeSensitiveTest extends ApplicationTestCase<Application> {
    public TimeSensitiveTest() {super(Application.class);}


    /*
     * Redefinition of PerformerTask
     * if hasCalled = true -> the Performer has performed
     */
    class PFT extends PerformerTask{
        public boolean hasCalled = false;

        void perform() {
            hasCalled = true;
        }
    }

    Timer t1;
    Timer t2;
    Timer t3;
    Performer p1;
    Performer p2;
    PFT taskP1;
    PFT taskP2;

    //Istantiate a basic TimeEntity hierarchy
    public void setUp() throws Exception {
        //t1
        // -> t2    t3
        //    -> p1   ->p2
        t1 = new Timer(null,1);
        t2 = new Timer(t1,3);
        t3 = new Timer(t1,2);
        taskP1 = new PFT();
        p1 = new Performer(t2,taskP1);
        taskP2 = new PFT();
        p2 = new Performer(t3,taskP2);
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
        assertEquals(taskP2.hasCalled,false);
        p2.tick();
        assertEquals(taskP2.hasCalled,true);
    }

    public void testTimerPerf(){
        assertEquals(taskP2.hasCalled, false);
        assertEquals(taskP1.hasCalled, false);
        t1.tick();
        assertEquals(taskP2.hasCalled, false);
        assertEquals(taskP1.hasCalled, false);
        t1.tick();
        assertEquals(taskP2.hasCalled, true); //t3.tick() -> p2.tick()
        assertEquals(taskP1.hasCalled, false);
        t1.tick();
        assertEquals(taskP1.hasCalled,true); //t2.tick() -> p1.tick()
        //Reset the has called variable
        taskP2.hasCalled = false;
        taskP1.hasCalled = false;
        t1.tick();
        assertEquals(taskP2.hasCalled, true); //t3.tick() -> p2.tick()
        assertEquals(taskP1.hasCalled, false);
        t1.tick();
        assertEquals(taskP1.hasCalled, false);
        t1.tick();
        assertEquals(taskP1.hasCalled, true); //t2.tick() -> p1.tick()
    }
}