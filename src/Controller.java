//: innerclasses/controller/Controller.java
// The reusable framework for control systems.
// From 'Thinking in Java, 4th ed.' (c) Bruce Eckel 2005
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

/***********************************************************************
 * Adapated for COMP308 Java for Programmer, 
 *		SCIS, Athabasca University
 *
 * Assignment: TME3
 * @author: Steve Leung
 * @date  : Oct 21, 2006
 *
 */

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class Controller implements Serializable {
    // A class from java.util to hold Event objects:
    private ArrayList<Event> eventList = new ArrayList<>();
    private int threadCount;

    protected boolean suspend;
    protected boolean running;


    public Controller()
        {
            this.running = true;
        }




    /** <b>Adds event to list and starts the thread. And then adds that thread to the list of threads.</b>
     * @param e Event to add.
     */
    public void addEvent(Event e)
    {
        eventList.add(e);
        Thread thread = new Thread(e);
        thread.start();
    }

    /** <b>Adds an event to the list of events.</b>
     * @param name Name of the class.
     * @param controller Greenhouse controls we are working on.
     * @param time The amount of time to delay for
     */
    public void addEvent(String name, Controller controller, Long time)
    {
        try {
            Class<?> className = Class.forName(name);
            Constructor<?> constructor = className.getDeclaredConstructor(GreenhouseControls.class,Long.class);
            Object object = constructor.newInstance(controller,time);
            Event e = (Event) object;
            e.setTheDuration(time);
            addEvent(e);
           // scheduleEvent(name, time);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /** <b>Adds ring event.</b>
     * @param name The name of the class.
     * @param time The amount of time to delay for
     * @param controller The greenhouse controls we are working on
     * @param ring The amount of times to ring.
     */
    public void addEvent(String name, Long time, Controller controller, Integer ring) {
        try {
            Class<?> className = Class.forName(name);
            Constructor<?> constructor = className.getDeclaredConstructor(GreenhouseControls.class, Long.class, Integer.class);
            Object object = constructor.newInstance(controller, time, ring);
            Event e = (Event) object;
            addEvent(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


        public boolean isRunning()
        {
            synchronized (this)
            {
                notifyAll();
            }
            return running;
        }
    public void setRunning(boolean running)
    {
        synchronized (this)
        {
            this.running = running;
            notifyAll();
        }
    }
    public void suspend(){
        synchronized (this) {
            suspend = true;
        }
    }
    //Overloaded function
    protected void shutdown(GreenhouseControls gc, String message) {

        System.out.println("Error occurred and we are shutting down now.");
    }
    //Getter for the events list.
    public ArrayList<Event> getEventList() {
        return eventList;
    }

}


///:~
