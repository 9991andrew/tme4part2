/**
 * Description: ThermostatNight event class.
 */
public class ThermostatNight extends Event {

    /**
     * @param controller Green house controls we are working on
     * @param delayTime The amount of time to delay for.
     */
    public ThermostatNight(GreenhouseControls controller, Long du) {
        super(controller,du);

    }

    /**
     * <b>Turns the thermostat to night.</b>
     */
    public void action() {
        this.ctl.setVariable("Thermostat","Night" );

     /*   if(!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);
        }else
        {
            return;
        }*/

    }

    /** <b>Thermostat night toString</b>
     * @return When a thermostat night is created this is sent to us.
     */
    public String toString() {
        return "Thermostat on night setting";
    }

}

