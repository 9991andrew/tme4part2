/**
 * Description: Bell Event class
 */
public class Bell extends Event {
    final int rings;

    /**
     * @param controller The greenhouse controls we are working on.
     * @param
     * @param rings The amount of rings.
     */
    public Bell(GreenhouseControls controller, Long du, Integer rings) {
        super(controller,du);
        this.rings = rings;
    }
    /**
     * @param controller The greenhouse controls we are working on.
     * @param
        */
    public Bell(GreenhouseControls controller, Long du) {
        super(controller,du);
        rings = 0;
    }

    /**
     * <b>Rings the bell.</b>
     */
    public void action() {
        this.ctl.setVariable("Bell", rings);
       /* if(!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);
        }*/
        return;
    }

    /** <b> The Bell toString</b>
     * @return Tells us that the bell is ringing.
     */
    public String toString() {
        return "Bing!";
    }

}
