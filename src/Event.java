//: innerclasses/controller/Event.java
// The common methods for any control event.
// From 'Thinking in Java, 4th ed.' (c) Bruce Eckel 2005
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

/***********************************************************************
 * Adapated for COMP308 Java for Programmer, 
 *		SCIS, Athabasca University
 *
 * Assignment: TME3
 * @author: Steve Leung
 * @date  : Oct. 21, 2006
 *
 * Description: Event abstract class
 *
 */

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public abstract class Event implements Runnable, Serializable {
    //How long to delay for
    private Long theDuration;
    //The event time
    private Long eventTime;
    //Starting time.
    private Long startingTime;
    //THe greenhouse controls
    public GreenhouseControls ctl;
    //Whether it has been suspended.
    private boolean suspended;
    private boolean attempted;
    /** <b>The Event constructor</b>
     * @param ctl The greenhouse controls we are working on
     * @param// delayTime The amount of time to delay for.
     */
    public Event(GreenhouseControls ctl, Long theDuration) {
        this.ctl = ctl;
        this.theDuration = theDuration;
        startingTime = System.currentTimeMillis();
        eventTime = System.currentTimeMillis() + theDuration;
    }

    /**
     * <b> What will be executed when a thread starts.</b>
     */
    public void run() {
/*            while (System.currentTimeMillis() < eventTime) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        try {
            synchronized (Event.this) {
                while (suspended) {
                    this.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (Event.this) {
            try {
                this.action();
            } catch (ControllerException e) {
                ctl.shutdown(ctl, e.getMessage());
            }
            try {
                Thread.currentThread().join(); // Wait for the thread to finish.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //GreenhouseControls.gfcf.setTheTextArea("Event Time: " + (eventTime - beginningTime))
        }*/

        while(System.currentTimeMillis() < eventTime || suspended)
        {
            if(suspended)
            {
                try {
                    synchronized (Event.this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            this.action();
        } catch (ControllerException e) {
            ctl.shutdown(ctl, e.getMessage());
        }


    }


    public void setTheDuration(Long theDuration)
    {
        this.theDuration = theDuration;
    }



    /**
     * <b> Suspends one event. </b>
     */
    public void suspend()
    {
        suspended = true;
    }

    public boolean isAttempted()
    {
        return attempted;
    }
    public void setAttempted(boolean attempted)
    {
        this.attempted = attempted;
    }

    /**
     * <b>Resumes what the program was executing</b>
     */

    public synchronized void resume()
    {
        suspended = false;
        startingTime = System.currentTimeMillis();
        eventTime = System.currentTimeMillis() +(theDuration);
        synchronized (Event.this)
        {
            notify();
        }
    }

    /**
     <b>The restart method, which re-sets the eventtime, beginning time and suspended variables.</b>
     */
   public synchronized void restart()
    {
        startingTime = System.currentTimeMillis();
        eventTime = System.currentTimeMillis() + theDuration;
        suspended = false;
    }
    /*<b>The action method for the events.</b>*/
    public abstract void action() throws ControllerException;
} ///:~
