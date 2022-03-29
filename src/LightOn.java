/**
 * Description: Light On Event Class.
 */
public class LightOn extends Event {

    /**
     * @param controller The greenhouse controls we are working on
     * @param delayTime The amount of time to delay for.
     */
    public LightOn(GreenhouseControls controller, Long du) {
        super(controller,du);
    }

    /**
     * <b>Turns the lights on.</b>
     */
    public void action() {
        this.ctl.setVariable("Lights", true);
    /*    if(!isAttempted())
        {
            setAttempted(true);
            GreenhouseControls.gfcf.setTheTextArea(toString());
        }*/
        return;
    }

    /**
     * @return lets us know that the light is on
     */
    public String toString() {
        return "Light is on";
    }


}
