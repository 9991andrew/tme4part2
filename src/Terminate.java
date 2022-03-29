/**
 * Description: The event when we want to terminate the program.
 */
public class Terminate extends Event {
    /** <b>The terminate constructor</b>
     * @param controller The greenhouse controls instance we are using
     * @param delayTime The amount of delay we want.
     */
    public Terminate(GreenhouseControls controller, Long du) {
        super(controller, du);
    }

    /**
     * <b>The action for terminate event</b>
     */
    public void action() {
        GreenhouseControls.gfcf.shutdownFully(Integer.parseInt(ctl.getErrorCode()));
        GreenhouseControls.gfcf.setTheTextArea(toString());
    }

    /** <b>The string for this class.</b>
     * @return The string for this class.
     */
    public String toString() {
            return "Terminating";
        }

}