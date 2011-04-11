
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class GraphApp extends JFrame implements ActionListener {
    GraphPanel methodGraph = new BisectionGraphPanel(-5.0, 5.0, 1E-4, 15, F.x2);

    public GraphApp(String title) {
        this.setTitle(title);
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem = null;
        JRadioButtonMenuItem rbMenuItem = null;
        JToolBar toolBar = new JToolBar("Controls");
        JButton button = null;
        ButtonGroup functionsButtonGroup = new ButtonGroup();
        ButtonGroup methodsButtonGroup = new ButtonGroup();
        //
        // FILE MENU
        //
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F4);
        fileMenu.getAccessibleContext().setAccessibleDescription("File Menu");

        menuItem = new JMenuItem("About", KeyEvent.VK_A);
        menuItem.getAccessibleContext().setAccessibleDescription("A program description");
        menuItem.setActionCommand("about");
        menuItem.addActionListener(this);
        fileMenu.add(menuItem);

        menuItem = new JMenuItem("Close", KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("close");
        menuItem.addActionListener(this);
        fileMenu.add(menuItem);

        //
        // Function MENU
        //
        JMenu functionsMenu = new JMenu("Function");
        functionsMenu.setMnemonic(KeyEvent.VK_F5);
        functionsMenu.getAccessibleContext().setAccessibleDescription("NumericalAnalysis Menu");
        for (F function : F.values()) {
            rbMenuItem = new JRadioButtonMenuItem(function.name());
            rbMenuItem.setActionCommand("changeFunction" + function.name());
            functionsButtonGroup.add(rbMenuItem);
            functionsMenu.add(rbMenuItem);
            rbMenuItem.addActionListener(this);
        }

        //
        // METHODS MENU
        //
        JMenu methodsMenu = new JMenu("Methods");
        methodsMenu.setMnemonic(KeyEvent.VK_F6);
        methodsMenu.getAccessibleContext().setAccessibleDescription("Method Menu");

        rbMenuItem = new JRadioButtonMenuItem("Bisection");
        rbMenuItem.setActionCommand("changeMethodBisection");
        rbMenuItem.setSelected(true);
        methodsButtonGroup.add(rbMenuItem);
        rbMenuItem.addActionListener(this);
        methodsMenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Newton");
        rbMenuItem.setActionCommand("changeMethodNewton");
        methodsButtonGroup.add(rbMenuItem);
        rbMenuItem.addActionListener(this);
        methodsMenu.add(rbMenuItem);

        // ADD MENUS
        menuBar.add(fileMenu);
        menuBar.add(functionsMenu);
        menuBar.add(methodsMenu);

        this.setJMenuBar(menuBar);

        // TOOLBAR
        button = new JButton();
        button.setActionCommand("back");
        button.setText("Back");
        button.setMnemonic(KeyEvent.VK_LEFT);
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton();
        button.setActionCommand("step");
        button.setText("Next");
        button.setMnemonic(KeyEvent.VK_RIGHT);
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton();
        button.setActionCommand("reset");
        button.setText("Reset");
        button.addActionListener(this);
        toolBar.add(button);
        this.add(toolBar, BorderLayout.SOUTH);
        this.add(methodGraph, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if ("close".equals(e.getActionCommand())) {
            System.exit(1);
        } else if ("about".equals(e.getActionCommand())) {
            JOptionPane.showMessageDialog(this, "About", "About us",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getActionCommand().contains("changeFunction")) {
            String fName = e.getActionCommand().substring(14);
            methodGraph.method.f = F.valueOf(fName);
            methodGraph.repaint();
        } else if ("changeMethodBisection".equals(e.getActionCommand())) {

        } else if ("changeMethodNewton".equals(e.getActionCommand())) {

        } else if ("step".equals(e.getActionCommand())) {
            System.out.println("calling step");
            methodGraph.repaint();
            System.out.println("Step called");
        } else if ("back".equals(e.getActionCommand())) {

        } else if ("reset".equals(e.getActionCommand())) {

        } else if ("step".equals(e.getActionCommand())) {

        } else if ("step".equals(e.getActionCommand())) {

        }
    }
}
