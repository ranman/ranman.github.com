package edu.wcu.cs.team2.project03;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is a modal dialog that allows the user to update the
 * SystemConfiguration parameters.
 * 
 * @author Joseph Randall Hunt
 * @author Brian Lenau
 * @author Josh Davis
 * @version Nov 6, 2010
 */
public class ConfigurationDialog extends JDialog {
    /**
     * Text field used to contain a new email address for the user.
     * Once placed in the textfield, user clicks the Save button and
     * the
     * new email address is saved.
     */
    private JTextField userEmailAddress;

    /**
     * Text field used to contain a new server address for incoming
     * and
     * outgoing mail. Once placed in the textfield, user clicks the
     * Save button and the
     * new email address is saved.
     */
    private JTextField smtpHost;

    /**
     * Constructor for Configuration Dialog model. Calls setup methods
     * and assigns locations so all textfields, buttons, etc, are in
     * their proper location. Also sets the default location and size
     * of the model. Initializes the userEmailAddress and smtpHost
     * Text Fields.
     */
    public ConfigurationDialog(JFrame parent) {
        super(parent);

        // Set to modal dialog
        setModal(true);

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        setSize(400, 400);
        setTitle("System Configuration");

        userEmailAddress = new JTextField();
        smtpHost = new JTextField();

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }

    /**
     * Method that adds all relevant items and settings to the top
     * panel of the model. Sets the Layout of the panel and calls
     * build functions for each item to be added to the panel.
     * 
     * @return a panel for use in the top part of the model. holds
     *         the userEmailAddress and smtpHost text fields and
     *         labels
     */
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("User Email:"));
        panel.add(buildEmailTextField());
        panel.add(new JLabel("SMTP Host:"));
        panel.add(buildSmtpTextField());
        return panel;
    }

    /**
     * Method that adds all relevant items and settings to the bottom
     * panel of the model. Sets the Layout of the panel and calls
     * build functions for each item to be added to the panel.
     * 
     * @return a panel for use in the bottom part of the model. holds
     *         the save button and done button
     */
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.add(buildCancelButton());
        panel.add(buildSaveButton());

        return panel;
    }

    /**
     * Sets the userEmailAddress text field's contents to default to
     * their current email.
     * 
     * @return a textfield with the current email address
     */
    private JTextField buildEmailTextField() {
        DataStore dataStore = DataStore.getDataStore();

        userEmailAddress.setText(dataStore.getSystemConfig()
        .getSenderEmailAddress());
        return userEmailAddress;
    }

    /**
     * Sets the smtpHost text field's contents to default to
     * their host server's address.
     * 
     * @return a textfield with the current smtp server address
     */
    private JTextField buildSmtpTextField() {
        DataStore dataStore = DataStore.getDataStore();

        smtpHost.setText(dataStore.getSystemConfig().getSmtpHost());
        return smtpHost;
    }

    /**
     * Returns a button that "closes" the model. Sets the model's
     * visibility ot false when clicked.
     * 
     * @return a button used to "close" the window. has an Observer
     *         pattern used to determine when clicked and set model
     *         visibility
     */
    private JButton buildCancelButton() {
        JButton button = new JButton("Cancel");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                DataStore dataStore = DataStore.getDataStore();

                userEmailAddress.setText(dataStore.getSystemConfig()
                .getSenderEmailAddress());
                smtpHost.setText(dataStore.getSystemConfig()
                .getSmtpHost());
            }
        });
        return button;
    }

    /**
     * Returns a button that saves changes to the user email address
     * and the smtp server address. Updates the datastore with the
     * current settings.
     * 
     * @return a button used to determine when to update the data
     *         store has an Observer pattern used to determine when
     *         clicked
     *         and execute update.
     */
    private JButton buildSaveButton() {
        JButton button = new JButton("Save");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidationUtil.isValidEmail(userEmailAddress
                .getText())
                && ValidationUtil.isValidHost(smtpHost.getText())) {
                    DataStore dataStore = DataStore.getDataStore();
                    dataStore
                    .getSystemConfig()
                    .setSenderEmailAddress(userEmailAddress.getText());
                    String address = smtpHost.getText();
                    dataStore.getSystemConfig().setSmtpHost(address);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(
                    (Component) e.getSource(),
                    "Those aren't valid fields", "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }
}
