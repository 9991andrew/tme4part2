import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Restart extends Event {
    private String theString;
    private int rings = 0;

    /**
     * @param ctl       The greenhouse controls we are working on
     * @param delayTime The amount of time to delay for .
     * @param fileName  The file name.
     */
    public Restart(GreenhouseControls ctl, long delayTime, String fileName) {
        super(ctl, delayTime);
        this.theString = fileName;
        //Add the events.
        File file = new File(theString);
        this.ctl.setVariable("Events File", fileName);
        try (Scanner s = new Scanner(file)) {

            while (s.hasNext()) {
                String line = s.nextLine();
                String delimiter = "^Event=(?<event>[^,]*),time=(?<time>[^,]*)(,rings=(?<rings>[^,]*))?$";
                Pattern pattern = Pattern.compile(delimiter);
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    rings = 0;
                    String events = matcher.group("event");
                    long time = Long.parseLong(matcher.group("time"));
                    if (matcher.group("rings") != null) {
                        rings = Integer.parseInt(String.valueOf(matcher.group("rings")));

                    }
                    switch (events) {
                        case "WindowMalfunction":
                        case "ThermostatNight":
                        case "LightOn":
                        case "LightOff":
                        case "WaterOff":
                        case "WaterOn":
                        case "ThermostatDay":
                        case "Terminate":
                        case "FansOn":
                        case "FansOff":
                        case "PowerOut":
                            ctl.addEvent(events, ctl, time);
                            break;
                        case "Bell":
                            long bellSoundCount = time;
                            ctl.addEvent(events, bellSoundCount, ctl, rings);
                            for (int i = 0; i < rings - 1; i++) {
                                bellSoundCount += 2000;
                                ctl.addEvent(events, bellSoundCount, ctl, rings);
                            }
                            break;

                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("hglkdgdf");
            e.printStackTrace();
        }
        //for(Event e : ctl.getEventList())
          //  e.resume();
    }



    public void action() throws ControllerException {
    }


}