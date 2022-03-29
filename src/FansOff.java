/**
 * Description: The event when fans are off.
 */
public class FansOff extends Event {

    /**
     * @param controller The greenhouse controls we are working on
     * @param
     */
    public FansOff(GreenhouseControls controller, Long du) {
        super(controller, du);

    }

    /**
     * <b>Turns the fans off.</b>
     */
    public void action() {
        this.ctl.setVariable("Fans", false);
       // if(isAttempted())
        //{
         //   GreenhouseControls.gfcf.setTheTextArea(toString());
          //  setAttempted(true);
        //}
    }

    /** <b>Fans off toString</b>
     * @return Lets us know that the fans are off
     */
    public String toString() {
        return "Fans are Off";
    }
}