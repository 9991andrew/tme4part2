import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


/*Purpose: If the window from (WindowMalfunction) needs fixing then we use this to fix it.*/
public class FixWindow implements Fixable {
    //The greenhouse controls we are working on
    private GreenhouseControls greenhouseControls;
    /**
     * @param greenhouseControls The greenhousecontrols we are working on.
     */
    public FixWindow(GreenhouseControls greenhouseControls)
    {
        this.greenhouseControls = greenhouseControls;
    }

    /**
     * <b>Fixes the window</b>
     */
    @Override
    public void fix() {
        greenhouseControls.setWindowOk(true);
    }

    /**
     * <b>Log that we have fixed the window!</b>
     */
    @Override
    public void log() {
            BufferedWriter out;
            try {
                out = new BufferedWriter(new FileWriter("fix.log"));
                out.write("Fixed the window at: " + new Date() + "Error Code is now: " + greenhouseControls.getErrorCode());
                GreenhouseControls.gfcf.setTheTextArea("Fixing the Window" + " on " + new Date() );
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
    }

    /** <b>FixWindow toString</b>
     * @return Lets us know that we have fixed the window!
     */
    @Override
    public String toString() {
        return "Fix Window";
    }
}
