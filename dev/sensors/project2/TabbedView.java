import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;

/**
 * This class starts the gui and everything then it waits for messages and when
 * it gets a message it adds a point the the graph like a boss. The
 * messageRecieved method does all the conversions and is super awesome.
 * 
 * @author Joseph Randall Hunt
 * @author Ben Rudolph
 * @author Chris Blades
 * @version Mar 12, 2010
 */
public class TabbedView extends JPanel implements MessageListener {
    private MoteIF           moteif                = new MoteIF();
    private GraphPanel       voltageGraphPanel     =
                                                           new GraphPanel("Voltage", "Time", "V",
                                                                   0.0, 5.0);
    private GraphPanel       humidityGraphPanel    =
                                                           new GraphPanel("Humidity", "Time",
                                                                   "Humidity", 0.0, 100.0);
    private GraphPanel       temperatureGraphPanel =
                                                           new GraphPanel("Temperature", "Time",
                                                                   "Temperature (C)", -20.0, 40.0);
    private GraphPanel       tsrGraphPanel         = new GraphPanel("TSR", "Time", "Lux", 0, 2E3);
    private GraphPanel       psrGraphPanel         = new GraphPanel("PSR", "Time", "Lux", 0, 2E2);
    private SimpleDateFormat sdf                   = new SimpleDateFormat("h:mm:ss:SSS");

    /**
     * 
     */
    public TabbedView() {
        super(new GridLayout(1, 1));
        moteif.registerListener(new SensorMessage(), this);
        setPreferredSize(new Dimension(660, 400));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Voltage", new ImageIcon("images/voltage.png"), voltageGraphPanel);
        tabbedPane.addTab("Humidity", new ImageIcon("images/humidity.png"), humidityGraphPanel);
        tabbedPane.addTab("Temperature", new ImageIcon("images/temperature.png"),
                temperatureGraphPanel);
        tabbedPane.addTab("TSR", new ImageIcon("images/tsr.png"), tsrGraphPanel);
        tabbedPane.addTab("PSR", new ImageIcon("images/psr.png"), psrGraphPanel);
        this.add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sensor Grapher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TabbedView(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void messageReceived(int toAddr, Message msg) {
        if (msg instanceof SensorMessage) {
            SensorMessage sm = (SensorMessage) msg;
            int addr = sm.get_address();
            double voltage = sm.get_voltage();
            double humidity = sm.get_humidity();
            double temperature = sm.get_temperature();
            double solarRadiation = sm.get_solarRadiation();
            double photoRadiation = sm.get_photoRadiation();

            // Conversions
            voltage = ((voltage / 4096) * 1.5) * 2;

            temperature = -39.60 + 0.01 * temperature;

            humidity = -4 + 0.0405 * humidity + (-2.8 * Math.pow(10, -6)) * Math.pow(humidity, 2);
            humidity = (temperature - 25) * (.01 + .00008 * sm.get_humidity()) + humidity;

            double current = (((solarRadiation / 4096) * 1.5) * 2) / 100000;
            solarRadiation = 0.625 * 1E6 * current * 1000;
            current = ((photoRadiation / 4096) * 1.5) * 2 / 100000;
            photoRadiation = 0.769 * 1E5 * current * 1000;
            // END CONVERSIONS

            voltageGraphPanel.addPoint(addr, voltage);
            humidityGraphPanel.addPoint(addr, humidity);
            temperatureGraphPanel.addPoint(addr, temperature);
            tsrGraphPanel.addPoint(addr, solarRadiation);
            psrGraphPanel.addPoint(addr, photoRadiation);
            Calendar cal = Calendar.getInstance();
            System.out.println("Time: " + sdf.format(cal.getTime()));
            System.out.println("\tAddress: " + addr);
            System.out.println("\t\tVoltage: " + voltage);
            System.out.println("\t\tTemperature: " + temperature);
            System.out.println("\t\tHumidity: " + humidity);
            System.out.println("\t\tPSR: " + solarRadiation);
            System.out.println("\t\tTSR: " + photoRadiation);
        }
    }

    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
