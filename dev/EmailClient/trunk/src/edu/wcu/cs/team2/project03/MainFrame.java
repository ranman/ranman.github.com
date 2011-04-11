package edu.wcu.cs.team2.project03;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * A class that implements the main window frame of the Email
 * client. This class extends JFrame because it models a JFrame.
 * The frame, when loaded, contains all the information on the
 * users current contacts. It allows the user to add, remove,
 * or edit contacts within the table. It also allows the user
 * to create a new Email to a selected contact.
 * 
 * @author Brian M. Lenau
 * @author J. Randall Hunt
 * @author Josh Davis
 * @version Nov 4, 2010
 */
public class MainFrame extends JFrame {
    /**
     * Title for the main frame. Appears in the top bar of the window.
     */
    public static final String      WINDOW_TITLE      =
                                                      "Awesome Mail";

    /** Dialog that contains information about the program itself. */
    private SystemInformationDialog systemInfoDialog  =
                                                      new SystemInformationDialog(
                                                      this);

    /**
     * Dialog for allowing the user to change the System
     * Configuration.
     */
    private ConfigurationDialog     configDialog      =
                                                      new ConfigurationDialog(
                                                      this);

    /**
     * Dialog for editing contact information.
     */
    private ContactEditingDialog    contactEditDialog =
                                                      new ContactEditingDialog(
                                                      this);

    /**
     * Button to add a new contact. Opens the ContactEditingDialog
     * with blank input areas.
     */
    private JButton                 addButton         = new JButton(
                                                      "Add");

    /**
     * Button to edit an existing contact. Opens the
     * ContactEditingDialog with the contacts information in the input
     * areas.
     */
    private JButton                 editButton        = new JButton(
                                                      "Edit");

    /** Button to delete an existing contact. */
    private JButton                 deleteButton      = new JButton(
                                                      "Delete");

    /**
     * Menu item to add a new contact. Opens the ContactEditingDialog
     * with blank input areas.
     */
    private JMenuItem               addMenuItem       =
                                                      new JMenuItem(
                                                      "Add");
    /**
     * Menu item to edit an existing contact. Opens the
     * ContactEditingDialog with
     * the contacts information in the input areas.
     */
    private JMenuItem               editMenuItem      =
                                                      new JMenuItem(
                                                      "Edit");
    /**
     * Menu item to delete an existing contact.
     */
    private JMenuItem               deleteMenuItem    =
                                                      new JMenuItem(
                                                      "Delete");
    /**
     * Menu item that opens the ConfigurationDialog.
     */
    private JMenuItem               configMenuItem    =
                                                      new JMenuItem(
                                                      "Configure");

    /** Table who displays all contacts. */
    private JTable                  contactTable;

    /**
     * This constructor makes you an awesome mail, it centers the
     * window in the screen and creates the frames and stuff.
     */
    public MainFrame() {
        this.setTitle("Awesome Mail");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Make 600x600 window centered on screen.
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);

        // Setup the frame:
        this.setLayout(new BorderLayout());
        // JTable and buttons
        this.add(this.createContactsTable(), BorderLayout.CENTER);
        this.add(this.buildBottomPanel(), BorderLayout.SOUTH);
        // Build the JMenu
        this.setJMenuBar(this.createMenuBar());
        // Saves the state of the client on close
        WindowAdapter frameListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    DataStore.getDataStore().saveStateToDisk();
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    DataStore.getDataStore().saveStateToDisk();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        this.addWindowListener(frameListener);

        // Attach listeners to buttons, menu items, and add a mediator
        // for the table buttons.
        attachListeners();
    }

    /**
     * Creates and attaches <code>ActionListener</code>s to the
     * appropriate item.
     */
    private void attachListeners() {
        /*
         * Creates a table mediator. This will only enable the buttons
         * and menu items if a row in the table is selected.
         */
        ListSelectionListener sl = new ListSelectionListener() {
            @SuppressWarnings({ "synthetic-access", "unused" })
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Set to false if no rows are selected
                boolean enabled =
                (contactTable.getSelectedRow() != -1);
                deleteButton.setEnabled(enabled);
                deleteMenuItem.setEnabled(enabled);
                editButton.setEnabled(enabled);
                editMenuItem.setEnabled(enabled);
            }
        };

        contactTable.getSelectionModel().addListSelectionListener(sl);

        configMenuItem.addActionListener(new ActionListener() {
            @SuppressWarnings({ "synthetic-access", "unused" })
            @Override
            public void actionPerformed(ActionEvent e) {
                configDialog.setVisible(true);
            }
        });

        ActionListener addListener = new ActionListener() {
            @SuppressWarnings({ "synthetic-access", "unused" })
            @Override
            public void actionPerformed(ActionEvent e) {
                contactEditDialog.setVisible(true);
                contactEditDialog.setEditing(-1);
            }
        };

        addButton.addActionListener(addListener);
        addMenuItem.addActionListener(addListener);

        ActionListener deleteListener = new ActionListener() {
            @SuppressWarnings({ "unused", "synthetic-access" })
            @Override
            public void actionPerformed(ActionEvent e) {
                int contactIndex = contactTable.getSelectedRow();
                DataStore.getDataStore().getContacts()
                .remove(contactIndex);
                ContactTableModel model =
                (ContactTableModel) contactTable.getModel();
                model.fireTableDataChanged();
            }
        };

        deleteButton.addActionListener(deleteListener);
        deleteMenuItem.addActionListener(deleteListener);

        ActionListener editListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactEditDialog.setEditing(-1);
                int row = contactTable.getSelectedRow();
                if (row != -1)
                    contactEditDialog.setEditing(row);
                contactEditDialog.setVisible(true);
            }
        };

        editButton.addActionListener(editListener);
        editMenuItem.addActionListener(editListener);

        contactTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    EmailContact contact =
                    DataStore.getDataStore().getContacts()
                    .get(contactTable.getSelectedRow());
                    new EmailTransmissionFrame(contact);
                }
            }
        });
    }

    /**
     * Method that returns a scrollable table that contains all
     * Contacts.
     * 
     * @return a scrollable table with all contact information
     */
    private JScrollPane createContactsTable() {
        DataStore dataStore = DataStore.getDataStore();
        ArrayList<EmailContact> contacts = dataStore.getContacts();
        ContactTableModel contactTableModel =
        new ContactTableModel(contacts);
        contactTable = new JTable(contactTableModel);
        contactTable
        .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactTable.setRowSelectionAllowed(true);
        contactTable.setColumnSelectionAllowed(false);
        return new JScrollPane(contactTable);
    }

    /**
     * Returns a panel with the contact control buttons. Has the Add
     * button,
     * Edit button, and Delete button.
     * 
     * @return a panel with the contact controls
     */
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        deleteButton.setEnabled(false);
        deleteMenuItem.setEnabled(false);
        editButton.setEnabled(false);
        editMenuItem.setEnabled(false);
        return panel;
    }

    /**
     * Returns a Menubar with the the main menus.
     * 
     * @return the main menubar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        menu.add(this.createFileMenu());
        menu.add(this.createConfigMenu());
        menu.add(this.createHelpMenu());

        return menu;
    }

    /**
     * Returns a menu with the contact controls and the Exit item.
     * 
     * @return the File menu
     */
    private JMenu createFileMenu() {
        JMenuItem menuItem;
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(addMenuItem);
        fileMenu.add(editMenuItem);
        fileMenu.add(deleteMenuItem);
        fileMenu.add(new JSeparator());
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        fileMenu.add(menuItem);
        return fileMenu;
    }

    /**
     * Returns a menu with the ability to open the
     * SystemConfigurationDialog.
     * 
     * @return the Configuration menu
     */
    private JMenu createConfigMenu() {
        JMenu configMenu = new JMenu("Configuration");
        configMenu.add(configMenuItem);
        return configMenu;
    }

    /**
     * Returns a menu with the ability to open the systemInfoDialog.
     * 
     * @return the help Menu
     */
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem menuItem = new JMenuItem("About");
        menuItem.addActionListener(new ActionListener() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void actionPerformed(ActionEvent e) {
                systemInfoDialog.setVisible(true);
            }
        });
        helpMenu.add(menuItem);
        return helpMenu;
    }

    public boolean addContact(EmailContact contact) {
        if (DataStore.getDataStore().getContacts().contains(contact)) {
            return false;
        }
        DataStore.getDataStore().getContacts().add(contact);
        ContactTableModel model =
        (ContactTableModel) contactTable.getModel();
        model.fireTableDataChanged();
        return true;
    }

    public void setContact(int index, EmailContact contact) {
        DataStore.getDataStore().getContacts().set(index, contact);
        ContactTableModel model =
        (ContactTableModel) contactTable.getModel();
        model.fireTableDataChanged();
    }

    public static void main(String... args) {
        DataStore dataStore = DataStore.getDataStore();
        ArrayList<EmailContact> emailContacts =
        dataStore.getContacts();
        emailContacts.add(new EmailContact("Randall Hunt", "123",
        "808-351-7739", "randallhunt@gmail.com"));
        emailContacts.add(new EmailContact("Brian Lenau", "123",
        "808-351-7739", "bleanu@polaris.cs.wcu.edu"));
        emailContacts.add(new EmailContact("Josh Davis", "123",
        "808-351-7739", "jdavis@polaris.cs.wcu.edu"));
        MainFrame test = new MainFrame();
        test.setVisible(true);
    }
}
