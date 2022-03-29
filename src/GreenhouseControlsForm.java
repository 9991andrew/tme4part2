import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/*<b>Greenhouse Controls Gui</b> - GUI For Greenhouse Controls*/
public class GreenhouseControlsForm extends JFrame {
    //Variables
    private final GreenhouseControls gc;

    private String eventsFile;
    private Boolean isDumpFile = false;

    private final JMenu fileMenu;
    private final JMenuItem newWindow;
    private final JMenuItem closeWindow;
    private final JMenuItem openEvents;
    private final JMenuItem restore;
    private final JMenuItem exit;

    private final JMenuItem startPopup;
    private final JMenuItem restartPopup;
    private final JMenuItem terminatePopup;
    private final JMenuItem suspendPopup;
    private final JMenuItem resumePopup;

    private final JPopupMenu popupMenu;

    private JLabel eventsFileLabel;
    private final JLabel filename;

    private final JTextArea textArea;
    private JScrollPane scrollArea;

    private final JButton chooseFile;
    private final JButton restoreDump;
    private final JButton start;
    private final JButton restart;
    private final JButton terminate;
    private final JButton suspend;


    private final JButton resume;

    private GridBagConstraints constraints = new GridBagConstraints();


    /*<b>Constructor for setting everything.</b>
     * @param gc  The greenhouse controls we are using.
     * @param title The title of the window for the JFrame.
     */
    public GreenhouseControlsForm (GreenhouseControls gc, String title)
    {
        super(title);
        this.gc = gc;

        setSize(700,400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        newWindow = new JMenuItem("New window");
        closeWindow = new JMenuItem("Close window");
        openEvents = new JMenuItem("Open Events File");
        restore = new JMenuItem("Restore");
        exit = new JMenuItem("Exit");

        newWindow.addActionListener(new NewWindow());
        //closeWindow.addActionListener(new CloseWindow());
     //   openEvents.addActionListener(new OpenEvents());
        restore.addActionListener(new restoreButton());
       // exit.addActionListener(new CloseWindow());

        startPopup = new JMenuItem("Start");
        restartPopup = new JMenuItem("Restart");
        terminatePopup = new JMenuItem("Terminate");
        suspendPopup = new JMenuItem("Suspend");
        resumePopup = new JMenuItem("Resume");
        startPopup.setEnabled(false);
        restartPopup.setEnabled(false);
        terminatePopup.setEnabled(false);
        suspendPopup.setEnabled(false);
        resumePopup.setEnabled(false);

        startPopup.addActionListener(new startGreenhouse());
        restartPopup.addActionListener(new restartPopupActions());
        terminatePopup.addActionListener(new terminatePopupActions());
        suspendPopup.addActionListener(new suspendPopupActions());
        resumePopup.addActionListener(new resumePopupActions());

        fileMenu.add(newWindow);
        fileMenu.add(closeWindow);
        fileMenu.add(openEvents);
        fileMenu.add(restore);
        fileMenu.add(exit);

        setJMenuBar(menuBar);
        menuBar.add(fileMenu);

//Add Items to Popup Menu-------------------------------------
        popupMenu = new JPopupMenu();
        rightclickListener listener = new rightclickListener();
        popupMenu.addMouseListener(listener);
        popupMenu.add(startPopup);
        popupMenu.add(restartPopup);
        popupMenu.add(terminatePopup);
        popupMenu.add(suspendPopup);
        popupMenu.add(resumePopup);
        addMouseListener(listener);
        add(popupMenu);

//Event File Label---------------------------------------------
        eventsFileLabel = new JLabel("Event File: ");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 5;
        add(eventsFileLabel, constraints);

//Event Filename Label-----------------------------------------
        filename = new JLabel("");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(filename, constraints);

//TextArea-----------------------------------------------------
        textArea = new JTextArea(5, 5);
        scrollArea = new JScrollPane(textArea);
        scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 5;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipady = 150;
        add(scrollArea, constraints);
        textArea.setEditable(false);

        chooseFile = new JButton("Choose");
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 0;
        chooseFile.setEnabled(true);
        chooseFile.addActionListener(new chooseGreenhouse());
        add(chooseFile, constraints);

        restoreDump = new JButton("Restore");
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 0;
        restoreDump.setEnabled(true);
        restoreDump.addActionListener(new restoreButton());
        add(restoreDump, constraints);

        start = new JButton("Start");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 25;
        start.setEnabled(false);
        start.addActionListener(new executeGreenhouse());
        add(start, constraints);

        //Restart Button
        restart = new JButton("Restart");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1;
        restart.setEnabled(false);
        restart.addActionListener(new restartGreenhouse());
        add(restart, constraints);

        //Terminate Button
        terminate = new JButton("Terminate");
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.weightx = 1;
        terminate.setEnabled(false);
        terminate.addActionListener(new terminateGreenhouse());
        add(terminate, constraints);

        //Suspend Button
        suspend = new JButton("Suspend");
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.weightx = 1;
        suspend.setEnabled(false);
        suspend.addActionListener(new suspendGreenhouse());
        add(suspend, constraints);

        //Resume Button
        resume = new JButton("Resume");
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.weightx = 1;
        resume.setEnabled(false);
        resume.addActionListener(new resumeGreenhouse());
        add(resume, constraints);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

    }

    /**
     * <b>Starts the program after we select a file.</b>
     */
    private class startGreenhouse implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                start.setEnabled(false);
                startPopup.setEnabled(true);
                restart.setEnabled(false);
                restartPopup.setEnabled(false);
                terminate.setEnabled(true);
                terminatePopup.setEnabled(true);
                suspend.setEnabled(true);
                suspendPopup.setEnabled(true);
                resume.setEnabled(false);
                resumePopup.setEnabled(false);
                if(isDumpFile)
                {
                    gc.restore(eventsFile);
                }
                if(!isDumpFile) {
                    start.setEnabled(false);
                   gc.getEventList().add(new Restart(gc, 0L, eventsFile));
                    for (int i = 0; i < gc.getEventList().size(); i++) {
                        Event event = gc.getEventList().get(i);
                   //     event.resume();
                    }
                }


        }
    }

    /**
     * <b>Actions restart popup</b>
     */
    private class restartPopupActions implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            restart.setEnabled(false);
            restartPopup.setEnabled(false);
            resume.setEnabled(false);
            resumePopup.setEnabled(false);
            terminate.setEnabled(true);
            terminatePopup.setEnabled(true);
            start.setEnabled(false);
            startPopup.setEnabled(false);
            suspend.setEnabled(true);
            suspendPopup.setEnabled(true);
        }
    }

    /**
     * <b>The action for "terminate" in popup.</b>
     */
    private class terminatePopupActions implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String delay = "";
            long delayTime;
            delay = JOptionPane.showInputDialog(this, "Enter a time in milliseconds:");
            restart.setEnabled(true);
            restartPopup.setEnabled(true);
            suspend.setEnabled(false);
            suspendPopup.setEnabled(false);
            terminate.setEnabled(false);
            terminatePopup.setEnabled(false);
            restart.setEnabled(true);
            restartPopup.setEnabled(true);
            restore.setEnabled(false);
            resume.setEnabled(false);
            resumePopup.setEnabled(false);
            chooseFile.setEnabled(true);
            if(delay == null) {
                return;
            }
            if(delay != null) {
                delayTime =Long.parseLong(delay);
                start.setEnabled(false);
             //   gc.terminateAll(delayTime);
            }

        }
    }

    /**
     * <b>Actions for suspend when we want to use the popup.</b>
     */
    private class suspendPopupActions implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Suspend"))
            {
                suspend.setEnabled(false);
                suspendPopup.setEnabled(false);
                resume.setEnabled(true);
                resumePopup.setEnabled(true);
                terminate.setEnabled(false);
                terminatePopup.setEnabled(false);
                restart.setEnabled(false);
                restartPopup.setEnabled(false);
                start.setEnabled(false);
                startPopup.setEnabled(false);
                gc.suspendAllThreads();
            }
        }
    }

    /**
     * <b>Resumes the events.</b>
     */
    private class resumePopupActions implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Resume"))
            {

                suspend.setEnabled(true);
                suspendPopup.setEnabled(true);
                terminate.setEnabled(true);
                terminatePopup.setEnabled(true);
                restart.setEnabled(false);
                restartPopup.setEnabled(false);
                restore.setEnabled(true);
                resume.setEnabled(false);
                resumePopup.setEnabled(false);
                suspend.setEnabled(true);
                suspendPopup.setEnabled(true);
                start.setEnabled(false);
                startPopup.setEnabled(false);
                for(int i = 0; i < gc.getEventList().size(); i++)
                {
                    Event k = gc.getEventList().get(i);
                   // k.resume();
                }
            }
        }
    }
    /**
     * <b>Listens for popup request, right clicks.</b>
     */
    private class rightclickListener extends MouseAdapter{
        public void mousePressed(MouseEvent event)
        {
            if(event.isPopupTrigger())
            {
                doPop(event);
            }

        }
        public void mouseReleased(MouseEvent event)
        {
            if(event.isPopupTrigger())
            {
                doPop(event);
            }
        }
        private void doPop(MouseEvent event)
        {
            popupMenu.show(event.getComponent(), event.getX(), event.getY());
        }

    }

    /**
     * <b>What happens when press "exit"</b>
     */
    private class ExitActions implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
         /*   if(!gc.IsRunning())
                GreenhouseControlsForm.this.dispose();
            while (gc.IsRunning()) {
                int result = JOptionPane.showConfirmDialog(GreenhouseControlsForm.this,
                        "The program is still running, do you really want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    GreenhouseControlsForm.this.dispose();
                }
            }
        }*/
    }

    /**
     * <b>Popup drop down box Action listener.</b>
     */

    /**
     * <b>Open Event Action - Opens a event file.</b>
     */
    private class OpenEvents implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser2 = new JFileChooser();
            int returnVal = jFileChooser2.showOpenDialog(GreenhouseControlsForm.this);
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = jFileChooser2.getSelectedFile();
                eventsFile = file.getName();
                filename.setText(eventsFile);
                openEvents.setEnabled(false);
                start.setEnabled(true);
                restart.setEnabled(false);
                suspend.setEnabled(true);
                terminate.setEnabled(false);
                restart.setEnabled(false);
                restore.setEnabled(false);
                resume.setEnabled(false);
                chooseFile.setEnabled(false);

            }
        }
    }

    /**
     * <b>Close window action under "file"</b>
     */
    //private class CloseWindow implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        //@Override
       /* public void actionPerformed(ActionEvent e) {
            if(!gc.IsRunning())
                GreenhouseControlsForm.this.dispose();
            while (gc.IsRunning()) {
                int result = JOptionPane.showConfirmDialog(GreenhouseControlsForm.this,
                        "do you really want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    GreenhouseControlsForm.this.dispose();
                }
            }
        }*/
    }

    /**
     * <b>Button for creating new windows under "file"</b>
     */
    private class NewWindow implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("New window"))
            {
                GreenhouseControlsForm greenhouseControlsForm = new GreenhouseControlsForm(new GreenhouseControls(), "Greenhouse Controls");
            }
        }
    }
    /*<b>Restore Button</b> - Button for Restoring the program.*/
    private class restoreButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Restore"))
            {
                JFileChooser jFileChooser = new JFileChooser();
                int returnVal = jFileChooser.showOpenDialog(GreenhouseControlsForm.this);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = jFileChooser.getSelectedFile();
                    eventsFile = file.getName();
                    if(!eventsFile.equals("dump.out"))
                    {
                        setTheTextArea("Cannot load this file. It is not the right format.");
                        isDumpFile = false;
                        start.setEnabled(false);
                        startPopup.setEnabled(false);
                        suspend.setEnabled(false);
                        suspendPopup.setEnabled(false);
                        restart.setEnabled(false);
                        restartPopup.setEnabled(false);
                        return;
                    }
                    isDumpFile = true;
                    filename.setText(eventsFile);
                    start.setEnabled(true);
                    startPopup.setEnabled(true);
                    restart.setEnabled(false);
                    restartPopup.setEnabled(false);
                    suspend.setEnabled(true);
                    suspendPopup.setEnabled(true);
                    terminatePopup.setEnabled(false);
                    terminatePopup.setEnabled(false);
                    restart.setEnabled(false);
                    restartPopup.setEnabled(false);
                    restore.setEnabled(false);
                    resume.setEnabled(false);
                    resumePopup.setEnabled(false);
                    chooseFile.setEnabled(false);


                }
            }
        }
    }
    /*<b>Restarts the Event calls.</b> - Restarts the Event calls.*/
    private class restartGreenhouse implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
            for(Event ev : gc.getEventList())
            {
             //   ev.setAttempted(false);
            }
            restart.setEnabled(false);
            restartPopup.setEnabled(false);
            resume.setEnabled(false);
            resumePopup.setEnabled(false);
            terminate.setEnabled(true);
            terminatePopup.setEnabled(true);
            start.setEnabled(false);
            startPopup.setEnabled(false);
            suspend.setEnabled(true);
            suspendPopup.setEnabled(true);
            for(Event event : gc.getEventList())
            {
           /*     if (!event.isAttempted())
                {
              //      event.restart();
                    Thread t = new Thread(event);
                    t.start();
                }*/
            }
        }
    }
    private class chooseGreenhouse implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser jFileChooser = new JFileChooser();
            if(e.getActionCommand().equals("Choose"))
            {
                System.out.println("gfsdgfsd");
                int returnVal = jFileChooser.showOpenDialog(GreenhouseControlsForm.this);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = jFileChooser.getSelectedFile();
                    eventsFile = file.getName();
                    if(eventsFile.equals("dump.out"))
                    {
                        setTheTextArea("Cannot load this file. It is most likely a dump.out file.");
                        start.setEnabled(false);
                        startPopup.setEnabled(false);
                        suspend.setEnabled(false);
                        suspendPopup.setEnabled(false);
                        restart.setEnabled(false);
                        restartPopup.setEnabled(false);
                        return;
                    }

                        filename.setText(eventsFile);
                        start.setEnabled(true);
                        startPopup.setEnabled(true);
                        restart.setEnabled(false);
                        restartPopup.setEnabled(false);
                        suspend.setEnabled(true);
                        suspendPopup.setEnabled(true);
                        terminate.setEnabled(false);
                        terminatePopup.setEnabled(false);
                        restart.setEnabled(false);
                        restartPopup.setEnabled(false);
                        restore.setEnabled(false);
                        resume.setEnabled(false);
                        resumePopup.setEnabled(false);
                        chooseFile.setEnabled(false);
                        restore.setEnabled(false);

                }
            }
        }
    }
    /*<b>Terminate Button class.</b> - For the terminate button event.*/
    private class terminateGreenhouse implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String delay = "";
            long delayTime;
            delay = JOptionPane.showInputDialog(this, "0");
            if(delay == null) {
                return;
            }
            if(delay != null) {
                delayTime = Long.parseLong(delay);
                start.setEnabled(false);
                textArea.setText("");
              // gc.terminateAll(delayTime);
                restart.setEnabled(true);
                restartPopup.setEnabled(true);
                suspend.setEnabled(false);
                suspendPopup.setEnabled(false);
                terminate.setEnabled(false);
                terminatePopup.setEnabled(false);
                restart.setEnabled(true);
                restartPopup.setEnabled(true);
                restart.setEnabled(false);
                resume.setEnabled(false);
                resumePopup.setEnabled(false);
                chooseFile.setEnabled(true);
            }

        }
    }
    /*<b>Resume Button class.</b> - For the resume button event.*/
    private class resumeGreenhouse implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Resume"))
            {

                suspend.setEnabled(true);
                terminate.setEnabled(true);
                restart.setEnabled(false);
                restore.setEnabled(true);
                resume.setEnabled(false);
                suspend.setEnabled(true);
                start.setEnabled(false);
                gc.resumeAll();
            }
        }
    }
    /*<b>Suspend Button class.</b> - For the suspend button event.*/
    private class suspendGreenhouse implements ActionListener
    {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Suspend"))
            {
                suspend.setEnabled(false);
                resume.setEnabled(true);
                terminate.setEnabled(false);
                restart.setEnabled(false);
                start.setEnabled(false);
                gc.suspendAllThreads();
            }

        }
    }
    /*<b>Execute or Start Button class.</b> - For the execute button event.*/
    private class executeGreenhouse implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Start")) {
                gc.addEvent(new Restart(gc, 0L, eventsFile));

                if(isDumpFile == true && !gc.getErrorCode().equals("0"))
                {
                    gc.restore(eventsFile);
                }
                if(isDumpFile == false) {
                    start.setEnabled(false);
                    }

                start.setEnabled(false);
                startPopup.setEnabled(false);
                restart.setEnabled(false);
                restartPopup.setEnabled(false);
                terminate.setEnabled(true);
                terminatePopup.setEnabled(true);
                suspend.setEnabled(true);
                suspendPopup.setEnabled(true);
                resume.setEnabled(false);
                resumePopup.setEnabled(false);
                restore.setEnabled(true);
             }
        }
    }
    /*<b>Fully shuts down the UI</b> - disables the buttons and acts as if its shutdown.*/
    public void shutdownFully(int errorCode)
    {
        if(!(errorCode > 0)) { //shutdown normally
            start.setEnabled(false);
            restart.setEnabled(false);
            suspend.setEnabled(false);
            terminate.setEnabled(false);
            restart.setEnabled(false);
            restore.setEnabled(false);
            resume.setEnabled(false);
            chooseFile.setEnabled(false);
        }
        else if(errorCode == 1 || errorCode == 2) { //Shutdown for error codes > 0.
            start.setEnabled(false);
            restart.setEnabled(false);
            suspend.setEnabled(false);
            terminate.setEnabled(false);
            restart.setEnabled(false);
            restore.setEnabled(true);
            resume.setEnabled(false);
            chooseFile.setEnabled(false);
        }
    }
    /*<b>Sets the text area.</b> -  Set the text area.*/
    public void setTheTextArea(String s) {
        textArea.append(s + "\n");
    }
}

