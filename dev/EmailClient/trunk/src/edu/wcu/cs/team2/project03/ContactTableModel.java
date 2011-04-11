package edu.wcu.cs.team2.project03;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * @author Joseph Randall Hunt
 * @author Briam M. Lenau
 * @author Josh Davis
 * @version Nov 7, 2010
 */
public class ContactTableModel extends AbstractTableModel {
    /**
     * Static Integer used to hold the index of the NAME Column
     */
    public static final int         NAME_INDEX    = 0;
    /**
     * Static Integer used to hold the index of the EMAIL Column
     */
    public static final int         EMAIL_INDEX   = 1;
    /**
     * Static Integer used to hold the index of the PHONE Column
     */
    public static final int         PHONE_INDEX   = 2;
    /**
     * Static Integer used to hold the index of the ADDRESS Column
     */
    public static final int         ADDRESS_INDEX = 3;

    /**
     * Field containing an array of column names used to identify
     * identifying data for each contact
     */
    private String[]                columnNames   = { "Name",
            "Email", "Phone", "Address"          };

    private ArrayList<EmailContact> contacts;

    /**
     * Sets the contact list to the parameter and adds an observer.
     * 
     * @param contacts
     *            the contact list
     */
    public ContactTableModel(ArrayList<EmailContact> contacts) {
        this.contacts = contacts;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return contacts.size();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int row, int column) {
        EmailContact contact = contacts.get(row);
        switch (column) {
            case NAME_INDEX:
                return contact.getContactName();
            case EMAIL_INDEX:
                return contact.getEmailAddress();
            case PHONE_INDEX:
                return contact.getPhoneNumber();
            case ADDRESS_INDEX:
                return contact.getPostalAddress();
            default:
                return "Error";
        }
    }

    /**
     * Makes sure all cells in the table are not editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Returns the name of the given column.
     * 
     * @param index
     *            of the column
     * @return name of column
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
