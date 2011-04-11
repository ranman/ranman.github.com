import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;

public class TabbedView extends JPanel implements MessageListener {
    private MoteIF moteif = new MoteIF();

    protected JComponent makeTextPanel(String label) {
        JPanel panel = new JPanel(false);
        JLabel text = new JLabel(label);
        text.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(text);
        return panel;
    }

    public void messageReceived(int toAddr, Message msg) {
        if (msg instanceof SensorMessage) {
            SensorMessage sm = (SensorMessage) msg;
            System.out.println("Time =" + System.currentTimeMillis());
            System.out.println("\tVoltage: " + sm.get_voltage());
            System.out.println("\tHumidity: " + sm.get_humidity());
            System.out.println("\tTemperature: " + sm.get_temperature());
            System.out.println("\tTSR: " + sm.get_solarRadiation());
            System.out.println("\tPSR: " + sm.get_photoRadiation());
        }
    }

    VoltageGraphPanel voltageGraphPanel     = new VoltageGraphPanel();
    JComponent        humidityGraphPanel    = makeTextPanel("Humidity");
    JComponent        temperatureGraphPanel = makeTextPanel("Temperature");
    JComponent        tsrGraphPanel         = makeTextPanel("TSR");
    JComponent        psrGraphPanel         = makeTextPanel("PSR");

    public TabbedView() {
        super(new GridLayout(1, 1));
        moteif.registerListener(new SensorMessage(), this);
        setPreferredSize(new Dimension(400, 400));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Voltage", voltageGraphPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab("Humidity", humidityGraphPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.addTab("Temperature", temperatureGraphPanel);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabbedPane.addTab("TSR", tsrGraphPanel);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.addTab("PSR", psrGraphPanel);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
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
