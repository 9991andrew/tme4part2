// This produces a specific application of the
// control system, all in a single class. Inner
// classes allow you to encapsulate different
// functionality for each type of event.
// From 'Thinking in Java, 4th ed.' (c) Bruce Eckel 2005
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

/***********************************************************************
 * Adapated for COMP308 Java for Programmer,
 *		SCIS, Athabasca University
 *
 * Assignment: TME3
 * @author: Steve Leung
 * @date  : Oct 21, 2005
 *
 */

/*
 *
 * Name: Andrew Whenham
 * Student ID: 3469950
 * Date: February 5 2022
 *
 *
 * Problem Description: Create a Greenhousecontrols simulation. Using OOP, regex, serialization, file loading in and writing to.
 *
 *
 *
 * Compiling & Running Instructions:
 * (Should be able to run & compile with JDK 16)
 * Run the Java Compiler through Command Prompt.
 * Locate where the file that the program is located in.
 * First Compile javac GreenhouseControls.java
 */


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;



public class GreenhouseControls extends Controller implements Serializable {
    //The window we are working on.
    public static GreenhouseControlsForm gfcf;
    //The list of states.
    private ArrayList<TwoTuple<String, Object>> states = new ArrayList<>();


    /**
     * <b>The constructor for greenhouse controls. - Sets up the window. </b>
     */
    public GreenhouseControls()
    {
        gfcf = new GreenhouseControlsForm(this,"Green house Controls");

        this.setPowerOn(false);
        this.setLights(false);
        this.setWater(false);
        this.setFans(false);
        this.setThermostat("Day");
        this.setEventsFile("");
        this.setWindowOk(true);
        this.setErrorCode("0");

        this.getFans();
        Integer.parseInt(this.getErrorCode());
    }

    /** <b>Sets the variable.</b>
     * @param k String variable we want to set.
     * @param v Object variable we want to set.
     */
    public void setVariable(String k, Object v) {
        for(int i = 0; i < this.states.size(); i++)
        {
            TwoTuple<String, Object> objectTwoTuple = this.states.get(i);
            if(objectTwoTuple.first.equals(k))
            {
                this.states.set(i,new TwoTuple<>(k,v));
                return;
            }
        }
        states.add(new TwoTuple<>(k,v));
    }

    /** <b>Get the variable from states.</b>
     * @param k The string we use to get the variable out of the states list.
     * @return returns the object corresponding to the variable k
     */
    public Object getVariable(String k)
    {
        for(TwoTuple<String, Object> s : this.states)
        {
            if(s.first.equals(k))
            {
                return s.second;
            }
        }
        return null;
    }

    /** <b>Shutdown the system.</b>j
     * @param gc Green house controls instance we are working on
     * @param message The message when we are shutting down.
     */
    @Override
    public void shutdown(GreenhouseControls gc, String message) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("error.log"));
            out.write(message + " on " + new Date() + " (Error Code: " + gc.getErrorCode() + ")");
            out.close();
            gfcf.setTheTextArea(message + " on " + new Date() + " (Error Code: " + gc.getErrorCode() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ObjectOutputStream dumpOut = new ObjectOutputStream(new FileOutputStream("dump.out"));
            dumpOut.writeObject(GreenhouseControls.this);
            dumpOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        gfcf.shutdownFully(Integer.parseInt(this.getErrorCode()));
        suspendAllThreads();
    }
    /** <b>This method suspends all the threads (or events).</b>
     *
     */
    public void suspendAllThreads() {
        for(Event e : this.getEventList())
        {
            e.suspend();
        }
    }

    /** <b>This method restarts all the threads (or events).</b>
     * @param gc The greenhouse controls we are working on
     */
    public void restartAllThreads(GreenhouseControls gc) {
        for (int i = 0; i < gc.getEventList().size(); i++) {
            Event e = gc.getEventList().get(i);
            if (!e.isAttempted())
            {
                e.restart();
                Thread t = new Thread(e);
                t.start();
            }
        }
    }

    /** <b>Terminates the events.</b>
     * @param d Amount of tiem to terminate for
     */
    public void terminateAll(long d) {
        for(Event e : this.getEventList())
        {
            e.setAttempted(false);
        }
        Thread t = new Thread(new Terminate(this, d));
        t.start();
    }

    /**
     * <b>Resumes all the events.</b>
     */
    public void resumeAll()
    {
       for(Event e : this.getEventList())
           e.resume();
    }

    /** <b>Restores the events.</b>
     * @param dump The string usually dump.out.
     */
    public void restore(String dump) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dump));
            try {
                GreenhouseControls theDump = (GreenhouseControls) inputStream.readObject();
                theDump.fixEvents(theDump, Integer.parseInt(theDump.getErrorCode()));
                inputStream.close();


               // theDump.restartAllThreads();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            gfcf.setTheTextArea("This file is not compatible");
        }
    }

    /** <b>Fixes the events. (Windowmalfunction or PowerOff)</b>
     * @param gc The green house controls instance we are using
     * @param error The error code
     */
    public void fixEvents(GreenhouseControls gc, int error)
    {
        int theError = error;
        if(theError == 1)
        {
            System.out.println(error);
            Fixable fixable = gc.getFixable(error);
            fixable.fix();
            System.out.println(error);
            fixable.log();
            gc.setErrorCode("0");
            theError =  Integer.parseInt(gc.getErrorCode());
            gfcf.setTheTextArea("Error Code is now: " + theError);
        }

        if(theError == 2)
        {
            Fixable fixable = gc.getFixable(error);
            fixable.fix();
            theError =  Integer.parseInt(gc.getErrorCode());
            fixable.log();
            gc.setErrorCode("0");
            gfcf.setTheTextArea("Error Code is now: " + getErrorCode());
            if(theError == 0)
              restartAllThreads(gc);
        }
        if(theError == 0)
            restartAllThreads(gc);
    }

    /** <b>Gets a fixable interface corresponding to the individual error numbers</b>
     * @param error The error number 1 windowmalfunction 2 poweroff.
     * @return returns the Fixable interface for the particular errorn umber.
     */
    Fixable getFixable(int error) {
        Fixable fixable = null;

        if(error == 1)
        {
            fixable = new FixWindow(this);
            return fixable;
        }
        if(error == 2)
        {
            fixable = new PowerOn(this);
            return fixable;
        }

        return null;
    }

    /** <b>The main method.</b>
     * @param args Used for when we are running in console.
     * @throws Exception The exception it may throw
     */
    public static void main(String[] args) throws Exception {
        try {
           GreenhouseControls ghc = new GreenhouseControls();
           gfcf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }catch(Throwable e)
        {
            e.printStackTrace();
        }
    }
    //All of this is miscellaneous getters and setters.
    public void setLights(Boolean state) { this.setVariable("Lights", state.toString());}
    public Boolean getLights() { return Boolean.parseBoolean((String)this.getVariable("Lights")); }

    public void setWater(Boolean state) { this.setVariable("Water", state.toString()); }
    public Boolean getWater() { return Boolean.parseBoolean((String)this.getVariable("Water")); }

    public void setFans(Boolean state) {  this.setVariable("Fans", state.toString());  }
    public Boolean getFans() { return Boolean.parseBoolean((String)this.getVariable("Fans")); }

    public void setThermostat(String state) {  this.setVariable("Thermostat", state); }
    public String getThermostat() { return (String)this.getVariable("Thermostat"); }

    public void setEventsFile(String filename) {  this.setVariable("Events File", filename); }
    public String getEventsFile() { return (String)this.getVariable("Events File"); }

    public void setWindowOk(Boolean state) { this.setVariable("Window Ok", state.toString());  }
    public Boolean getWindowOk() { return Boolean.parseBoolean((String)this.getVariable("Window Ok")); }

    public void setPowerOn(Boolean state) {  this.setVariable("Power On", state.toString()); }
    public Boolean getPowerOn() { return Boolean.parseBoolean((String)this.getVariable("Power On")); }

    public void setErrorCode(String code) { this.setVariable("Error Code", "" + code); }
    public String getErrorCode()
    {

        return (String) this.getVariable("Error Code");
    }
}

///:~
