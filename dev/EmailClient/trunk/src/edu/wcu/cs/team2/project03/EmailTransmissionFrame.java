package edu.wcu.cs.team2.project03;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This frame allows a user to draft and transmit an email to one of
 * the contacts in their database.
 * 
 * @author Joseph Randall Hunt
 * @author Brian Lenau
 * @author Josh Davis
 * @version Nov 6, 2010
 */
public class EmailTransmissionFrame extends JFrame {
    // Private class to determine whether all the fields have
    // appropriate input. Correct format email address, etc.
    // When they do, the mediator enables or disables the send
    // button.
    private class SendMediator implements DocumentListener {
        public SendMediator() {
            update();
        }

        public void changedUpdate(DocumentEvent e) {
            update();
        }

        public void insertUpdate(DocumentEvent e) {
            update();
        }

        public void removeUpdate(DocumentEvent e) {
            update();
        }

        private void update() {
            boolean enabled =
            toField.getText().trim().length() > 0
            && fromField.getText().trim().length() > 0
            && subjectField.getText().trim().length() > 0
            && bodyArea.getText().trim().length() > 0;
            sendButton.setEnabled(enabled);
            sendMenuItem.setEnabled(enabled);
        }
    }

    /**
     * Text field for the recipient's address.
     */
    private JTextField toField      = new JTextField();

    /**
     * Textfield for the user's address.
     */
    private JTextField fromField    = new JTextField();

    /**
     * Textfield for the subject of the message.
     */
    private JTextField subjectField = new JTextField();

    /**
     * Textarea for the message itself.
     */
    private JTextArea  bodyArea     = new JTextArea();

    /**
     * Button used to send the message.
     */
    private JButton    sendButton   = new JButton("Send");

    /**
     * Button used to cancel the message and close the window.
     */
    private JButton    cancelButton = new JButton("Cancel");

    /**
     * Menu item used to send the message.
     */
    private JMenuItem  sendMenuItem = new JMenuItem("Send");

    /**
     * Menu item used to close the window.
     */
    private JMenuItem  exitMenuItem = new JMenuItem("Exit");

    /**
     * Constructs the frame. Calls creation methods to make
     * the GUI components of the frame.
     * 
     * @param contact
     *            The contact to be used as the recipient
     */
    public EmailTransmissionFrame(EmailContact contact) {
        super();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setJMenuBar(buildMenuBar());
        this.add(buildTopPanel(), BorderLayout.NORTH);
        this.add(buildCenterPanel(), BorderLayout.CENTER);
        this.add(buildBottomPanel(), BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        attachListeners();
        fromField.setEnabled(false);
        fromField.setText(DataStore.getDataStore().getSystemConfig()
        .getSenderEmailAddress());
        toField.setText(contact.getEmailAddress());
        setVisible(true);
    }

    /**
     * Returns the top panel for the frame. Adds the message detail
     * fields,
     * to, from, subject.
     * 
     * @return the top panel
     */
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Details"));
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("To:"));
        panel.add(toField);
        panel.add(new JLabel("From:"));
        panel.add(fromField);
        panel.add(new JLabel("Subject:"));
        panel.add(subjectField);
        return panel;
    }

    /**
     * Returns the center panel for the frame. Adds the message body
     * area.
     * 
     * @return the center panel
     */
    private JPanel buildCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(bodyArea), BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createTitledBorder("Body"));

        return panel;
    }

    /**
     * Returns the bottom panel for the frame. Adds the send and
     * cancel
     * buttons.
     * 
     * @return the bottom panel
     */
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.add(sendButton);
        panel.add(cancelButton);

        return panel;
    }

    /**
     * Returns the Menubar for the window.
     * 
     * @return the menu bar
     */
    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(buildFileMenu());
        menuBar.add(buildEditMenu());

        return menuBar;
    }

    /**
     * Returns the File menu.
     * 
     * @return the file menu
     */
    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");
        menu.add(sendMenuItem);
        menu.add(new JSeparator());
        menu.add(exitMenuItem);

        return menu;
    }

    /**
     * Returns the edit menu.
     * 
     * @return the edit menu
     */
    private JMenu buildEditMenu() {
        JMenu menu = new JMenu("Edit");
        return menu;
    }

    /**
     * Sends the message to the recipient. Sets up the smtp protocols
     * and
     * properties.
     * 
     * @return whether or not the message was sent
     */
    private boolean sendMail() {
        try {
            DataStore dataStore = DataStore.getDataStore();
            Properties props = System.getProperties();
            props.put("mail.smtp.host", dataStore.getSystemConfig()
            .getSmtpHost());

            Session session = Session.getDefaultInstance(props);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromField.getText()));
            msg.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(toField.getText()));
            msg.addHeader("MailApplication", "AwesomeMail");

            msg.setSubject(subjectField.getText());
            msg.setContent(bodyArea.getText(), "text/plain");
            Transport.send(msg);
            return true;
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(this,
            "Error: Unable to send mail", "Error",
            JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Attaches listeners to GUI components
     */
    private void attachListeners() {
        DocumentListener dl = new SendMediator();
        toField.getDocument().addDocumentListener(dl);
        fromField.getDocument().addDocumentListener(dl);
        subjectField.getDocument().addDocumentListener(dl);
        bodyArea.getDocument().addDocumentListener(dl);

        ActionListener sendListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendMail()) {
                    dispose();
                }
            }
        };

        sendButton.addActionListener(sendListener);
        sendMenuItem.addActionListener(sendListener);
        ActionListener exitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        exitMenuItem.addActionListener(exitActionListener);
        cancelButton.addActionListener(exitActionListener);
    }
}
