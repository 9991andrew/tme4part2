/**
 * Description: ThermostatDay Event class
 */
public class ThermostatDay extends Event {
    /**
     * @param controller The greenhouse controls we are working on.
     * @param delayTime The amount of time to delay for.
     */
    public ThermostatDay(GreenhouseControls controller, Long du) {
        super(controller,du);

    }

    /**
     * <b>Sets the thermostat to day.</b>
     */
    public void action() {
        this.ctl.setVariable("Thermostat", true);
     /*   if(!isAttempted())
        {
            GreenhouseControls.gfcf.setTheTextArea(toString());
            setAttempted(true);
        }else
        {
            return;
        }*/
    }

    /** <b>Thermostat toString</b>
     * @return Tells us the thermostat has been set to day.
     */
    public String toString() {
        return "Thermostat on day setting";
    }
}
