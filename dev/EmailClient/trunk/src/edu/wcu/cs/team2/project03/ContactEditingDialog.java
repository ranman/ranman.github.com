package edu.wcu.cs.team2.project03;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is a modal dialog that allows for editing of the
 * contacts.
 * 
 * @author Joseph Randall Hunt
 * @author Brian Lenau
 * @author Josh Davis
 * @version Nov 6, 2010
 */
public class ContactEditingDialog extends JDialog {
    /**
     * Textfield for the contact's name.
     */
    private JTextField           contactNameField;

    /**
     * Textfield for the contact's postal address.
     */
    private JTextField           postalAddressField;

    /**
     * Textfield for the contact's phone number.
     */
    private JTextField           phoneNumberField;

    /**
     * Textfield for the contact's email address.
     */
    private JTextField           emailAddressField;

    /**
     * Index of contact to be edited
     */
    private int                  editIndex;

    private MainFrame            parent;
    private ContactEditingDialog dialog;

    /**
     * Sets up the GUI. Calls all creation methods and sets the
     * location and size of the frame.
     * 
     * @param frame
     *            JFrame for use in the JFrame constructor
     */
    public ContactEditingDialog(MainFrame frame) {
        super(frame);
        this.parent = frame;
        this.editIndex = -1;
        this.dialog = this;
        setModal(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        setSize(400, 400);
        setTitle("Contact");
        WindowAdapter frameListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                editIndex = -1;
                clearFields();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                editIndex = -1;
                clearFields();
            }
        };
        this.addWindowListener(frameListener);

        contactNameField = new JTextField();
        postalAddressField = new JTextField();
        phoneNumberField = new JTextField();
        emailAddressField = new JTextField();

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }

    /**
     * Loads the given contact's information into the Textfields
     */
    private void loadContactIntoFields() {
        EmailContact contact =
        DataStore.getDataStore().getContacts().get(editIndex);
        contactNameField.setText(contact.getContactName());
        postalAddressField.setText(contact.getPostalAddress());
        phoneNumberField.setText(contact.getPhoneNumber());
        emailAddressField.setText(contact.getEmailAddress());
    }

    /**
     * Opens a different contacts information.
     * 
     * @param editIndex
     */
    public void setEditing(int editIndex) {
        if (editIndex != -1) {
            this.editIndex = editIndex;
            loadContactIntoFields();
        }
    }

    /**
     * Returns the top panel of the GUI. Contains the contact's
     * information.
     * 
     * @return the top panel
     */
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(contactNameField);
        panel.add(new JLabel("Postal Address:"));
        panel.add(postalAddressField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Email Address"));
        panel.add(emailAddressField);
        return panel;
    }

    /**
     * Returns the bottom panel of GUI. Contains the Save and Cancel
     * buttons
     * 
     * @return the bottom panel
     */
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.add(buildCancelButton());
        panel.add(buildSaveButton());

        return panel;
    }

    /**
     * Return the done button. Creates the Listener class to
     * determine what to do when the button is clicked.
     * 
     * @return the done button
     */
    private JButton buildCancelButton() {
        JButton button = new JButton("Cancel");
        button.addActionListener(new ActionListener() {
            // actionListener to catch the click action and close the
            // dialog.
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                clearFields();
                setEditing(-1);
            }
        });
        return button;
    }

    /**
     * Return the save button. Creates the Listener class to
     * determine what to do when the button is clicked.
     * 
     * @return the save button
     */
    private JButton buildSaveButton() {
        JButton button = new JButton("Save");

        button.addActionListener(new ActionListener() {
            // actionListener to catch the click action and save the
            // contact
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ValidationUtil.isValidPhone(phoneNumberField
                .getText())
                && ValidationUtil.isValidEmail(emailAddressField
                .getText())) {
                    saveContact();
                    clearFields();
                    setEditing(-1);
                    setVisible(false);
                } else {
                    JOptionPane
                    .showMessageDialog(
                    dialog,
                    "Phone or e-mail is invalid\n "
                    + "Phone should be: ###-###-####\n"
                    + "Email needs to be a well formed email address",
                    "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }

    private void clearFields() {
        contactNameField.setText("");
        postalAddressField.setText("");
        phoneNumberField.setText("");
        emailAddressField.setText("");
    }

    private void saveContact() {
        EmailContact contact =
        new EmailContact(contactNameField.getText(),
        postalAddressField.getText(), phoneNumberField.getText(),
        emailAddressField.getText());

        if (editIndex != -1) {
            parent.setContact(editIndex, contact);
        } else {
            if (!parent.addContact(contact)) {
                JOptionPane.showMessageDialog(dialog,
                "This contact already exists.", "Error",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
