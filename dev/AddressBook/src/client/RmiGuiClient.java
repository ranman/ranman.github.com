package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import common.AddressBook;
import common.Contact;

/**
 * This class implements a graphical front-end and RMI back-end of an
 * AddressBook. A better example would have separated the view and the model.
 * 
 * @author Randall Hunt
 * 
 */
public class RmiGuiClient extends JFrame {
    /**
     * 
     */
    private static final long    serialVersionUID = -4254069561283599563L;
    /** */
    private Registry             registry;
    /** */
    private AddressBook          addressBook;
    /** */
    private JTable               contactTable;
    /** */
    private JButton              addButton;
    /** */
    private JButton              removeButton;
    /** Column Names */
    public static final String[] columnNames      =
                                                          { "First Name",
            "Last Name"                                  };

    /**
     * Creates a new <code>RmiGuiClient</code>.
     * 
     * @param hostname
     *            the host to connect to.
     * @param port
     *            the port to connect to.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public RmiGuiClient(String hostname, int port) throws RemoteException,
            NotBoundException {
        this.registry = LocateRegistry.getRegistry(hostname, port);
        this.addressBook = (AddressBook) this.registry.lookup("addressBook");

        // ///////////
        // THE GUI //
        // ///////////
        this.setLayout(new BorderLayout());
        // Populate Table
        List<Contact> contactList = this.addressBook.getContactList();
        String[][] rowData = new String[0][0];
        if (!contactList.isEmpty()) {
            rowData = new String[contactList.size()][2];
            for (int i = 0; i < contactList.size(); i++) {
                rowData[i][0] = contactList.get(i).getFirstName();
                rowData[i][1] = contactList.get(i).getLastName();
            }
        }
        DefaultTableModel dm =
                new DefaultTableModel(rowData, RmiGuiClient.columnNames);
        this.contactTable = new JTable(dm);
        this.contactTable
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.contactTable.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(this.contactTable);
        this.contactTable.setFillsViewportHeight(true);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        this.addButton = new JButton("Add Contact");
        buttonPanel.add(this.addButton);
        this.removeButton = new JButton("Remove Contact");
        buttonPanel.add(this.removeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String first = JOptionPane.showInputDialog("First Name: ");
                String last = JOptionPane.showInputDialog("Last Name: ");
                Contact c = new Contact(first, last);
                DefaultTableModel dm =
                        (DefaultTableModel) RmiGuiClient.this.contactTable
                                .getModel();

                if (first.equals("") || last.equals("")) {
                    JOptionPane
                            .showMessageDialog(RmiGuiClient.this.contactTable,
                                    "Please fill out the fields correctly.",
                                    "Problem Adding Contact",
                                    JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        if (RmiGuiClient.this.addressBook.lookupContact(first,
                                last) == null) {
                            RmiGuiClient.this.addressBook.addContact(c);
                            dm.addRow(new String[] { first, last });
                        } else {
                            JOptionPane.showMessageDialog(
                                    RmiGuiClient.this.contactTable,
                                    "Contact already exists.",
                                    "Problem Adding Contact",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(
                                RmiGuiClient.this.contactTable,
                                "Connection to server comprimised.",
                                "Problem Adding Contact",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dm =
                        (DefaultTableModel) RmiGuiClient.this.contactTable
                                .getModel();
                String first =
                        (String) dm.getValueAt(RmiGuiClient.this.contactTable
                                .getSelectedRow(), 0);
                String last =
                        (String) dm.getValueAt(RmiGuiClient.this.contactTable
                                .getSelectedRow(), 1);
                try {
                    RmiGuiClient.this.addressBook.removeContact(first, last);
                    dm.removeRow(RmiGuiClient.this.contactTable
                            .getSelectedRow());
                } catch (RemoteException ex) {
                    JOptionPane
                            .showMessageDialog(RmiGuiClient.this.contactTable,
                                    "Connection to server comprimised.",
                                    "Error Removing Contact",
                                    JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    };

    /**
     * Makes the GUI visible.
     * 
     * @throws RemoteException
     */
    public void execute() throws RemoteException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
