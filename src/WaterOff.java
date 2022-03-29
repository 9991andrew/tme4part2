/**
 * Description WaterOff event class
 */
public class WaterOff extends Event {

    /**
     * @param controller The greenhouse controls we are working on
     * @param delayTime The amount of time to delay for
     */
    public WaterOff(GreenhouseControls controller, Long du) {
        super(controller, du);
        this.ctl.setVariable("Water", false);
    }

    /**
     * <b>The action called when the water is off.</b>
     */
    public void action() {
        /*if(!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);
        }else
        {
            return;
        }*/
    }

    /** <b>The WaterOff toString</b>
     * @return Lets us know that the water is off!
     */
    public String toString() {
        return "Greenhouse water is off";
    }
}
