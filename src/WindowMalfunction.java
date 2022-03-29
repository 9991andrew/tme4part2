/**
 * Description: The event when the window malfunctions.
 */
public class WindowMalfunction extends Event {
    /**
     * @param controller The greenhouse controls we are working on
     * @param delayTime The amount of time to delay for.
     */
    public WindowMalfunction(GreenhouseControls controller, Long du) {

        super(controller,du);
    }

    /** <b>Sets everything for when the window malfunctions</b>
     * @throws Exception Throws an exception when the window malfunctions
     */
    @Override
    public void action() throws ControllerException{
        this.ctl.setVariable("Window Ok", false);
        this.ctl.setVariable("Error Code", "1");
       /* if(!isAttempted())
        {
            setAttempted(true);
            throw new ControllerException(toString());
        }*/
        return;
    }

    /** <b>Window malfunction toString</b>
     * @return What string is returned when we create an object of this type.
     */
    @Override
    public String toString() {
        return "Window is broken!";
    }
}

