/**
 * Description: The event when fans are on.
 */
public class FansOn extends Event {

    /**
     * @param controller The greenhousecontrols we are working on.
     * @param du The amount of time to delay for.
     */
    public FansOn(GreenhouseControls controller, Long du) {
        super(controller,du);

    }

    /**
     * <b>The action for this event which is turn the fans on.</b>
     */
    public void action() {
        this.ctl.setVariable("Fans", true);
        this.ctl.setErrorCode("0");
       if(!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);

        }

        return;
    }

    /** <b>FansOn toString</b>
     * @return Lets us know that the fans are on
     */
    public String toString() {
        return "Fans are On";
    }

}