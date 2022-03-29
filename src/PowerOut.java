/**
 * Description: The event when the power is out.
 */
public class PowerOut extends Event {

    /**
     * @param controller The greenhouse controls we are working on
     * @param delayTime The amount of time to delay for.
     */
    public PowerOut(GreenhouseControls controller,Long du) {
        super(controller, du);
    }

    /** <b>Turns of the power.</b>
     * @throws Exception The exception thrown when the power is off.
     */
    @Override
    public void action() throws ControllerException {
        this.ctl.setVariable("Power On", false);
        this.ctl.setVariable("Error Code", "2");
     /*   if(!isAttempted())
        {
            setAttempted(true);
            throw new ControllerException("Error Code 2: Power out!");
        }else
        {
            return;
        }*/

    }

    /** <b>The PowerOut toString</b>
     * @return Lets us know the power is out!
     */
    @Override
    public String toString() {
        return "Power is out!";
    }


}
