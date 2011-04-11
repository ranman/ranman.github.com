package edu.wcu.cs.team2.project03;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that display information about the program itself. Authors, how it
 * works, etc.
 */
public class SystemInformationDialog extends JDialog {
    /**
     * Field that contains the GUI part of the class. Displays the information.
     */
    private JDialog systemInfo = new JDialog();
    
    /**
     * Constructor for the class. Sets location, size, visibility, layout,
     * calls creation methods for constituent parts. Also makes it so the
     * window is always on top.
     */
    public SystemInformationDialog(JFrame frame) {
        super(frame);

        Image icon = new ImageIcon("bleargh.jpg").getImage();

        setAlwaysOnTop(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // CENTER
        setModal(true);
        setSize(600, 150);
        setTitle("System Information");
        setIconImage(icon);

        add(buildTopPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }
    
    /**
     * Returns a panel that contains information about the program.
     * @return the system information panel
     */
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(createTextLabel(), BorderLayout.NORTH);
        panel.add(createInfoLabel(), BorderLayout.SOUTH);

        return panel;
    }
    
    /**
     * Returns a panel that contains a button that will close the dialog.
     */
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.add(createDoneButton());

        return panel;
    }

    /**
     * Returns a label that tells the programmer's names.
     * @return the creator label
     */
    private JLabel createTextLabel() {
        JLabel label =
        new JLabel("This mail client was designed by: J."
        + " Randall Hunt, Brian M. Lenau, and Josh Davis.");

        return label;
    }
    
    /**
     * Returns a label that describes how the program works.
     * @return the information label
     */
    private JLabel createInfoLabel() {
        JLabel label =
        new JLabel("It can send email using the SMTP Protocol");

        return label;
    }
    
    /**
     * Returns a button with an aatached listener for use in closing the
     * dialog.
     * @return the done button. Closes the dialog.
     */
    private JButton createDoneButton() {
        JButton button = new JButton("Done");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        return button;
    }
}
