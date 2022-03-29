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
    //THe greenhouse controls
    public GreenhouseControls ctl;
    //Whether it has been suspended.
    private boolean suspended;

    /** <b>The Event constructor</b>
     * @param ctl The greenhouse controls we are working on
     * @param// delayTime The amount of time to delay for.
     */
    public Event(GreenhouseControls ctl, Long theDuration) {
        this.ctl = ctl;
        this.theDuration = theDuration;
    }

    /**
     * <b> What will be executed when a thread starts.</b>
     */
    public void run() {
        try {
                Thread.sleep(theDuration );
                this.action();
                //  System.out.println("Turning off: " );

        }catch(Exception e)
        {
            e.printStackTrace();
        }
     //GreenhouseControls.gfcf.setTheTextArea("Event Time: " + (eventTime - beginningTime))
    }


    public void setTheDuration(Long theDuration)
    {
        this.theDuration = theDuration;
    }



    /**
     * <b> Suspends one event. </b>
     */


    /**
     * <b>Resumes what the program was executing</b>
     */


    /**
     * <b>The restart method, which re-sets the eventtime, beginning time and suspended variables.</b>
     */
   // public synchronized void restart()
    //{
      //  currentTime = System.currentTimeMillis();
       // eventTime = System.currentTimeMillis() + delayTime;
        //suspended = false;
    //}
    /*<b>The action method for the events.</b>*/
    public abstract void action() throws ControllerException;
} ///:~
