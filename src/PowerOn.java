import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/*Purpose: Whenever the power goes out use this class to turn it back on.*/
public class PowerOn implements Fixable {
    //The greenhouse contorls we are using.
    private GreenhouseControls gc;

    /** <b>The instructor that creates everything.</b>
     * @param gc The greenhouse controls we are using
     */
    public PowerOn(GreenhouseControls gc)
    {
        this.gc = gc;
    }

    /**
     * <b>Fixes the power, so turns it on.</b>
     */
    @Override
    public void fix() {
        this.gc.setPowerOn(true);
    }

    /**
     * <b>Logs what we did when we fixed the power.</b>
     */
    @Override
    public void log() {
        BufferedWriter out;
        try{
        out = new BufferedWriter(new FileWriter("fix.log"));
        out.write("Turning the power on at: " + new Date() + "Error Code is now: " + gc.getErrorCode());
        GreenhouseControls.gfcf.setTheTextArea("Turning the power on at: "  + new Date() + " (Error Code: " +gc.getErrorCode() + ")");
        out.close();
        } catch (IOException ioe) {
            System.err.println("Could not create the file.");
        }
    }

    /** <b>Returns the string when we instantiate the class.</b>
     * @return Returns the string when we instantiate a class.
     */
    @Override
    public String toString() {
        return "Turn the Power On";
    }
}