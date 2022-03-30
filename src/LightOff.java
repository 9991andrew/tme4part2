/**
 * Description: Light Off Event class.
 */
public class LightOff extends Event {

    /**
     * @param controller The greenhouse controls we are working on
     * @param du The amount of time to delay for
     */
    public LightOff(GreenhouseControls controller, Long du) {
        super(controller,du);

    }

    /**
     * <b>Turns the light off</b>
     */
    public void action() {
        this.ctl.setVariable("Lights", false);
        if (!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);
        }
        return;
    }

    /**
     * @return Lets us know that the light is off
     */
    public String toString() {
        return "Light is off";
    }

}
