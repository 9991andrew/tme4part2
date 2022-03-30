/*
 * Description: WaterOn Event Class
 */
public class WaterOn extends Event {

    /**
     * @param controller The greenhouse controls we are working on
     * @param du The amount of time to delay for.
     */
    public WaterOn(GreenhouseControls controller, Long du) {
        super(controller,du);

    }

    /**
     * <b>The action for this event. Which is turn the water on.</b>
     */
    public void action() {
        this.ctl.setVariable("Water", true);
       if(!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);
        }else{
            return;
        }
    }

    /** <b>The WaterOn toString</b>
     * @return String we get when we create an object of this type.
     */
    public String toString() {
        return "Greenhouse water is on";
    }

}

